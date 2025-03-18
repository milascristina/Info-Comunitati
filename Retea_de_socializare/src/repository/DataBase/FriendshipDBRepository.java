package repository.DataBase;

import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import repository.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FriendshipDBRepository implements Repository<Long, Friendship> {
    FriendshipValidator friendshipValidator;

    public FriendshipDBRepository(FriendshipValidator friendshipValidator) {
        this.friendshipValidator = friendshipValidator;
    }

    @Override
    public Friendship findOne(Long id) {
        String query = "SELECT * FROM friendships WHERE \"id\" = ?";
        Friendship friendship = null;
        try(Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Long idUser1=null;
            Long idUser2=null;
            while (resultSet.next()) {
                idUser1 = resultSet.getLong("iduser1");
                idUser2 = resultSet.getLong("iduser2");
            }
            friendship = new Friendship(idUser1, idUser2);
            friendship.setId(id);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return friendship;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Map<Long,Friendship> friendships = new HashMap<>();
        try(Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM friendships");
        ResultSet resultSet=preparedStatement.executeQuery()){
            while (resultSet.next()){
                Long id=resultSet.getLong("id");
                Long idUser1=resultSet.getLong("iduser1");
                Long idUser2=resultSet.getLong("iduser2");
                Friendship friendship=new Friendship(idUser1,idUser2);
                friendship.setId(id);
                friendships.put(id,friendship);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return friendships.values();
    }

    @Override
    public Friendship save(Friendship entity) {
        if(entity==null){
            throw new IllegalArgumentException("Friendship can't be null!");
        }
        String query="INSERT INTO friendships(\"id\",\"iduser1\",\"iduser2\") VALUES (?,?,?)";

        try(Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
        PreparedStatement preparedStatement=connection.prepareStatement(query);){
            preparedStatement.setLong(1,entity.getId());
            preparedStatement.setLong(2,entity.getIdUser1());
            preparedStatement.setLong(3,entity.getIdUser2());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Friendship delete(Long id) {
        String query="DELETE FROM friendships WHERE \"id\" = ?";

        try(Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
        PreparedStatement preparedStatement=connection.prepareStatement(query);){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        Friendship friendshipToDelete=null;
        for(Friendship friendship:findAll()){
            if(Objects.equals(friendship.getId(),id)){
                friendshipToDelete=friendship;
            }
        }
        return friendshipToDelete;
    }
    @Override
    public Friendship update(Friendship entity) {
        return entity;
    }
}
