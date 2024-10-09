package springdemo.databasejdbc.mapper;


import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.exception.InvalidDateFormatException;
import springdemo.databasejdbc.model.UserModel;


@Component
public class UserMapper {

    public Users toEntity(UserModel userModel) throws InvalidDateFormatException {
        Users users = new Users();

        users.setUserId(Integer.parseInt(userModel.getUserId()));
        users.setBookId(Integer.parseInt(userModel.getBookId()));
        users.setUserName(userModel.getUserName());
        users.setUserEmail(userModel.getUserEmail());

        return users;
    }

    public UserModel toModel(Users users){
        UserModel userModel = new UserModel();

        userModel.setUserId(String.valueOf(users.getUserId()));
        userModel.setBookId(String.valueOf(users.getBookId()));
        userModel.setUserName(users.getUserName());
        userModel.setUserEmail(users.getUserEmail());

        return userModel;
    }
}
