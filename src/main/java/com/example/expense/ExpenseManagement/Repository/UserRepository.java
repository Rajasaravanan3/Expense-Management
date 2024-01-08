package com.example.expense.ExpenseManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense.ExpenseManagement.Entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select u from User u where u.userId = :userId")
    User findByUserId(@Param("userId") int userId);

    @Query("Select u.userId from User u where u.email = :email")
    Integer findUserIdByEmail(@Param("email") String email);

}