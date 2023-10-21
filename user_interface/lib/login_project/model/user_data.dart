class PersonData {
  String _username;
  String _password;

  PersonData(
    this._username,
    this._password,
  );

  String get username => _username;

  set username(String username) {
    _username = username;
  }

  String get password => _password;

  set password(String password) {
    _password = password;
  }

  Map<String, String> toJson() {
    return {
      'username': this._username,
      'password': this._password,
    };
  }
}
