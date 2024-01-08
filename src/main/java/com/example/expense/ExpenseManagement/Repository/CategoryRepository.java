package com.example.expense.ExpenseManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense.ExpenseManagement.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByCategoryId(int categoryId);

    @Query("Select c from Category c inner join c.user u where u.userId = :userId")
    List<Category> findCategoriesByUserId(@Param("userId") int userId);

    @Query("Select c from Category c where c.categoryName = :categoryName")
    Category findByCategoryName(@Param("categoryName") String categoryName);

}
