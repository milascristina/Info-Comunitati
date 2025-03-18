package repository.DataBase;

import domain.User;
import domain.validators.UserValidator;
import repository.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class UserDBRepository implements Repository<Long, User> {
    UserValidator userValidator;

    public UserDBRepository(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @Override
    public User findOne(Long aLong) {
        String query= "select * from users WHERE\"id\"=?";
        User user=null;
        try(Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
            PreparedStatement preparedStatement= connection.prepareStatement(query);){

            preparedStatement.setLong(1,aLong);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("last_name");
                user=new User(firstName,lastName);
                user.setId(aLong);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        HashMap<Long, User> users = new HashMap<>();
        try(Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
            PreparedStatement preparedStatement=connection.prepareStatement("select * from  users");
            ResultSet resultSet=preparedStatement.executeQuery()){
            while(resultSet.next()){
                Long id=resultSet.getLong("id");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("last_name");
                User user=new User(firstName,lastName);
                user.setId(id);
                users.put(user.getId(),user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return users.values();
    }

    @Override
    public User save(User entity){
        if(entity==null){
            throw new IllegalArgumentException("Entity cannot be null");
        }
        String query= "INSERT INTO users(\"id\", \"first_name\", \"last_name\") VALUES (?,?,?)";

        try(Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java","postgres","feliciamami");
            PreparedStatement preparedStatement= connection.prepareStatement(query);){
            preparedStatement.setLong(1,entity.getId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public User delete(Long userId) {
        String selectQuery = "SELECT first_name, last_name FROM users WHERE \"id\" = ?";
        User userToDelete = null;

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java", "postgres", "feliciamami");
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            selectStatement.setLong(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                userToDelete = new User(firstName, lastName);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user for deletion", e);
        }
        if (userToDelete == null) {
            return null;
        }
        String deleteQuery = "DELETE FROM users WHERE \"id\" = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java", "postgres", "feliciamami");
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            deleteStatement.setLong(1, userId);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
        return userToDelete;
    }


    @Override
    public User update(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        String checkQuery = "SELECT * FROM users WHERE \"id\" = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java", "postgres", "feliciamami");
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setLong(1, entity.getId());
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if user exists for update", e);
        }
        String updateQuery = "UPDATE users SET \"first_name\" = ?, \"last_name\" = ? WHERE \"id\" = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplicatie_java", "postgres", "feliciamami");
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, entity.getFirstName());
            updateStatement.setString(2, entity.getLastName());
            updateStatement.setLong(3, entity.getId());
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected == 0) {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        return entity;
    }

}
