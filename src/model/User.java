package model;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String userName;
    private int versionNumber;
    private Set<User> coaches = new HashSet<User>();
    private Set<User> students = new HashSet<User>();
    
    public User(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() { return userName; }
    public int getVersionNumber() { return versionNumber; }
    public void setVersionNumber(int versionNumber) { this.versionNumber = versionNumber; }
    public Set<User> getCoaches() { return coaches; }
    public void setCoaches(Set<User> coaches) { this.coaches = coaches; }
    public Set<User> getStudents() { return students; }
    public void setStudents(Set<User> students) { this.students = students;}

    @Override public int hashCode() {
        return userName.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if (obj == null || ! (obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return this.userName.equals(other.getUserName());
    }

}
