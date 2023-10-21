import 'package:flutter/material.dart';
import 'package:login_project/login_project/model/account_settings_person_data.dart';
import 'package:login_project/login_project/pages/accepted_login_page.dart';
import 'package:login_project/login_project/pages/login_page.dart';
import 'package:login_project/login_project/view/content/components/account_settings_textfield_component.dart';
import 'package:http/http.dart' as http;

class AccountSettingsPage extends StatefulWidget {
  String username;
  String password;

  AccountSettingsPage({
    required this.username,
    required this.password,
    Key? key,
  }) : super(key: key);

  @override
  State<AccountSettingsPage> createState() => _AccountSettingsPageState();
}

class _AccountSettingsPageState extends State<AccountSettingsPage> {
  bool showPassword = true;
  final TextEditingController _username = TextEditingController();
  final TextEditingController _password = TextEditingController();

  @override
  void initState() {
    super.initState();
    _username.text = widget.username;
    _password.text = widget.password;
  }

  bool _isPressed(bool showPassword) {
    if (showPassword == false) {
      return false;
    } else {
      return true;
    }
  }

  String domain = 'localhost';

  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;

    return GestureDetector(
      onTap: () {
        return FocusManager.instance.primaryFocus?.unfocus();
      },
      child: Scaffold(
        body: Column(
          children: [
            Container(
              height: MediaQuery.of(context).padding.top,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                TextButton(
                  child: const Text(
                    'Back',
                    textAlign: TextAlign.start,
                  ),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) {
                          return const LoginPage(
                          );
                        },
                      ),
                    );
                  },
                ),
              ],
            ),
            Expanded(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Row(
                      children: [
                        Container(
                          padding: const EdgeInsets.only(
                              left: 20, bottom: 20, right: 5),
                          child: const Text(
                            "Account Settings",
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 30,
                            ),
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.only(
                            bottom: 20,
                            right: 5,
                          ),
                          child: Icon(
                            Icons.settings,
                            color: Colors.black,
                            size: height / 28,
                          ),
                        ),
                      ],
                    ),
                    AccountSettingsTextField(
                      padding: const EdgeInsets.only(
                        top: 7.5,
                        left: 20,
                        right: 20,
                        bottom: 7.5,
                      ),
                      size: Size.fromHeight(height),
                      controller: _username,
                      readOnly: true,
                      text: 'username',
                      icon: Icon(Icons.email_outlined),
                    ),
                    Container(
                      padding: const EdgeInsets.only(
                        top: 7.5,
                        left: 20,
                        right: 20,
                        bottom: 7.5,
                      ),
                      height: height / 12,
                      child: TextField(
                        controller: _password,
                        obscureText: _isPressed(showPassword),
                        decoration: InputDecoration(
                          border: const OutlineInputBorder(),
                          prefixIcon: const Icon(Icons.lock_outline),
                          suffix: TextButton(
                            onPressed: () {
                              showPassword =
                                  (showPassword == false ? true : false);
                              _isPressed(showPassword);
                              setState(() {});
                            },
                            child: const Icon(
                              Icons.remove_red_eye_outlined,
                            ),
                          ),
                          labelText: 'Password',
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
