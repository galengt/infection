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

    private InfectorImpl infector = new InfectorImpl();
    
    @Test public void infectAll() {
        int numCoaches = 1;
        int numStudents = 3;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void infectAll1() {
        int numCoaches = 2;
        int numStudents = 10;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void infectAll2() {
        int numCoaches = 40;
        int numStudents = 300;
        User user = createClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void infectAllInConnectedClasses() {
        User coachOfClass1 = createClassesConnectedByTutor(2, 30);
        int numInfected = infector.infectAll(coachOfClass1, 1);
        assertEquals(2 * 32 + 1, numInfected);
    }
    
    @Test public void studentIsAlsoTeacher() {
        User coach = createClassWithStudentTeacher(1, 30);
        int numInfected = infector.infectAll(coach, 1);
        assertEquals(2 * 31, numInfected);
    }

    //Some students in multiple classes
    
    @Test public void nearlyLimitedInfection() {
        //not actually all users, but one user from each class, so infectAll would reach all users
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 5, 1);
        assertEquals(8,numInfected);
    }
    
    @Test public void nearlyLimitedInfection1() {
        //not actually all users, but one user from each class, so infectAll would reach all users
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createClass(2,5,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 12, 1);
        assertEquals(14,numInfected);
    }

    @Test public void nearlyLimitedInfection2() {
        //not actually all users, but one user from each class, so infectAll would reach all users
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
