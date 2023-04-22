// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: init signup page part2

import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:test_flutter/HomePage.dart';

import 'Profile_signup.dart';


class DietPreferencesPage extends StatefulWidget {
  const DietPreferencesPage({super.key});

  @override
  _DietPreferencesPageState createState() => _DietPreferencesPageState();
}

class _DietPreferencesPageState extends State<DietPreferencesPage> {
  //todo: change this list to a default list afterwards
  List<String> dishes = ['Dairy', 'Eggs', 'Fish', 'Food Not Analyzed for Allergens', 'Peanuts', 'Pork', 'Sesame', 'Shellfish', 'Soy', 'Tree Nuts', 'Vegan', 'Vegetarian', 'Wheat / Gluten'];

  List<String> selectedDishes = [];

  void _toggleDish(String dish) {
    setState(() {
      if (selectedDishes.contains(dish)) {
        selectedDishes.remove(dish);
      } else {
        selectedDishes.add(dish);
      }
    });
  }

  void _showAlertDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Error'),
          content: Text('Please select at least 6 preferences.'),
          actions: <Widget>[
            TextButton(
              child: Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _submitPreferences() async {
    if (selectedDishes.length >= 0) {
      // Save selected dishes to the server
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setStringList('diet', selectedDishes);
        Navigator.of(context).push(
          MaterialPageRoute(
            builder: (context) => SetProfilePicturePage(),
          ),
        );
    } else {
      _showAlertDialog();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Choose Diet Preferences'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            Center(
              child:Wrap(
                spacing: 10,
                runSpacing: 10,
                children: dishes
                    .map(
                      (dish) => ChoiceChip(
                    label: Text(dish),
                    selected: selectedDishes.contains(dish),
                        selectedColor: Colors.green,
                    onSelected: (_) => _toggleDish(dish),
                  ),
                )
                    .toList(),
              ),
            ),
            const Spacer(),
            ElevatedButton(
              onPressed: _submitPreferences,
              child: const Text('Save Preferences'),
            ),
          ],
        ),
      ),
    );
  }
}
