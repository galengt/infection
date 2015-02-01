ArrayList<User> graphs = new ArrayList<User>();
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
    }
    graphs.add(coach);
  }
  //connect two of the classes
  User tutor = new User(600, 200, "tutor");
  User studentFromClassOne = graphs.get(0).students.get(9);
  studentFromClassOne.coaches.add(tutor);
  tutor.students.add(studentFromClassOne);
  User studentFromClassTwo = graphs.get(1).students.get(9);
  studentFromClassTwo.coaches.add(tutor);
  tutor.students.add(studentFromClassTwo);
  graphs.add(tutor);
}

void draw() {
  background(255,255,255);
  for (User coach : graphs) {
    coach.display();
    for (User student : coach.students) {
      student.display();
    }
  }
  infector.infectAll(graphs.get(5), 1); // the tutor
  noLoop();
}
