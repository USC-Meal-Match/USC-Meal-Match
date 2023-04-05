// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: Main page

import 'package:flutter/material.dart';
import 'SignupLoginPage.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: SignupLoginPage(),
    );
  }
}
