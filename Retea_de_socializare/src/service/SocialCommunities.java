package service;

import domain.Friendship;
import domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialCommunities {
    SocialNetwork socialNetwork;
    HashMap<Long, List<Long>> adjList;

    public SocialCommunities(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    void DFS(Long v,HashMap<Long, Boolean> visited) {
        visited.put(v, true);
        System.out.print(v + " "+this.socialNetwork.findUser(v).getFirstName()+" "+this.socialNetwork.findUser(v).getLastName()+",");
        if(adjList.containsKey(v)) {
            for(Long x:adjList.get(v)) {
                if(!visited.containsKey(x))
                    DFS(x,visited);
            }
        }
    }

    public int connectCommunities() {
        adjList = new HashMap<Long,List<Long>>();
        for(User user:socialNetwork.getUsers()) {
            List<Long> friends=new ArrayList<>();
            for(Friendship friendship: socialNetwork.getFriendships()){
                if(friendship.getIdUser1().equals(user.getId()))
                    friends.add(friendship.getIdUser2());
                if(friendship.getIdUser2().equals(user.getId()))
                    friends.add(friendship.getIdUser1());
            }
            if(!friends.isEmpty())
                this.adjList.put(user.getId(), friends);
        }
        List<Long> ids=new ArrayList<>();
        for(User user:socialNetwork.getUsers())
            ids.add(user.getId());
        int nrOfCommunities=0;
        HashMap<Long,Boolean> visited=new HashMap<Long,Boolean>();
        for(Long v:ids) {
            if(!visited.containsKey(v)){
                DFS(v,visited);
                nrOfCommunities++;
                System.out.println();
            }
        }
        return nrOfCommunities;
    }
    // Modificarea metodei DFS pentru a include comunitatea
    private void DFS(Long v, HashMap<Long, Boolean> visited, List<Long> community) {
        visited.put(v, true);  // Marchează utilizatorul ca vizitat
        community.add(v);  // Adaugă utilizatorul în comunitatea curentă

        // Vizitează toți prietenii utilizatorului curent care nu au fost vizitați
        if (adjList.containsKey(v)) {
            for (Long x : adjList.get(v)) {
                if (!visited.containsKey(x) || !visited.get(x)) {
                    DFS(x, visited, community);  // Apelează recursiv DFS pentru fiecare prieten
                }
            }
        }
    }

    public List<Long> mostSocialCommunities() {
        adjList = new HashMap<Long, List<Long>>();  // Inițializare listă de adiacență
        // Construirea listei de adiacență pe baza prieteniilor
        for (User user : socialNetwork.getUsers()) {
            List<Long> friends = new ArrayList<>();
            for (Friendship friendship : socialNetwork.getFriendships()) {
                if (friendship.getIdUser1().equals(user.getId())) {
                    friends.add(friendship.getIdUser2());
                }
                if (friendship.getIdUser2().equals(user.getId())) {
                    friends.add(friendship.getIdUser1());
                }
            }
            if (!friends.isEmpty()) {
                adjList.put(user.getId(), friends);  // Adaugă utilizatorul și prietenii săi în lista de adiacență
            }
        }

        List<Long> maxCommunity = new ArrayList<>();
        HashMap<Long, Boolean> visited = new HashMap<>();

        // Iterăm prin toți utilizatorii și explorăm comunitățile conectate
        for (Long userId : adjList.keySet()) {
            if (!visited.containsKey(userId)) {
                List<Long> currentCommunity = new ArrayList<>();
                DFS(userId, visited, currentCommunity);  // Găsim comunitatea conectată folosind DFS
                if (currentCommunity.size() > maxCommunity.size()) {
                    maxCommunity = currentCommunity;  // Actualizăm comunitatea cu cei mai mulți utilizatori
                }
            }
        }

        return maxCommunity;  // Returnează comunitatea cu cei mai mulți utilizatori conectați
    }
}
