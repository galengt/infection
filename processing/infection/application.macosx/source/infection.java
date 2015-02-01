import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class infection extends PApplet {

ArrayList<User> graphs = new ArrayList<User>();
Infector infector = new Infector();

public void setup() {
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

public void draw() {
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
class Infector {
  public void infectAll(User user, int versionNumber) {
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
class User {
  int x, y, versionNum;
  String userName;
  ArrayList<User> coaches = new ArrayList<User>();
  ArrayList<User> students = new ArrayList<User>();
  
  User(int xPos, int yPos, String name) {
    x = xPos;
    y = yPos;
    userName = name;
  }
  
  public void display() {
    int num = versionNum % 3;
    int[] rgb = {0,0,0};
    rgb[num] = 255;
    fill(rgb[0], rgb[1], rgb[2]);
    ellipse(x,y,25,25);
    textSize(15);
    //draw a little rectangle over the older version
    fill(255,255,255);
    noStroke();
    rect(x - 10, y + 15, 27, 20);
    fill(0,0,0);
    text("V_" + versionNum, x - 10, y + 30);
    stroke(0,0,0);
    for (User coach : coaches) {
      strokeWeight(1);
      line(coach.x, coach.y, x, y);
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "infection" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
