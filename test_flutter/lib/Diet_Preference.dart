// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: init signup page part2

import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:test_flutter/HomePage.dart';


class DietPreferencesPage extends StatefulWidget {
  const DietPreferencesPage({super.key});

  @override
  _DietPreferencesPageState createState() => _DietPreferencesPageState();
}

class _DietPreferencesPageState extends State<DietPreferencesPage> {
  //todo: change this list to a default list afterwards
  List<String> dishes = [
    'Vegan',
    'Eggs',
    'Fish',
    'Sesame',
    'ShellFish',
    'Vegetarian',
    'Wheat/ Gluten',
    'Pork',
  ];

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

  void _submitPreferences() {
    if (selectedDishes.length >= 6) {
      //Todo:!!!
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (context) => HomePage(),
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
