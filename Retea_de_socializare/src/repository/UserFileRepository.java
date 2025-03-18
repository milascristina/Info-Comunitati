package repository;

import domain.Entity;
import domain.User;
import domain.validators.Validator;

import java.io.File;
import java.util.List;

public class UserFileRepository extends AbstractFileRepository<Long, User> {
    public UserFileRepository(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> data) {
        User user=new User(data.get(1),data.get(2));
        user.setId(Long.parseLong(data.get(0)));
        return user;
    }
    @Override
    protected String createEntityasString(User user){
        return user.getId()+";"+user.getFirstName()+";"+user.getLastName();
    }
}
