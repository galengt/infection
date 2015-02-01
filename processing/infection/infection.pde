ArrayList<User> allUsers = new ArrayList<User>();
ArrayList<User> allClasses = new ArrayList<User>();
Infector infector = new Infector();

void setup() {
  size(750,750);
  //5 classes
  for (int j = 0; j < 5; j++) {
    User coach = new User(250, 125 * j + 30, "coach" + j);
    // 1 coach and 10 students per class
    for (int i = 0; i < 10; i++) {
      User student = new User(50 * i + 30, 80 + 125 * j, "student" + j + i);
      student.coaches.add(coach);
      coach.students.add(student);
      allUsers.add(student);
    }
    allUsers.add(coach);
    allClasses.add(coach);
  }
  //connect two of the classes
  User tutor = new User(600, 200, "tutor");
  User studentFromClassOne = allClasses.get(0).students.get(9);
  studentFromClassOne.coaches.add(tutor);
  tutor.students.add(studentFromClassOne);
  User studentFromClassTwo = allClasses.get(1).students.get(9);
  studentFromClassTwo.coaches.add(tutor);
  tutor.students.add(studentFromClassTwo);
  allUsers.add(tutor);
  allClasses.add(tutor);
}

void draw() {
  background(255,255,255);
  text("Click a user to give", 550, 300);
  text("them the next version", 550, 325);
  for (User coach : allClasses) {
    coach.display();
    for (User student : coach.students) {
      student.display();
    }
  }
}

void mousePressed() {
  for(User user : allUsers) {
    if (user.overMe()) {
      //tried using framerate to slow down the visualization but that didn't work
      infector.infectAll(user, user.versionNum + 1);
    }
  }
}
