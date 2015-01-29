package infect;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.User;

import org.junit.Test;

public class InfectorImplTests {

    private InfectorImpl infector = new InfectorImpl();
    
    @Test public void test() {
        int numCoaches = 1;
        int numStudents = 3;
        User user = createUserInClass(numCoaches,numStudents);
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void test2() {
        int numCoaches = 2;
        int numStudents = 10;
        User user = createUserInClass(numCoaches,numStudents);
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    @Test public void test3() {
        int numCoaches = 40;
        int numStudents = 300;
        User user = createUserInClass(numCoaches,numStudents);
        int numInfected = infector.infectAll(user, 1);
        assertTrue(numCoaches + numStudents == numInfected);
    }

    private User createUserInClass(int numCoaches, int numStudents) {
        if (numCoaches == 0 && numStudents == 0) return null;
        List<User> coaches = new ArrayList<User>();
        //if we could assume there was always a coach we wouldn't need this
        List<User> students = new ArrayList<User>();
        for (int i = 0; i < numCoaches; i++) {
            coaches.add(new User("coach" + i));
        }
        for (int i = 0; i < numStudents; i++) {
            User student = new User("student" + i);
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
