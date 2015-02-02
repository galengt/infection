package infect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.User;

import org.junit.Test;

public class InfectorImplTests {

    private Infector infector = new InfectorImpl();
    
    /************************************************************************
     * Infect the whole class
     ***********************************************************************/
    
    @Test public void infectAll() {
        long start = System.currentTimeMillis();
        int numCoaches = 1;
        int numStudents = 3;
        System.out.println("Creating a class with " + numCoaches + " coaches and " + numStudents + " students");
        User user = createClass(numCoaches,numStudents, "0");
        System.out.println("Infecting class");
        int numInfected = infector.infectAll(user, 1);
        System.out.println("Infected " + numInfected + " users");
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    @Test public void infectAll1() {
        long start = System.currentTimeMillis();
        int numCoaches = 2;
        int numStudents = 10;
        System.out.println("Creating a class with " + numCoaches + " coaches and " + numStudents + " students");
        User user = createClass(numCoaches,numStudents, "0");
        System.out.println("Infecting class");
        int numInfected = infector.infectAll(user, 1);
        System.out.println("Infected " + numInfected + " users");
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    @Test public void infectAll2() {
        long start = System.currentTimeMillis();
        int numCoaches = 40;
        int numStudents = 300;
        System.out.println("Creating a class with " + numCoaches + " coaches and " + numStudents + " students");
        User user = createClass(numCoaches,numStudents, "0");
        System.out.println("Infecting class");
        int numInfected = infector.infectAll(user, 1);
        System.out.println("Infected " + numInfected + " users");
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    //this is about the upper limit of what I can run on my machine
    @Test public void infectAllLarge() {
        long start = System.currentTimeMillis();
        int numCoaches = 200;
        int numStudents = 6000;
        System.out.println("Creating a class with " + numCoaches + " coaches and " + numStudents + " students");
        User user = createClass(numCoaches,numStudents, "0");
        System.out.println("Infecting class");
        int numInfected = infector.infectAll(user, 1);
        System.out.println("Infected " + numInfected + " users");
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    /************************************************************************
     * Infect the whole class for edge case classes
     ***********************************************************************/
    
    @Test public void infectAllInConnectedClasses() {
        long start = System.currentTimeMillis();
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        User coachOfClass1 = createClassesConnectedByTutor(2, 30);
        System.out.println("Infecting classes");
        int numInfected = infector.infectAll(coachOfClass1, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(2 * 32 + 1, numInfected);
        assertTrue(coachOfClass1.getVersionNumber() == 1);
        for (User student : coachOfClass1.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    @Test public void studentIsAlsoTeacher() {
        long start = System.currentTimeMillis();
        System.out.println("Creating two classes with " + 1 + " coach and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        User coach = createClassWithStudentTeacher(1, 30);
        System.out.println("Infecting class");
        int numInfected = infector.infectAll(coach, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(2 * 31, numInfected);
        assertTrue(coach.getVersionNumber() == 1);
        for (User student : coach.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    //TODO Some students in multiple classes
    
    /************************************************************************
     * Infect each class until you reach/pass the target
     * 
     * allUsers is not actually all users, rather one user from each class, so infectAll should reach all users
     ***********************************************************************/
    
    @Test public void nearlyLimitedInfection() {
        long start = System.currentTimeMillis();
        Set<User> allUsers = new HashSet<User>();
        System.out.println("Creating 3 classes with " + 1 + " coach and " + 3 + " students each");
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Infecting classes with nearlyLimitedInfection and limit of 5");
        int numInfected = infector.nearlyLimitedInfection(allUsers, 5, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(8,numInfected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    @Test public void nearlyLimitedInfection1() {
        long start = System.currentTimeMillis();
        Set<User> allUsers = new HashSet<User>();
        System.out.println("Creating 3 classes with " + 2 + " coaches and " + 3 + " students each");
        for (int i = 0; i < 3; i++) {
            User user = createClass(2,5,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Infecting classes with nearlyLimitedInfection and limit of 12");
        int numInfected = infector.nearlyLimitedInfection(allUsers, 12, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(14,numInfected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    

    @Test public void nearlyLimitedInfection2() {
        long start = System.currentTimeMillis();
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 10; i++) {
            User user = createClass(2,30,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Infecting classes with nearlyLimitedInfection and limit of 100");
        int numInfected = infector.nearlyLimitedInfection(allUsers, 100, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(128,numInfected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    

    @Test public void nearlyLimitedInfectionWithUnusualClassesAndHighMax() {
        long start = System.currentTimeMillis();
        Set<User> allUsers = new HashSet<User>();
        System.out.println("Creating a class with " + 2 + " coach and " + 30 + " students");
        allUsers.add(createClass(2,30, "0"));
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30));
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30));
        System.out.println("Infecting classes with nearlyLimitedInfection and limit of 1000");
        int numInfected = infector.nearlyLimitedInfection(allUsers, 1000, 1);
        System.out.println("Infected " + numInfected + " users");
        assertEquals(32 + 65 + 64, numInfected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    @Test public void nearlyLimitedInfectionWithUnusualClasses() {
        long start = System.currentTimeMillis();
        Set<User> allUsers = new HashSet<User>();
        System.out.println("Creating a class with " + 2 + " coach and " + 30 + " students");
        allUsers.add(createClass(2,30, "0")); // infects 32
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30)); //infects 65
        System.out.println("Creating two classes with " + 1 + " coaches and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        System.out.println("Infecting classes with nearlyLimitedInfection and limit of 100");
        int numInfected = infector.nearlyLimitedInfection(allUsers, 100, 1);
        System.out.println("Infected " + numInfected + " users");
        //just a quirk of hashing that determines order in which each group is infected
        assertEquals(129, numInfected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    /************************************************************************
     * Infect each class until you hit the target, or fail
     ***********************************************************************/
     
    @Test public void limitedInfectionPass() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        // 3 classes of 4 users
        System.out.println("Creating 3 classes with " + 1 + " coach and " + 3 + " students each");
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Infecting classes with limitedInfection and limit of 4");
        boolean infected = infector.limitedInfection(allUsers, 4, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(infected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
        //can't know which graphs were infected
        /*for (User user : allUsers) {
            assertTrue(user.getVersionNumber() == 1);
            for (User student : user.getStudents()) {
                assertTrue(student.getVersionNumber() == 1);
            }
        }*/
    }
    
    @Test public void limitedInfectionFail() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        // 3 classes of 4 users
        System.out.println("Creating 3 classes with " + 1 + " coach and " + 3 + " students each");
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Infecting classes with limitedInfection and limit of 5");
        boolean infected = infector.limitedInfection(allUsers, 5, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(!infected);
        //make sure no one was infected
        for (User user : allUsers) {
            assertTrue(user.getVersionNumber() == 0);
            for (User student : user.getStudents()) {
                assertTrue(student.getVersionNumber() == 0);
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    @Test public void limitedInfectionWithUnusualClassesPass() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        System.out.println("Creating a class with " + 2 + " coaches and " + 30 + " students");
        allUsers.add(createClass(2,30, "0")); // infects 32
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30)); //infects 65
        System.out.println("Creating two classes with " + 1 + " coach and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        System.out.println("Infecting classes with limitedInfection and limit of 96");
        boolean infected = infector.limitedInfection(allUsers, 96, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(infected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }

    @Test public void limitedInfectionWithUnusualClassesFail() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        System.out.println("Creating a class with " + 2 + " coaches and " + 30 + " students");
        allUsers.add(createClass(2,30, "0")); // infects 32
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30)); //infects 65
        System.out.println("Creating two classes with " + 1 + " coach and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        System.out.println("Infecting classes with limitedInfection and limit of 95");
        boolean infected = infector.limitedInfection(allUsers, 95, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(!infected);
        //make sure no one was infected
        for (User user : allUsers) {
            assertTrue(user.getVersionNumber() == 0);
            for (User student : user.getStudents()) {
                assertTrue(student.getVersionNumber() == 0);
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    @Test public void limitedInfectionShouldBacktrack() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        System.out.println("Creating two classes with " + 1 + " coach and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30)); // infects 65
        System.out.println("Infecting classes with limitedInfection and limit of 65");
        boolean infected = infector.limitedInfection(allUsers, 65, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(infected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    @Test public void limitedInfectionShouldBacktrackLargeSet() {
        long start = System.currentTimeMillis();
        List<User> allUsers = new ArrayList<User>();
        // this is about the max I can do on my machine before having memory issues
        System.out.println("Creating 7000 classes with " + 2 + " coaches and " + 30 + " students each");
        for (int i = 0; i < 7000; i++) {
            User user = createClass(2,30,String.valueOf(i));
            allUsers.add(user);
        }
        System.out.println("Creating two classes with " + 1 + " coach and " + 30 + " students each. Classes are connected by a user that is a students in class 1 and coach in class 2");
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        System.out.println("Creating two classes with " + 2 + " coaches and " + 30 + " students each. Classes are connected by a tutor with a student in each");
        allUsers.add(createClassesConnectedByTutor(2,30)); // infects 65
        allUsers.add(createClass(1,30, "winner!")); // infects 31
        System.out.println("Infecting classes with limitedInfection and limit of 31");
        boolean infected = infector.limitedInfection(allUsers, 31, 1);
        System.out.println("Were there infected users? " + (infected ? "yes" : "no"));
        assertTrue(infected);
        long finish = System.currentTimeMillis();
        System.out.println("Test took " + (finish - start) + " millis");
        System.out.println();
    }
    
    /************************************************************************
     * Helper functions to create different class configurations.
     * Because username drives identity of a user making it a function of
     * student vs teacher + an incremented value + class
     * 
     * Basically trying to simulate what you might get back from a db if you pulled
     * users by class.
     ***********************************************************************/
    
    private User createClass(int numCoaches, int numStudents, String classCode) {
        if (numCoaches == 0 && numStudents == 0) return null;
        List<User> coaches = new ArrayList<User>();
        //if we could assume there was always a coach we wouldn't need this
        List<User> students = new ArrayList<User>();
        for (int i = 0; i < numCoaches; i++) {
            coaches.add(new User("coach" + i + classCode));
        }
        for (int i = 0; i < numStudents; i++) {
            User student = new User("student" + i + classCode);
            for (User coach : coaches) {
                student.addCoach(coach);
                coach.addStudent(student);
                students.add(student);
            }
        }
        if (!coaches.isEmpty()) {
            return coaches.get(0);
        } else {
            return students.get(0);
        }
    }
    
    private User createClassesConnectedByTutor(int coachesPerClass, int studentsPerClass) {
        User coachOfClass1 = createClass(coachesPerClass,studentsPerClass, "a");
        User coachOfClass2 = createClass(coachesPerClass,studentsPerClass, "b");
        //a tutor with a student in two different classes, all teachers and students in both classes
        //plus tutor should all be infected.
        User tutor = new User("tutor");
        
        User studentFromClass1 = coachOfClass1.getStudents().iterator().next();
        tutor.addStudent(studentFromClass1);
        studentFromClass1.addCoach(tutor);
        
        User studentFromClass2 = coachOfClass2.getStudents().iterator().next();
        tutor.addStudent(studentFromClass2);
        studentFromClass2.addCoach(tutor);
        return coachOfClass1;
    }
    
    private User createClassWithStudentTeacher(int coachesPerClass, int studentsPerClass) {
        User coach = createClass(coachesPerClass, studentsPerClass, "x");
        //student in one class, teacher of all students in another
        User studentCoach = coach.getStudents().iterator().next();
        User coach2 = createClass(coachesPerClass, studentsPerClass, "y");
        for (User student : coach2.getStudents()) {
            student.addCoach(studentCoach);
            studentCoach.addStudent(student);
        }
        return coach;
    }
}
