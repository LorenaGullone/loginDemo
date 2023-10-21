import 'package:flutter/material.dart';
import 'package:login_project/login_project/model/user_data.dart';
import 'package:login_project/login_project/pages/accepted_login_page.dart';
import 'package:login_project/login_project/pages/sign_up_page.dart';
import 'package:http/http.dart' as http;

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  bool showPassword = true;
  bool errorLoginMessage = false;
  bool usernameValid = true;
  String errorMessage = '';

  final TextEditingController _username = TextEditingController();
  final TextEditingController _password = TextEditingController();

  bool _isPressed(bool showPassword) {
    if (showPassword == false) {
      return false;
    } else {
      return true;
    }
  }

  bool emptyFields(String valor) {
    return (valor == null || valor == '') ? true : false;
  }

  String domain = "localhost";

  _login(PersonData login) async {
    usernameValid = RegExp(
            r'^[a-zA-Z0-9_]{1,20}$')
        .hasMatch(login.username);

    if (emptyFields(login.username) || emptyFields(login.password)) {
      setState(() {
        errorMessage = 'Username or password is empty.';
        errorLoginMessage = true;
      });
    } else if (!usernameValid) {
      setState(() {
        errorMessage = 'Choose a valid username';
        errorLoginMessage = true;
      });
    } else {
      String username = login.username;
      String password = login.password;
      String urlString = "http://${domain}:8081/home/login1/${username}/${password}";
      Uri url = Uri.parse(urlString);

      http.Response response;
      response = await http.get(url);
      //response = await http.post(url, body: login.toJson());

      if (response.statusCode == 200) {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) {
              return AcceptedLoginPage(
                username: login.username,
                password: login.password,
              );
            },
          ),
        );
      } else if (response.statusCode == 409) {
        setState(() {
          errorMessage = response.body;
          errorLoginMessage = true;
        });
      } else {
        setState(() {
          errorMessage = response.body;
          errorLoginMessage = true;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;
    //double width = MediaQuery.of(context).size.width;

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
            Expanded(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Container(
                      padding: EdgeInsets.only(
                        left: height / 15,
                      ),
                      child: const Text(
                        'Login',
                        textAlign: TextAlign.left,
                        style: TextStyle(
                          fontWeight: FontWeight.w900,
                          fontSize: 35,
                        ),
                      ),
                    ),
                    Container(
                      padding: EdgeInsets.only(
                        top: 10,
                        bottom: 20,
                        left: height / 15,
                      ),
                      child: const Text(
                        'Enter your details below to continue',
                        textAlign: TextAlign.left,
                        style: TextStyle(
                          fontWeight: FontWeight.w400,
                          fontSize: 15,
                        ),
                      ),
                    ),
                    Center(
                      child: Container(
                        padding: const EdgeInsets.only(
                          left: 20,
                          right: 20,
                          bottom: 10,
                        ),
                        child: Visibility(
                          child: Text(
                            errorMessage,
                            style: const TextStyle(
                              color: Colors.red,
                            ),
                          ),
                          visible: errorLoginMessage,
                        ),
                      ),
                    ),
                    Container(
                      padding: const EdgeInsets.only(
                        top: 7.5,
                        left: 20,
                        right: 20,
                        bottom: 5,
                      ),
                      height: height / 10,
                      child: TextField(
                        controller: _username,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          prefixIcon: Icon(Icons.email_outlined),
                          labelText: 'Username',
                        ),
                      ),
                    ),
                    Container(
                      padding: const EdgeInsets.only(
                        top: 5,
                        left: 20,
                        right: 20,
                        bottom: 7.5,
                      ),
                      height: height / 11,
                      child: TextField(
                        controller: _password,
                        obscureText: _isPressed(showPassword),
                        decoration: InputDecoration(
                          border: const OutlineInputBorder(),
                          prefixIcon: const Icon(
                            Icons.lock_outline,
                          ),
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
                    Container(
                      padding: const EdgeInsets.only(
                        top: 7.5,
                        left: 20,
                        right: 20,
                        bottom: 7.5,
                      ),
                      height: height / 12,
                      child: ElevatedButton(
                        child: const Text('Log-in'),
                        onPressed: () {
                          PersonData login = PersonData(
                            _username.text,
                            _password.text,
                          );
                          _login(login);
                        },
                      ),
                    ),
                    Container(
                      padding: const EdgeInsets.only(
                        left: 20,
                        right: 20,
                        bottom: 7.5,
                      ),
                      height: height / 15,
                      child: TextButton(
                        child: const Text('Create account?'),
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) {
                                return const SignUpPage();
                              },
                            ),
                          );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
