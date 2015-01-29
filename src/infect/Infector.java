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
     * If the user has student/coach relations infect them up to numToInfect
     * @param user
     * @param numToInfect
     * @return
     */
    //public boolean limitedInfection(User user, int numToInfect);
    
    /**
     * @param allUsers - all known users
     * @param numToInfect - the number of users to get the new version
     * @param versionNumber
     * @return
     */
    public boolean limitedInfection(Set<User> allUsers, int numToInfect, int versionNumber); 
}
