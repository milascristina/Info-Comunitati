package service;

import domain.Friendship;
import domain.User;
import domain.validators.ValidatorException;
import repository.DataBase.FriendshipDBRepository;
import repository.DataBase.UserDBRepository;
import repository.InMemoryRepository;

import java.util.Vector;

public class SocialNetwork {
    private final UserDBRepository repositoryUser;
    private final FriendshipDBRepository repositoryFriendship;


    public SocialNetwork(UserDBRepository repositoryUser, FriendshipDBRepository repositoryFriendship) {
        this.repositoryUser = repositoryUser;
        this.repositoryFriendship = repositoryFriendship;
    }

    public Iterable<User> getUsers() {
        return repositoryUser.findAll();
    }
    public User findUser(Long id) {
        return repositoryUser.findOne(id);
    }
    public Friendship findFriendship(Long id1, Long id2) {
        Long id=0L;
        for(Friendship f: repositoryFriendship.findAll()) {
            if(f.getIdUser1().equals(id1) && f.getIdUser2().equals(id2)) {
                id=f.getId();
            }
            if(f.getIdUser1().equals(id2) && f.getIdUser2().equals(id1)) {
                id=f.getId();
            }
        }
        return repositoryFriendship.findOne(id);
    }

    public Long getNewUserId() {
        Long newUserId = 0L;
        for(User u: repositoryUser.findAll()) {
            newUserId=u.getId();
        }
        newUserId++;
        return newUserId;
    }
    public void addUser(User user) {
        user.setId(getNewUserId());
        repositoryUser.save(user);
    }
    public Iterable<Friendship> getFriendships() {
        return repositoryFriendship.findAll();
    }
    public User removeUser(Long id) {
        User deletedUser = null; // Inițializează rezultatul cu null
        try {
            User u = repositoryUser.findOne(id);
            if (u == null) {
                System.out.println("Error: The User with id " + id + " doesn't exist!");
                return null; // Ieși din funcție, dar programul continuă
            }
            deletedUser = repositoryUser.delete(id);
            if (deletedUser == null) {
                System.out.println("Error: User deletion failed for id " + id + ". The user may not exist.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove user with id " + id + ": " + e.getMessage());
        }
        return deletedUser;
    }





    public Long getNewFriendshipId() {
        Long newFriendshipId = 0L;
        for(Friendship f: getFriendships()) {
            newFriendshipId=f.getId();
        }
        newFriendshipId++;
        return newFriendshipId;
    }

    public void addFriendship(Friendship f) {
        User user1=repositoryUser.findOne(f.getIdUser1());
        User user2=repositoryUser.findOne(f.getIdUser2());
        if(getFriendships()!=null){
            for(Friendship friendship:getFriendships()){
                if(friendship.getIdUser1().equals(user1.getId())&&friendship.getIdUser2().equals(user2.getId())) {
                    throw new ValidatorException("The friendship already exists!");
                }
            }
            if(repositoryUser.findOne(f.getIdUser1())==null||repositoryUser.findOne(f.getIdUser2())==null){
                throw new ValidatorException("The user doesn't exist!");
            }
            if(f.getIdUser1().equals(f.getIdUser2())){
                throw new ValidatorException("IDs can't be the same!");
            }
        }
        f.setId(getNewFriendshipId());
        repositoryFriendship.save(f);

        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    public void removeFriendship(Long id1, Long id2) {
        User user1=repositoryUser.findOne(id1);
        User user2=repositoryUser.findOne(id2);
        Long id=0L;
        for(Friendship f: repositoryFriendship.findAll()) {
            if(f.getIdUser1().equals(id1)&&f.getIdUser2().equals(id2)|| f.getIdUser1().equals(id2)&&f.getIdUser2().equals(id1)) {
                id=f.getId();
            }
        }
        if(id==0L){
            throw new ValidatorException("The friendship doesn't exist!");
        }
        repositoryFriendship.delete(id);
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }
}
