// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: init signup page part1

import 'dart:convert';
import 'Profile_signup.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'Diet_Preference.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'Dish_Preference.dart';

class DishPreferencesPage extends StatefulWidget {
  const DishPreferencesPage({super.key});

  @override
  _DishPreferencesPageState createState() => _DishPreferencesPageState();
}

class _DishPreferencesPageState extends State<DishPreferencesPage> {
  //todo: change this list to a default list afterwards
  List<String> dishes = ['Char Siu Pork', 'Harissa Lemon Roasted Chicken',
    'Steamed Red Rice', 'Cheese Pizza', 'BBQ Pulled Pork',
    'Chow Mein Noodles', 'Flank Steak with Broccoli',
    'Oven Roasted Mushroom Soup', 'Beef and Pork Meatballs',
    'Popcorn Chicken', 'Garlic Mashed Potatoes', 'Tuna Salad',
    'Plain Green Tea Soba Noodles', 'Boston Clam Chowder', 'Chicken and Biscuits',
    'Plain Crepe', 'Banana Pancakes', 'Oatmeal',  'Sliced Turkey', 'Corn on the Cob', 'Plain Pasta', 'Bean Sprouts'];

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
          content: Text('Please select at least 6 preferences to help us match the best dining hall for you.'),
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
    if (selectedDishes.length >= 6) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setStringList('dish', selectedDishes);
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (context) =>  const DietPreferencesPage(),
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
