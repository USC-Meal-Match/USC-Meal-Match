// Author: Changzhong Qian
// Last Updated Date: April 18th, 2023
// Description: Main page

import 'dart:convert';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:test_flutter/HomePage.dart';
 // Import the Home.dart file here

class SetProfilePicturePage extends StatefulWidget {
  @override
  _SetProfilePicturePageState createState() => _SetProfilePicturePageState();
}

class _SetProfilePicturePageState extends State<SetProfilePicturePage> {
  TextEditingController _controller = TextEditingController();
  String _profileImageUrl = 'https://pbs.twimg.com/media/D48yGyTUUAAXstq?format=jpg&name=4096x4096';
  late String _userID; // Replace with the actual user ID
  List<String> _DishPreferences = []; // Define the variable for dish preferences
  List<String> _DietPreferences = []; // Define the variable for diet preferences


  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addPostFrameCallback((_) {
      _checkLoginStatus();
    });
  }

  Future<void> _checkLoginStatus() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? userID = prefs.getString('userID');
    if (userID != null) {
      setState(() {
        _userID = userID;
      });
    } else {
      print('User ID not found in SharedPreferences');
    }
    List<String> ? DishPreferences = prefs.getStringList('dish');
    List<String> ? DietPreferences = prefs.getStringList('diet');
    _DishPreferences = DishPreferences!;
    _DietPreferences = DietPreferences!;
  }

  Future<void> updateProfileImage() async {
    final response = await http.post(
      Uri.parse('http://34.83.198.237/usc_mealmatch/profile'),
      body: json.encode({
        'userID': _userID,
        'picURL': _profileImageUrl,
        'pref': _DishPreferences,
        'dietRstr': _DietPreferences
      }),
      headers: {'Content-Type': 'application/json'},
    );
    print(response.statusCode);
    if (response.statusCode != 200) {
      throw Exception('Failed to update profile');
    }
  }

  void _navigateToHomePage() async {
    await updateProfileImage();
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) =>  HomePage(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Set Profile Picture'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            CircleAvatar(
              backgroundImage: NetworkImage(_profileImageUrl),
              radius: 50,
            ),
            SizedBox(height: 20),
            Container(
              width: 250, // Set the width of the input field
              child: TextField(
                controller: _controller,
                decoration: InputDecoration(
                  labelText: 'Image URL',
                  hintText: 'Enter the URL of your profile picture',
                ),
                onChanged: (value) {
                  if (value.isNotEmpty) {
                    setState(() {
                      _profileImageUrl = value;
                    });
                  } else {
                    setState(() {
                      _profileImageUrl = 'https://pbs.twimg.com/media/D48yGyTUUAAXstq?format=jpg&name=4096x4096';
                    });
                  }
                },
              ),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _navigateToHomePage,
              child: Text('Next'),
            ),
          ],
        ),
      ),
    );
  }
}
