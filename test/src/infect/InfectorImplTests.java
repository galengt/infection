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
        User user = createUserInClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void infectAll1() {
        int numCoaches = 2;
        int numStudents = 10;
        User user = createUserInClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void infectAll2() {
        int numCoaches = 40;
        int numStudents = 300;
        User user = createUserInClass(numCoaches,numStudents, "0");
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void nearlyLimitedInfection() {
        //not actually all users, but one user from each class, so infectAll would reach all users
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createUserInClass(1,3,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 5, 1);
        assertEquals(8,numInfected);
    }
    
    
    @Test public void nearlyLimitedInfection1() {
        //not actually all users, but one user from each class, so infectAll would reach all users
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 3; i++) {
            User user = createUserInClass(2,5,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 12, 1);
        assertEquals(14,numInfected);
    }

    @Test public void nearlyLimitedInfection2() {
        //not actually all users, but one user from each class, so infectAll would reach all users
        Set<User> allUsers = new HashSet<User>();
        for (int i = 0; i < 10; i++) {
            User user = createUserInClass(2,30,String.valueOf(i));
            allUsers.add(user);
        }
        int numInfected = infector.nearlyLimitedInfection(allUsers, 100, 1);
        assertEquals(128,numInfected);
    }

    private User createUserInClass(int numCoaches, int numStudents, String classCode) {
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
}
