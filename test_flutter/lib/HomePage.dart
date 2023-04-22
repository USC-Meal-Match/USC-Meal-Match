// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Home Page

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'Profile.dart';
import 'package:http/http.dart' as http;
import 'SignupLoginPage.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late Future<Map<String, dynamic>> diningHallDataFuture;
  List<int> _lastSubmittedRatingTimestamp = [0, 0, 0];
  List<double> _ratings = [0, 0, 0];
  List<double> _submittedRatings = [0, 0, 0];
  List<bool> _hasSubmittedRating = [false, false, false];
  late bool _isLoggedIn;

  void _submitRating(int index) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? userID = prefs.getString('userID');
    if (userID != null) {
      _hasSubmittedRating[index] = true;
      String rate = _submittedRatings[index].ceil().toString();
      String dinID = (index + 1).toString();
      DateTime now = DateTime.now();

      final response = await http.post(
        Uri.parse('http://34.83.198.237/usc_mealmatch/rating'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'rating': rate,
          'userID': userID,
          'diningHallID': dinID,
        }),
      );

      if (response.statusCode == 200) {
        updateDiningHallRating(index);
        setState(() {
          _submittedRatings[index] = -1;
          _lastSubmittedRatingTimestamp[index] = now.millisecondsSinceEpoch;
        });
        prefs.setInt('lastSubmittedRatingTimestamp$dinID', _lastSubmittedRatingTimestamp[index]);
      } else {
        // Handle rating submission error
      }
    } else {
      // Handle the case when the user is not logged in
    }
  }




  void updateDiningHallRating(int index) async {
    double newRating = await fetchDiningHallRating(index);
    setState(() {
      _ratings[index] = newRating;
    });
  }

  Future<Map<String, dynamic>> fetchDiningHallData() async {
    final response = await http.get(Uri.parse('http://34.83.198.237/usc_mealmatch/dininghall'));

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to load dining hall data');
    }
  }

  Future<double> fetchDiningHallRating(int diningHallID) async {
    diningHallID += 1;
    final response = await http.get(Uri.parse('http://34.83.198.237/usc_mealmatch/rating/$diningHallID'));

    if (response.statusCode == 200) {
      try {
        Map<String, dynamic> jsonResponse = jsonDecode(response.body);
        return jsonResponse['rating'] as double;
      } catch (e) {
        // Handle the case when the server returns an incorrectly formatted double value
        print('Error parsing rating: $e');
        return 0.0; // Return a default value, for example 0.0
      }
    } else {
      throw Exception('Failed to load dining hall rating');
    }
  }

  Future<Map<String, dynamic>> fetchMatchedDiningHall() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? userID = prefs.getString('userID');

    if (userID != null) {
      final response = await http.get(Uri.parse('http://34.83.198.237/usc_mealmatch/match/$userID'));

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        throw Exception('Failed to load matched dining hall');
      }
    } else {
      throw Exception('User not logged in');
    }
  }


  Future<void> _checkLoginStatus() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    bool? isLoggedIn = prefs.getBool('isLoggedIn');
    setState(() {
      _isLoggedIn = isLoggedIn ?? false;
    });
  }


  Future<void> _loadLastSubmittedRatingTimestamps() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    for (int i = 0; i < _lastSubmittedRatingTimestamp.length; i++) {
      int timestamp = prefs.getInt('lastSubmittedRatingTimestamp${i + 1}') ?? 0;
      setState(() {
        _lastSubmittedRatingTimestamp[i] = timestamp;
      });
    }
  }

  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
    diningHallDataFuture = fetchDiningHallData();
    _loadLastSubmittedRatingTimestamps(); // Load the last submitted rating timestamps

    // Fetch ratings for all dining halls and store them in _ratings list
    for (int i = 0; i < _ratings.length; i++) {
      fetchDiningHallRating(i).then((rating) {
        setState(() {
          _ratings[i] = rating;
        });
      });
    }
  }




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('USCMealMatch'),
        automaticallyImplyLeading: false,
        actions: [
          _isLoggedIn
              ? IconButton(
            icon: Icon(Icons.account_circle),
            onPressed: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (context) => ProfilePage(),
                ),
              );
              // Navigate to the profile page
            },
          )
              : TextButton(
            onPressed: () {
              // Navigate to the signup/login page
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (context) => SignupLoginPage(),
                ),
              );
            },
            child: const Text(
              'Signup/Login',
              style: TextStyle(
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),

      body: FutureBuilder<Map<String, dynamic>>(
        future: diningHallDataFuture,
        builder: (BuildContext context, AsyncSnapshot<Map<String, dynamic>> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else {
            List<Map<String, dynamic>> menus = List<Map<String, dynamic>>.from(snapshot.data!['menus']);

            return SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(20.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    if (_isLoggedIn) // Add a condition to show the matched dining hall line only if the user is logged in
                      FutureBuilder<Map<String, dynamic>>(
                        future: fetchMatchedDiningHall(),
                        builder: (BuildContext context, AsyncSnapshot<Map<String, dynamic>> snapshot) {
                          if (snapshot.connectionState == ConnectionState.waiting) {
                            return SizedBox.shrink();
                          } else if (snapshot.hasError) {
                            return Text('Error: ${snapshot.error}');
                          } else {
                            int matchedDiningHallIndex = snapshot.data?['match'] ?? -1;
                            if (matchedDiningHallIndex >= 0 && matchedDiningHallIndex < menus.length) {
                              return Text(
                                'The match dining hall is ${menus[matchedDiningHallIndex]['name']}',
                                style: Theme.of(context).textTheme.headline5,
                              );
                            } else {
                              return Text(
                                'The match dining hall is Unknown',
                                style: Theme.of(context).textTheme.headline5,
                              );
                            }
                          }
                        },
                      ),

                    SizedBox(height: 20),
                    Text(
                      'Current Dining Hall Menu',
                      style: Theme.of(context).textTheme.headline6,
                    ),
                    SizedBox(height: 20),
                    Text(
                      'Menus and Ratings',
                      style: Theme.of(context).textTheme.headline6,
                    ),
                    SizedBox(height: 10),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: menus.map((menu) {
                        int index = menus.indexOf(menu);
                        return Container(
                            color: Colors.grey[200],
                            padding: const EdgeInsets.all(8),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                              Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                              Text(menu['name']),
                              Text('${_ratings[index].toStringAsFixed(1)} ⭐'), // Added rating value next to the dining hall name
                              ],
                              ),
                                SingleChildScrollView(
                                  scrollDirection: Axis.horizontal,
                                  child: ConstrainedBox(
                                    constraints: BoxConstraints(maxHeight: 500), // Set the fixed height constraint
                                    child: ClipRect(
                                      child: SingleChildScrollView(
                                        scrollDirection: Axis.vertical,
                                        child: Table(
                                          columnWidths: const {
                                            0: FixedColumnWidth(300),
                                          },
                                          children: [
                                            for (Map<String, dynamic> menuItem in menu['menu'])
                                              TableRow(
                                                children: [
                                                  Padding(
                                                    padding: const EdgeInsets.all(8.0),
                                                    child: Text(menuItem['name']),
                                                  ),
                                                ],
                                              ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                ),
                                SizedBox(height: 10),
                                if (_isLoggedIn && !_hasSubmittedRating[index] && DateTime.fromMillisecondsSinceEpoch(_lastSubmittedRatingTimestamp[index]).add(Duration(hours: 4)).isBefore(DateTime.now()))
                                // Add a condition to show the rating bar and submit button only if the user is logged in
                                  Column(
                                    children: [
                                      RatingBar.builder(
                                        initialRating: 0,
                                        minRating: 1,
                                        direction: Axis.horizontal,
                                        allowHalfRating: true,
                                        itemCount: 5,
                                        itemSize: 20,
                                        itemBuilder: (context, _) => Icon(
                                          Icons.star,
                                          color: Colors.amber,
                                        ),
                                        onRatingUpdate: (rating) {
                                          setState(() {
                                            _submittedRatings[index] = rating;
                                          });
                                        },
                                      ),
                                      SizedBox(height: 10),
                                      ElevatedButton(
                                        onPressed: () => _submitRating(index),
                                        child: Text('Submit Rating'),
                                      ),
                                    ],
                                  ),
                              ],
                            ),
                        );
                      }).toList(),
                    ),
                  ],
                ),
              ),
            );
          }
        },
      ),
    );

    }
}