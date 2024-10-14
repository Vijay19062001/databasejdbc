package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.UserModel;

import java.util.List;

public interface ServiceUser {

    public UserModel createUser(UserModel user) ;
    public List<UserModel> getAllUsers() ;
    public UserModel getUserById(String userId) ;
    public UserModel updateUser(String userId, UserModel updatedUser) ;
    public boolean deleteUser(String userId) ;

    }
