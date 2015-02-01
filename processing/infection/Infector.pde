class Infector {
  void infectAll(User user, int versionNumber) {
    if (user.versionNum != versionNumber) {
      user.versionNum = versionNumber;
      user.display();
    } else {
      return;
    }
    for (User coach : user.coaches) {
      infectAll(coach, versionNumber);
    }
    for (User student : user.students) {
      infectAll(student, versionNumber);
    }
  }
}
