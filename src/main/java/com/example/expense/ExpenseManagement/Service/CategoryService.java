package com.example.expense.ExpenseManagement.Service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.DTO.CategoryDto;
import com.example.expense.ExpenseManagement.Entity.Category;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;
import com.example.expense.ExpenseManagement.Repository.CategoryRepository;
import com.example.expense.ExpenseManagement.Repository.UserRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper mapper;

    public List<CategoryDto> getAllCategories(int userId) {

        List<Category> categories = null;
        List<CategoryDto> categoryDtos = new ArrayList<>();

        try {
            categories = categoryRepository.findCategoriesByUserId(userId);
            if(categories == null) {
                throw new ValidationException("No record found for the user " + userId, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured");
        }

        for(Category category : categories) {
            categoryDtos.add(this.mapCategoryToCategoryDto(category));
        }
        return categoryDtos;
    }

    public CategoryDto getCategoryById(int categoryId) {

        Category category = null;

        try {
            category = categoryRepository.findByCategoryId(categoryId);

            if(category == null) {
                throw new ValidationException("No record found for the given categoryId", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured");
        }
        return this.mapCategoryToCategoryDto(category);
    }

    public CategoryDto getCategoryByName(String categoryName) {

        Category category = null;
        try {
            category = categoryRepository.findByCategoryName(categoryName);

            if(category == null) {
                throw new ValidationException("No record found for the given categoryId", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ApplicationException("An unexpected error occured");
        }
        return this.mapCategoryToCategoryDto(category);
    }

    public void addCategory(CategoryDto categoryDto) {

        try {
            if(categoryDto == null || categoryDto.getCategoryId() == 0 || categoryDto.getUserId() == 0 ||
                (categoryDto.getCategoryName() instanceof String || categoryDto.getCategoryName().isEmpty())) {

                throw new ValidationException("Non nullable Field value must not be empty", HttpStatus.BAD_REQUEST);
            }
            categoryRepository.save(this.mapCategoryDtoToCategory(categoryDto));

        } catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while saving");
        }
    }

    public void updateCategory(CategoryDto updatedCategoryDto) {

        Category existingCategory = null;

        try {
            existingCategory = categoryRepository.findByCategoryId(updatedCategoryDto.getCategoryId());

            if(updatedCategoryDto.getCategoryName() instanceof String && ! updatedCategoryDto.getCategoryName().isEmpty())
                existingCategory.setCategoryName(updatedCategoryDto.getCategoryName());

            if(updatedCategoryDto.getDescription() instanceof String && ! updatedCategoryDto.getDescription().isEmpty())
                existingCategory.setDescription(updatedCategoryDto.getDescription());

            
        } catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while updating");
        }

        categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(int categoryId){
        categoryRepository.deleteById(categoryId);
    }
    
    public CategoryDto mapCategoryToCategoryDto(Category category) {

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        categoryDto.setUserId(category.getUser().getUserId());
        return categoryDto;
    }

    public Category mapCategoryDtoToCategory(CategoryDto categoryDto) {

        Category category = mapper.map(categoryDto, Category.class);
        category.setUser(userRepository.findByUserId(categoryDto.getUserId()));
        return category;
    }

}
