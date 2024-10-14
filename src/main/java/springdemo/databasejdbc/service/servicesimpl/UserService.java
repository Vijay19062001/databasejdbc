package springdemo.databasejdbc.service.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.mapper.UserMapper;
import springdemo.databasejdbc.model.UserModel;
import springdemo.databasejdbc.repository.UserRepository;
import springdemo.databasejdbc.service.ServiceUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements ServiceUser {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        logger.info("Creating user: {}", userModel.getUserName());
        Users users = userMapper.toEntity(userModel);
        Users saved = userRepository.save(users);
        logger.info("User created successfully with ID: {}", saved.getUserId());
        return userMapper.toModel(saved);
    }

    @Override
    public List<UserModel> getAllUsers() {
        logger.info("Retrieving all users");
        List<Users> allUsers = userRepository.findAll();
        logger.info("Found all users Successfully {} users", allUsers.size());
        return allUsers.stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserModel getUserById(String userId) {
        logger.info("Fetching user with ID: {}", userId);
        Optional<Users> userOptional = userRepository.findById(Integer.valueOf(userId));
        if (userOptional.isPresent()) {
            logger.info("User found: {}", userOptional.get().getUserName());
            return userMapper.toModel(userOptional.get());
        } else {
            logger.warn("User with ID: {} not found", userId);
            return null;
        }
    }

    @Override
    public UserModel updateUser(String userId, UserModel updatedUser) {
        logger.info("Updating user with ID: {}", userId);
        Optional<Users> userOptional = userRepository.findById(Integer.valueOf(userId));
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setUserEmail(updatedUser.getUserEmail());
            existingUser.setBookId(Integer.parseInt(updatedUser.getBookId()));
            Users saved = userRepository.save(existingUser);
            logger.info("User updated successfully with ID: {}", saved.getUserId());
            return userMapper.toModel(saved);
        }
        logger.warn("User with ID: {} not found for update", userId);
        return null; // User not found
    }

    @Override
    public boolean deleteUser(String userId) {
        logger.info("Deleting user with ID: {}", userId);
        if (userRepository.existsById(Integer.valueOf(userId))) {
            userRepository.deleteById(Integer.valueOf(userId));
            logger.info("User with ID: {} deleted successfully", userId);
            return true; // Successfully deleted
        }
        logger.warn("User with ID: {} not found for deletion", userId);
        return false;
    }
}
