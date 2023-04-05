// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: init signup page part1

import 'package:flutter/material.dart';

import 'Diet_Preference.dart';

class DishPreferencesPage extends StatefulWidget {
  const DishPreferencesPage({super.key});

  @override
  _DishPreferencesPageState createState() => _DishPreferencesPageState();
}

class _DishPreferencesPageState extends State<DishPreferencesPage> {
  //todo: change this list to a default list afterwards
  List<String> dishes = [
    'Buttermilk Pancakes',
    'Scrambled Eggs',
    'Boiled Eggs',
    'Bacon',
    'Triangle Hash Browns',
    'Turkey Maple Sausage Links',
    'Fried Plantains',
    'Cheese Pizza',
    'MADE TO ORDER OMELETTES AVAILABLE UNTIL 1PM',
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
      // Save selected dishes to the server
      // and navigate to the next page
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (context) => const DietPreferencesPage(),
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
        title: const Text('Choose Dish Preferences'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Center(
              child:Wrap(
                crossAxisAlignment: WrapCrossAlignment.center,
                spacing: 10,
                runSpacing: 10,
                children: dishes
                    .map(
                      (dish) => ChoiceChip(
                        selectedColor: Colors.green,
                    backgroundColor: Colors.grey,
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
                //todo
                // Save selected dishes to the server
                // and navigate to the next page
              child: const Text('Save Preferences'),
            ),
          ],
        ),
      ),
    );
  }
}
