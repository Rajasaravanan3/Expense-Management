package com.example.expense.ExpenseManagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.Entity.User;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;
import com.example.expense.ExpenseManagement.Repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {

        User user = null;

        try {
            user = userRepository.findByUserId(userId);
            if(user == null) {
                throw new ValidationException("No record found for the given userId ", HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the user");
        }

        return user;
    }

    public Integer getUserIdByEmail(String email) {

        Integer userId = null;
        try {
            userId = userRepository.findUserIdByEmail(email);
            if(userId == null) {
                throw new ValidationException("No record found for the given email", HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the userId");
        }
        return userId;
    }

    public void addUser(User user){

        try {
            if(user == null || (user.getEmail() instanceof String && user.getEmail().isEmpty()) ||
                (user.getPassword() instanceof String && user.getPassword().isEmpty())||
                (user.getUsername() instanceof String && user.getUsername().isEmpty()))
                    
                throw new ValidationException("Non nullable Field value must not be empty", HttpStatus.BAD_REQUEST);
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while saving");
        }
        userRepository.save(user);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(User updatedUser) {

        User existingUser = null;

        try {
            existingUser = userRepository.findByUserId(updatedUser.getUserId());

            if(updatedUser.getEmail() instanceof String && ! (updatedUser.getEmail().isEmpty()))
                existingUser.setEmail(updatedUser.getEmail());

            if(updatedUser.getFullName() instanceof String && ! (updatedUser.getFullName().isEmpty()))
                existingUser.setFullName(updatedUser.getFullName());

            if(updatedUser.getPassword() instanceof String && ! (updatedUser.getPassword().isEmpty()))
                existingUser.setPassword(updatedUser.getPassword());

            if(updatedUser.getUsername() instanceof String && ! (updatedUser.getUsername().isEmpty()))
                existingUser.setUsername(updatedUser.getUsername());
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while updating");
        }
        
        userRepository.save(existingUser);
    }

}
