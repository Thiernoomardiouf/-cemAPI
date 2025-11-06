package com.cybersourcecem.services;

import com.cybersourcecem.entities.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);
    List<Category> findAll();
    void delete(long id);
    void editCategory(Category category, long id);
    Category findOne(long id);
    void deleteAll();
}
