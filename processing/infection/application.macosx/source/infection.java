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

ArrayList<User> allUsers = new ArrayList<User>();
ArrayList<User> allClasses = new ArrayList<User>();
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

public void draw() {
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

public void mousePressed() {
  for(User user : allUsers) {
    if (user.overMe()) {
      //tried using framerate to slow down the visualization but that didn't work
      infector.infectAll(user, user.versionNum + 1);
    }
  }
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
  
  public boolean overMe()  {
    if (mouseX >= x - 25 && mouseX <= x+25 &&  mouseY >= y - 25 && mouseY <= y+25) {
      return true;
    } else {
      return false;
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
