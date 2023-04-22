// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Main page

import 'package:flutter/material.dart';
import 'package:test_flutter/HomePage.dart';
import 'Profile.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return  MaterialApp(
      theme: ThemeData(
        colorScheme: ColorScheme.fromSwatch().copyWith(
          primary: const Color(0xFF990000),
          secondary: const Color(0xFF990000),

        ),
      ),
      routes: {
        '/home': (context) => HomePage(), // Define the HomePage route
        '/profile': (context) => ProfilePage(), // Define the ProfilePage route
      },
      home: HomePage(),
    );
  }
}
