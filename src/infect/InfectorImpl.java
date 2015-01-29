package infect;

import java.util.Set;

import model.User;

public class InfectorImpl implements Infector {

    @Override public int infectAll(User user, int versionNumber) {
        int numInfected = 0;
        // checking to see if we already infected this user so we don't double count
        if (user.getVersionNumber() != versionNumber) {
            user.setVersionNumber(versionNumber);
            numInfected++;
        } else {
            //if we have visited already return or it gets circular
            return 0;
        }
        for (User coach : user.getCoaches()) {
            numInfected += infectAll(coach, versionNumber);
        }
        for (User student : user.getStudents()) {
            numInfected += infectAll(student, versionNumber);
        }
        return numInfected;
    }

    @Override public boolean limitedInfection(Set<User> allUsers, int numToInfect, int versionNumber) {
        // TODO Auto-generated method stub
        return false;
    }

}
