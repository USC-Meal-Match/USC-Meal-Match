// Author: Changzhong Qian
// Last Updated Date: April 2nd, 2023
// Description: signup log in page

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'Dish_Preference.dart';
import 'HomePage.dart';

class SignupLoginPage extends StatefulWidget {
  const SignupLoginPage({super.key});

  @override
  _SignupLoginPageState createState() => _SignupLoginPageState();
}

class _SignupLoginPageState extends State<SignupLoginPage> {
  final GlobalKey<FormBuilderState> _formKey = GlobalKey<FormBuilderState>();
  bool _isSignup = true;

  void _toggleView() {
    setState(() {
      _isSignup = !_isSignup;
    });
  }

  Future<void> _submitForm() async {
    if (_formKey.currentState!.saveAndValidate()) {
      final formData = _formKey.currentState!.value;

      if (_isSignup) {
        // Signup logic
        final response = await http.post(
          Uri.parse('http://34.83.198.237/usc_mealmatch/signup'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({
            'email': formData['email'],
            'password': formData['password'],
          }),
        );

        if (response.statusCode == 200) {
          final jsonResponse = jsonDecode(response.body);

          if (jsonResponse['signup']) {
            SharedPreferences prefs = await SharedPreferences.getInstance();
            await prefs.setString('userID', jsonResponse['userID'].toString());
            await prefs.setBool('isLoggedIn', true);

            // Successfully signed up
            // Navigate to the next page
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => DishPreferencesPage(),
              ),
            );
          } else {
            // Handle signup error: user already exists
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return AlertDialog(
                  title: Text('Signup Error'),
                  content: Text('User already exists.'),
                  actions: [
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                      child: Text('OK'),
                    ),
                  ],
                );
              },
            );
          }
        } else {
          // Handle signup error: server error (HTTP 400)
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AlertDialog(
                title: Text('Signup Error'),
                content: Text('User already exists.'),
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text('OK'),
                  ),
                ],
              );
            },
          );
        }
      } else {
        // Login logic
        final response = await http.post(
          Uri.parse('http://34.83.198.237/usc_mealmatch/login'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({
            'email': formData['email'],
            'password': formData['password'],
          }),
        );

        if (response.statusCode == 200) {
          final jsonResponse = jsonDecode(response.body);
          if (jsonResponse['authenticated']) {
            SharedPreferences prefs = await SharedPreferences.getInstance();
            await prefs.setString('userID', jsonResponse['userID']);
            await prefs.setBool('isLoggedIn', true);

            // Successfully logged in
            // Navigate to the next page
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => HomePage(),
              ),
            );
          } else {
            // Handle login error
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return AlertDialog(
                  title: Text('Login Error'),
                  content: Text('Invalid username or password.'),
                  actions: [
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                      child: Text('OK'),
                    ),
                  ],
                );
              },
            );
          }
        } else {
          // Handle login error
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AlertDialog(
                title: Text('Login Error'),
                content: Text('Invalid username or password.'),
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text('OK'),
                  ),
                ],
              );
            },
          );
        }
      }
    }
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_isSignup ? 'Signup' : 'Login'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: FormBuilder(
          key: _formKey,
          child: Column(
            children: [
              FormBuilderTextField(
                name: 'email',
                decoration: const InputDecoration(labelText: 'Email'),
                keyboardType: TextInputType.emailAddress,
                validator: FormBuilderValidators.compose([
                  FormBuilderValidators.required(),
                  FormBuilderValidators.email(),
                ]),
              ),
              const SizedBox(height: 20),
              FormBuilderTextField(
                name: 'password',
                decoration: const InputDecoration(labelText: 'Password'),
                obscureText: true,
                validator: FormBuilderValidators.compose([
                  FormBuilderValidators.required(),
                  FormBuilderValidators.minLength(6),
                ]),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _submitForm,
                child: Text(_isSignup ? 'Sign up' : 'Log in'),
              ),
              TextButton(
                onPressed: _toggleView,
                child: Text(_isSignup
                    ? 'Already have an account? Log in'
                    : "Don't have an account? Sign up"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
