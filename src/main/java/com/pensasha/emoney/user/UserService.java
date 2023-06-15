package com.pensasha.emoney.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    //Adding a user
    public User addUser(User user){
        return userRepository.save(user);
    }

    //Updating user details
    public User updateUserDetails(User user){
        return userRepository.save(user);
    }

    //Getting all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //Deleting user details
    public void deleteUserDetails(int idNumber){
        userRepository.deleteById(idNumber);
    }

    //Getting a single user
    public User getUser(int idNumber){
        return userRepository.findById(idNumber).get();
    }

    //Changing username
    public User changeUsername(int idNumber, int newIdNumber){
        
        User existingUser = userRepository.findById(idNumber).get();
        existingUser.setIdNumber(newIdNumber);

        userRepository.deleteById(idNumber);

        return userRepository.save(existingUser);
    }

    //Changing password
    public User changePassword(int idNumber, String newPassword){

        User existingUser = userRepository.findById(idNumber).get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingUser.setPassword(encoder.encode(newPassword));

        return userRepository.save(existingUser);
    }
}
