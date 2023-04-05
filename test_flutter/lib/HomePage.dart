// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Home Page

import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'Profile.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late Future<Map<String, dynamic>> diningHallDataFuture;
  List<double> _ratings = [0, 0, 0];

  void _submitRating(int index) {
    // Send the rating to the backend
    //todo!!!!!!!!
  }

  //todo: to be fix!!!!!!
  Future<Map<String, dynamic>> fetchDiningHallData() async {
    await Future.delayed(Duration(seconds: 2)); // Simulate network delay
    return {
      'diningHallName': 'Example Dining Hall',
      'menus': [
        {'name': 'Parkside Dining Hall', 'items': ['Pizza', 'Burger', 'Salad','Pizza', 'Burger']},
        {'name': 'EveryBody\'s Kitchen', 'items': ['Pasta', 'Soup', 'Sandwich','Pizza', 'Burger']},
        {'name': 'Village Dining Hall', 'items': ['Fries', 'Nuggets', 'Wrap', 'Pizza', 'Burger']},
      ],
    };
  }

  @override
  void initState() {
    super.initState();
    diningHallDataFuture = fetchDiningHallData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Home'),
        automaticallyImplyLeading: false,
        actions: [
          IconButton(
            icon: Icon(Icons.account_circle),
            onPressed: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (context) => ProfilePage(),
                ),
              );
              // Navigate to the profile page
            },
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
            String diningHallName = snapshot.data!['diningHallName'];
            List<Map<String, dynamic>> menus = List<Map<String, dynamic>>.from(snapshot.data!['menus']);

            return SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(20.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'The match dining hall is $diningHallName',
                      style: Theme.of(context).textTheme.headline5,
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
                      children: menus.map((menu) {
                        int index = menus.indexOf(menu);
                        return Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Text(menu['name']),
                            Table(
                              columnWidths: const {
                                0: FixedColumnWidth(200),
                              },
                              children: [
                                for (String menuItem in menu['items'])
                                  TableRow(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Text(menuItem),
                                      ),
                                    ],
                                  ),
                              ],
                            ),
                            SizedBox(height: 10),
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
                                  _ratings[index] = rating;
                                });
                              },
                            ),
                            SizedBox(height: 10),
                            ElevatedButton(
                              onPressed: () => _submitRating(index),
                              child: Text('Submit Rating'),
                            ),
                          ],
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