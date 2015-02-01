package infect;

import java.util.List;
import java.util.Set;

import model.User;

public interface Infector {

    /**
     * Infect the entire connected graph
     * @param user
     * @param versionNumber
     * @return number of users infected
     */
    public int infectAll(User user, int versionNumber);
    
    /**
     * If the user has student/coach relations infect them up to numToInfect.
     * Doesn't really seem useful to us, violates the constraint that a classroom (connected graph)
     * should all be on the same version, which is important.
     * @param user
     * @param numToInfect
     * @param versionNumber
     * @return
     */
    //public boolean limitedInfection(User user, int numToInfect, int versionNumber);
    
    /**
     * Infects clusters of users until the threshold is reached. Values the constraint of
     * keeping all connected graphs on the same version over the constraint of numToInfect.
     * @param allUsers - all known users
     * @param numToInfect - the minimum number of users to get the new version. 
     * @param versionNumber
     * @return number of users infected
     */
    public int nearlyLimitedInfection(Set<User> allUsers, int numToInfect, int versionNumber);
    
    /**
     * Values the constraint of keeping all connected graphs on the same version AND the constraint of numToInfect.
     * Fails if both can't be satisfied exactly with no version numbers having been changed.
     * @param allUsers - all known users
     * @param numToInfect - the number of users to get the new version. If the exact number
     * is not possible no users are infected.
     * @param versionNumber
     * @return true if exact number were infected, false otherwise
     */
    public boolean limitedInfection(List<User> allUsers, int numToInfect, int versionNumber); 
}
