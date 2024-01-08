package com.example.expense.ExpenseManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.ExpenseManagement.DTO.CategoryDto;
import com.example.expense.ExpenseManagement.Service.CategoryService;
import com.example.expense.ExpenseManagement.Service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam("userId") int userId){

        List<CategoryDto> categoryDtoList = categoryService.getAllCategories(userId);
        if(categoryDtoList != null && userService.getUserById(userId) != null) {
            return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId){

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        if (categoryDto != null) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDto categoryDto) {

        categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {

        if(categoryService.getCategoryById(categoryId) != null) {
            categoryService.deleteCategoryById(categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDto updatedCategoryDto) {

        if(categoryService.getCategoryById(updatedCategoryDto.getCategoryId()) != null && userService.getUserById(updatedCategoryDto.getUserId()) != null) {
            categoryService.updateCategory(updatedCategoryDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
