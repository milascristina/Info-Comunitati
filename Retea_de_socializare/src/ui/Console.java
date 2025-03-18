package ui;

import domain.Friendship;
import domain.User;
import domain.validators.ValidatorException;
import repository.DataBase.UserDBRepository;
import service.SocialCommunities;
import service.SocialNetwork;

import java.util.List;
import java.util.Scanner;

public class Console {
    private SocialNetwork socialNetwork;
    private SocialCommunities socialCommunities;
    public Console(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        this.socialCommunities =new SocialCommunities(socialNetwork);
    }
    void printMenu(){
        System.out.println("\t\t\tMENU\t\t\t");
        System.out.println("1. Add user");
        System.out.println("2. Remove user");
        System.out.println("3. Add friendship");
        System.out.println("4. Remove friendship");
        System.out.println("5. Print user");
        System.out.println("6. Print friendship");
        System.out.println("7. Communities");
        System.out.println("8. Most social communities");
        System.out.println("0. Exit");
    }

    public void run(){
        Scanner scan=new Scanner(System.in);
        boolean ok=true;
        while(ok){
            printMenu();
            String input=scan.nextLine();
            switch(input){
                case "1":
                    addUser();
                    break;
                case "2":
                    removeUser();
                    break;
                case "3":
                    addFriendship();
                    break;
                case "4":
                    removeFriendship();
                    break;
                case "5":
                    printUser();
                    break;
                case "6":
                    printFriendship();
                    break;
                case "7":
                    printConnectCommunities();
                    break;
                case "8":
                    printMostSocialCommunities();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    ok=false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private void addUser(){
        System.out.println("Add user");
        Scanner scan=new Scanner(System.in);
        System.out.print("First name: ");
        String firstName=scan.nextLine();
        System.out.print("Last name: ");
        String lastName=scan.nextLine();
        try{
            socialNetwork.addUser(new User(firstName,lastName));
        }catch (ValidatorException e){
                System.out.println("Invalid user!");
        }catch (IllegalArgumentException e){
            System.out.println("Invalid argument!");
        }

    }
    private void removeUser(){
        printUser();
        System.out.println("Remove user");
        Scanner scan=new Scanner(System.in);
        System.out.print("ID: ");
        String var=scan.nextLine();
        try{
            Long id=Long.parseLong(var);

            User user= socialNetwork.findUser(id);
            if(user==null){
                throw new IllegalArgumentException("The user id is invalid!");
            }
            user=socialNetwork.removeUser(id);
            System.out.println("User "+id+" "+user.getFirstName()+" "+user.getLastName()+" was removed");
        }catch (IllegalArgumentException e){
            System.out.println("Id must be a number!");
        }
    }
    private void addFriendship(){
        Scanner scan=new Scanner(System.in);
        System.out.print("Id of the first user: ");
        String var1=scan.nextLine();
        System.out.print("Id of the second user: ");
        String var2=scan.nextLine();
        try {
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            } catch (IllegalArgumentException e){
                System.out.println("Id must be a number!");
            }
            socialNetwork.addFriendship(new Friendship(id1,id2));
        }catch (ValidatorException e){
            System.out.println("Friendship is invalid!");
        }catch (IllegalArgumentException e){
            System.out.println("Invalid arguments!");
        }
    }
    private void removeFriendship(){
        Scanner scan=new Scanner(System.in);
        System.out.print("Id of the first user: ");
        String var1=scan.nextLine();
        System.out.print("Id of the second user: ");
        String var2=scan.nextLine();
        try{
            Long id1 = 0L, id2 = 0L;
            try {
                id1 = Long.parseLong(var1);
                id2 = Long.parseLong(var2);
            }catch (IllegalArgumentException e){
                System.out.println("Id must be a number!");
            }
            Friendship friendship=socialNetwork.findFriendship(id1,id2);
            if(friendship==null){
                throw new IllegalArgumentException("The friendship id is invalid!");
            }
            socialNetwork.removeFriendship(id1,id2);
        }catch (IllegalArgumentException e){
            System.out.println("Invalid arguments!");
        }catch (ValidatorException e){
            System.out.println("Friendship is invalid!");
        }
    }
    private void printUser(){
        System.out.println("\t\t\tUSER\t\t\t");
        for(User user: socialNetwork.getUsers()){
            System.out.println("ID:"+user.getId()+" "+user.getFirstName()+" "+user.getLastName());
        }
    }
//    private void printFriendship(){
//        System.out.println("\t\t\tFRIENDSHIP\t\t\t");
//        for(User user: socialNetwork.getUsers()){
//            System.out.println("Friends of user:"+user.getFirstName()+" "+user.getLastName()+"\n(Number of friends: "+user.getFriends().size()+")");
//            if(user.getFriends()!=null){
//                for(User friend: user.getFriends()){
//                    System.out.println("ID:"+friend.getId()+" "+friend.getFirstName()+" "+friend.getLastName());
//                }
//            }
//        }
//    }
    private void printFriendship(){
        System.out.println("\t\t\tFRIENDSHIP\t\t\t");
        for(Friendship friendship: socialNetwork.getFriendships()){
            System.out.println(friendship.getIdUser1()+" "+friendship.getIdUser2());
        }
    }

    private void printConnectCommunities(){
        System.out.println("\t\t\tCONNECT COMMUNITIES");
        int nrOfCommunities=socialCommunities.connectCommunities();
        System.out.println("Number of Social Communities: "+nrOfCommunities);
    }
    private void printMostSocialCommunities(){
        System.out.println("\t\t\tMOST SOCIAL COMMUNITIES");
        List<Long> mostSocialCommunity=socialCommunities.mostSocialCommunities();
        for(Long id: mostSocialCommunity){
            System.out.println(id);
        }
    }

}
