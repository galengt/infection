package infect;

import java.util.Set;

import model.User;

public interface Infector {

    /**
     * 
     * @param user
     * @param versionNumber
     * @return number of users infected
     */
    public int infectAll(User user, int versionNumber);
    
    /**
     * If the user has student/coach relations infect them up to numToInfect.
     * Doesn't really seem useful to us, violates the idea that a classroom should
     * all be on the same version
     * @param user
     * @param numToInfect
     * @param versionNumber
     * @return
     */
    //public boolean limitedInfection(User user, int numToInfect, int versionNumber);
    
    /**
     * @param allUsers - all known users
     * @param numToInfect - the minimum number of users to get the new version. 
     * Infects clusters of users until the threshold is reached
     * @param versionNumber
     * @return
     */
    public int nearlyLimitedInfection(Set<User> allUsers, int numToInfect, int versionNumber);
    
    /**
     * @param allUsers - all known users
     * @param numToInfect - the number of users to get the new version. If the exact number
     * is not possible no users are infected.
     * @param versionNumber
     * @return true if exact number were infected, false otherwise
     */
    public boolean limitedInfection(Set<User> allUsers, int numToInfect, int versionNumber); 
}
