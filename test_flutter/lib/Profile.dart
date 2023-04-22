// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Profile page

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:cached_network_image/cached_network_image.dart';


class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}



//todo!!!!!!! get backend
class _ProfilePageState extends State<ProfilePage> {
  List<String> _DishPreferences = []; // Define the variable for dish preferences
  List<String> _DietPreferences = []; // Define the variable for diet preferences
  late String _userID; // Replace with the actual user ID
  String _profileImageUrl = ''; // Updated to an empty string

  Future<Map<String, dynamic>> getProfile(String userID) async {
    final response = await http.get(
      Uri.parse('http://34.83.198.237/usc_mealmatch/profile/$userID'),
      headers: {'Content-Type': 'application/json'},
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to load profile');
    }
  }

  Future<void> _checkLoginStatus() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? userID = prefs.getString('userID');
    if (userID != null) {
      setState(() {
        _userID = userID;
      });
      _loadProfileData();
    } else {
      print('User ID not found in SharedPreferences');
    }
  }

  Future<void> updateProfile() async {
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

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addPostFrameCallback((_) {
      _checkLoginStatus();
    });
    _loadProfileData();
  }

  Future<void> _loadProfileData() async {
    try {
      Map<String, dynamic> profileData = await getProfile(_userID);
      setState(() {
        _profileImageUrl = profileData['picURL'];
        _DishPreferences = List<String>.from(profileData['pref']);
        _DietPreferences = List<String>.from(profileData['dietRstr']);
      });
    } catch (e) {
      print('Error loading profile data: $e');
    }
  }

  Future<void> _showAddPreferenceDialog(String type) async {
    bool isDish = false;
    TextEditingController _controller = TextEditingController();
    if(type == "dish") isDish = true;

    return showDialog<void>(
      context: context,
      barrierDismissible: false, // User must tap a button to close the dialog.
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Add Preference'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                if(isDish) Text('Please enter the name of the dish you want to add to your preferences:')
                else Text('Please enter the diet preference you want to add:'),
                TextField(
                  controller: _controller,
                  decoration: InputDecoration(labelText: 'Preference'),
                ),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Add'),
              onPressed: () {
                // Implement the logic for adding the preference here.
                String newPreference = _controller.text.trim();
                if (newPreference.isNotEmpty) {
                  setState(() {
                    if(!isDish) {
                      _DietPreferences.add(newPreference);
                    } else {
                      _DishPreferences.add(newPreference);
                    }
                  });
                }
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> _showChangeProfilePictureDialog() async {
    TextEditingController _controller = TextEditingController();

    return showDialog<void>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Change Profile Picture'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text('Please enter the URL of the new profile picture:'),
                TextField(
                  controller: _controller,
                  decoration: InputDecoration(labelText: 'Image URL'),
                ),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Change'),
              onPressed: () async {
                String newImageUrl = _controller.text.trim();
                if (newImageUrl.isNotEmpty) {
                  setState(() {
                    _profileImageUrl = newImageUrl;
                  });
                  await updateProfile();
                }
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }


  Future<void> _logout() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    await prefs.setBool('isLoggedIn', false);
    Navigator.pushNamedAndRemoveUntil(context, '/home', (route) => false);
  }


  void _addDishPreference() {
    // Implement logic to add a preference
    _showAddPreferenceDialog("dish").then((_) async {
      await updateProfile();
      print('Add preference');
    });
  }

  void _addDietPreference() {
    // Implement logic to add a preference
    _showAddPreferenceDialog("diet").then((_) async {
      await updateProfile();
      print('Add preference');
    });
  }

  void _deleteDishPreference(String preference) {
    // Implement logic to delete a preference
    print('Delete preference: $preference');
    setState(() {
      _DishPreferences.remove(preference);
    });
    updateProfile();
  }

  void _deleteDietPreference(String preference) {
    // Implement logic to delete a preference
    print('Delete preference: $preference');
    setState(() {
      _DietPreferences.remove(preference);
    });
    updateProfile();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile'),
        actions: [
          TextButton(
            onPressed: _logout,
            child: Text(
              'Logout',
              style: TextStyle(color: Colors.white),
            ),
          ),
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: EdgeInsets.all(20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Center(
                child: Column(
                  children: [
                    CircleAvatar(
                      backgroundImage: NetworkImage(
                        _profileImageUrl,
                      ),
                      radius: 50,
                    ),
                    TextButton(
                      onPressed: () {
                        // Implement logic to change profile picture
                        _showChangeProfilePictureDialog;
                      },
                      child: TextButton(
                        onPressed: _showChangeProfilePictureDialog,
                        child: Text('Change profile picture'),
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 20),
              Text(
                'Dish Preferences',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              SizedBox(height: 10),
              Wrap(
                spacing: 8,
                runSpacing: 8,
                children: _DishPreferences
                    .map((preference) => Chip(
                  label: Text(preference),
                  onDeleted: () => _deleteDishPreference(preference),
                ))
                    .toList(),
              ),
              SizedBox(height: 10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton.icon(
                    onPressed: _addDishPreference,
                    icon: Icon(Icons.add),
                    label: Text('Add'),
                  ),
                  SizedBox(width: 10),
                ],
              ),
              Text(
                'Diet Preferences',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              SizedBox(height: 10),
              Wrap(
                spacing: 8,
                runSpacing: 8,
                children: _DietPreferences
                    .map((preference) => Chip(
                  label: Text(preference),
                  onDeleted: () => _deleteDietPreference(preference),
                ))
                    .toList(),
              ),
              SizedBox(height: 10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton.icon(
                    onPressed: _addDietPreference,
                    icon: Icon(Icons.add),
                    label: Text('Add'),
                  ),
                  SizedBox(width: 10),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
