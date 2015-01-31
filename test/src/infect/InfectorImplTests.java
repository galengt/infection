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
        int numCoaches = 1;
        int numStudents = 3;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }

    @Test public void infectAll1() {
        int numCoaches = 2;
        int numStudents = 10;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }

    @Test public void infectAll2() {
        int numCoaches = 40;
        int numStudents = 300;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }
    
    //this is about the upper limit of what I can run on my machine
    @Test public void infectAllLarge() {
        int numCoaches = 200;
        int numStudents = 6000;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
        assertTrue(user.getVersionNumber() == 1);
        for (User student : user.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }

    /************************************************************************
     * Infect the whole class for edge case classes
     ***********************************************************************/
    
    @Test public void infectAllInConnectedClasses() {
        User coachOfClass1 = createClassesConnectedByTutor(2, 30);
        int numInfected = infector.infectAll(coachOfClass1, 1);
        assertEquals(2 * 32 + 1, numInfected);
        assertTrue(coachOfClass1.getVersionNumber() == 1);
        for (User student : coachOfClass1.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }

    @Test public void studentIsAlsoTeacher() {
        User coach = createClassWithStudentTeacher(1, 30);
        int numInfected = infector.infectAll(coach, 1);
        assertEquals(2 * 31, numInfected);
        assertTrue(coach.getVersionNumber() == 1);
        for (User student : coach.getStudents()) {
            assertTrue(student.getVersionNumber() == 1);
        }
    }

    //TODO Some students in multiple classes
    
    /************************************************************************
     * Infect each class until you reach/pass the target
     * 
     * allUsers is not actually all users, rather one user from each class, so infectAll should reach all users
     ***********************************************************************/
    
    @Test public void nearlyLimitedInfection() {
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 5, 1);
        assertEquals(8,numInfected);
    }
    
    @Test public void nearlyLimitedInfection1() {
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createClass(2,5,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 12, 1);
        assertEquals(14,numInfected);
    }
    

    @Test public void nearlyLimitedInfection2() {
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 10; i++) {
            User user = createClass(2,30,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 100, 1);
        assertEquals(128,numInfected);
    }
    

    @Test public void nearlyLimitedInfectionWithUnusualClassesAndHighMax() {
        Set<User> allUsers = new HashSet<User>();
        allUsers.add(createClass(2,30, "0"));
        allUsers.add(createClassesConnectedByTutor(2,30));
        allUsers.add(createClassWithStudentTeacher(2,30));
        int numInfected = infector.nearlyLimitedInfection(allUsers, 1000, 1);
        assertEquals(32 + 65 + 64, numInfected);
    }
    
    @Test public void nearlyLimitedInfectionWithUnusualClasses() {
        Set<User> allUsers = new HashSet<User>();
        allUsers.add(createClass(2,30, "0")); // infects 32
        allUsers.add(createClassesConnectedByTutor(2,30)); //infects 65
        allUsers.add(createClassWithStudentTeacher(2,30)); // infects 64
        int numInfected = infector.nearlyLimitedInfection(allUsers, 100, 1);
        //just a quirk of hashing that determines order in which each group is infected
        assertEquals(129, numInfected);
    }
    
    /************************************************************************
     * Infect each class until you hit the target, or fail
     ***********************************************************************/
     
    @Test public void limitedInfectionPass() {
        Set<User> allUsers = new HashSet<User>();
        // 3 classes of 4 users
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        boolean infected = infector.limitedInfection(allUsers, 4, 1);
        assertTrue(infected);
        //can't know which graphs were infected
        /*for (User user : allUsers) {
            assertTrue(user.getVersionNumber() == 1);
            for (User student : user.getStudents()) {
                assertTrue(student.getVersionNumber() == 1);
            }
        }*/
    }
    
    @Test public void limitedInfectionFail() {
        Set<User> allUsers = new HashSet<User>();
        // 3 classes of 4 users
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        boolean infected = infector.limitedInfection(allUsers, 5, 1);
        assertTrue(!infected);
        //make sure no one was infected
        for (User user : allUsers) {
            assertTrue(user.getVersionNumber() == 0);
            for (User student : user.getStudents()) {
                assertTrue(student.getVersionNumber() == 0);
            }
        }
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
