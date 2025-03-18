import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.DataBase.FriendshipDBRepository;
import repository.DataBase.UserDBRepository;
import repository.InMemoryRepository;
import service.SocialNetwork;
import ui.Console;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserDBRepository repoUser=new UserDBRepository(new UserValidator());
        FriendshipDBRepository repoFriend=new FriendshipDBRepository(new FriendshipValidator(repoUser));

        SocialNetwork socialNetwork=new SocialNetwork(repoUser,repoFriend);
        Console ui=new Console(socialNetwork);

//        User u1=new User("John","Doe");
//        User u2=new User("Jane","Doe");
//        User u3=new User("Jack","Doe");
//        User u4=new User("Jill","Dne");
//        User u5=new User("Jill","Dore");
//        User u6=new User("Jill","Does");
//        User u7=new User("Jill","Doew");
//        User u8=new User("Jill","Doeu");
//
//        socialNetwork.addUser(u1);
//        socialNetwork.addUser(u2);
//        socialNetwork.addUser(u3);
//        socialNetwork.addUser(u4);
//        socialNetwork.addUser(u5);
//        socialNetwork.addUser(u6);
//        socialNetwork.addUser(u7);
//        socialNetwork.addUser(u8);
        ui.run();
    }
}