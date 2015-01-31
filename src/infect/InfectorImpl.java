package infect;

import java.util.HashSet;
import java.util.Set;

import model.User;

public class InfectorImpl implements Infector {

    @Override public int infectAll(User user, int versionNumber) {
        //depth first search
        int numInfected = 0;
        // checking to see if we already infected this user so we don't double count
        if (user.getVersionNumber() != versionNumber) {
            user.setVersionNumber(versionNumber);
            numInfected++;
        } else {
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

    @Override public int nearlyLimitedInfection(Set<User> allUsers, int numToInfect, int versionNumber) {
        int numInfected = 0;
        for (User user : allUsers) {
            if (numInfected < numToInfect) {
                numInfected += infectAll(user, versionNumber);
            } else {
                break;
            }
        }
        return numInfected;
    }

    @Override public boolean limitedInfection(Set<User> allUsers, int numToInfect, int versionNumber) {
        int totalNumInfected = 0;
        Set<User> infectedGraphs = new HashSet<User>();
        // there is probably a recursive backtracking approach, pretty sure this misses some possible success cases
        for (User user : allUsers) {
            int numInfected = infectAll(user, versionNumber);
            if (numInfected + totalNumInfected > numToInfect) {
                //rollback this graph
                infectAll(user, versionNumber - 1);
            } else {
                totalNumInfected += numInfected;
                infectedGraphs.add(user);
            }
            if (totalNumInfected == numToInfect) break;
        }
        if (totalNumInfected == numToInfect) {
            return true;
        } else {
            //we failed, rollback all
            for (User user : infectedGraphs) {
                infectAll(user, versionNumber - 1);
            }
            return false;
        }
    }

    
}
