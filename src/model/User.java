package model;

import java.util.HashSet;
import java.util.Set;

public class User {

    //would like to enforce uniqueness some how, normally would rely on db for that
    private String userName;
    private int versionNumber = 0;
    //if all we wanted to do was to traverse the graph for a given
    //user for the purpose of infection we could just have one Set called relations, 
    //but for other uses we probably want to preserve the difference between coach and student
    private Set<User> coaches = new HashSet<User>();
    private Set<User> students = new HashSet<User>();
    
    public User(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() { return userName; }
    public int getVersionNumber() { return versionNumber; }
    public void setVersionNumber(int versionNumber) { this.versionNumber = versionNumber; }
    public Set<User> getCoaches() { return coaches; }
    //public void setCoaches(Set<User> coaches) { this.coaches = coaches; }
    public void addCoach(User coach) { this.coaches.add(coach); }
    public Set<User> getStudents() { return students; }
    //public void setStudents(Set<User> students) { this.students = students;}
    public void addStudent(User student) { this.students.add(student); }
    
    
    /******************************************************
     * username is the field that makes sense for identity, 
     * but don't have a great way to enforce uniqueness on that field.
     * Hopefully that doesn't create problems
     *****************************************************/
    
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
