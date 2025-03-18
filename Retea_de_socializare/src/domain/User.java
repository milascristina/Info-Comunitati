package domain;

import java.util.*;

public class User extends Entity<Long>{
    private String firstName;
    private String lastName;
    List<User> friends;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<User>();
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getFriends() {
        return friends;
    }
    public void addFriend(User friend) {
        friends.add(friend);
    }
    public void removeFriend(User friend) {
        friends.remove(friend);
    }
    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", friends=" + friends + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(!(obj instanceof User)) return false;
        User other = (User) obj;
        return this.firstName.equals(other.getFirstName()) && this.lastName.equals(other.getLastName()) && this.getFriends().equals(other.getFriends());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(),getLastName(),getFriends());
    }
}
