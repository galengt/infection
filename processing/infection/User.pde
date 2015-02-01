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
  
  void display() {
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
