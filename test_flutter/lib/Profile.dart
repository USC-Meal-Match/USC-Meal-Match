// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Profile page

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}

//todo!!!!!!! get backend
class _ProfilePageState extends State<ProfilePage> {
  String _profileImageUrl =
      'https://courses.uscden.net/d2l/lp/login/picture?fileSystemType=Database&fileId=41&v=20.23.3.17072'; // Placeholder profile image URL
  List<String> _DishPreferences = [
    'Pizza',
    'Burger',
    'Salad',
    'Soup',
    'Pasta',
  ]; // Sample user preferences
  List<String> _DietPreferences = [
    'Pizza',
    'Burger',
    'Salad',
    'Soup',
    'Pasta',
  ];

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


  void _addDishPreference() {
    // Implement logic to add a preference
    _showAddPreferenceDialog("dish");
    print('Add preference');
  }

  void _addDietPreference() {
    // Implement logic to add a preference
    _showAddPreferenceDialog("diet");
    print('Add preference');
  }

  void _deleteDishPreference(String preference) {
    // Implement logic to delete a preference
    print('Delete preference: $preference');
    setState(() {
      _DishPreferences.remove(preference);
    });
  }

  void _deleteDietPreference(String preference) {
    // Implement logic to delete a preference
    print('Delete preference: $preference');
    setState(() {
      _DietPreferences.remove(preference);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile'),
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
                      backgroundImage: NetworkImage(_profileImageUrl),
                      radius: 50,
                    ),
                    TextButton(
                      onPressed: () {
                        // Implement logic to change profile picture
                        print('Change profile picture');
                      },
                      child: Text('Change profile picture'),
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
