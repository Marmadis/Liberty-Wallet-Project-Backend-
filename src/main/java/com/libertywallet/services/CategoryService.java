package com.libertywallet.services;


import com.libertywallet.exception.NotFoundException;
import com.libertywallet.models.Category;
import com.libertywallet.models.CategoryType;
import com.libertywallet.models.User;
import com.libertywallet.repositories.CategoryRepository;
import com.libertywallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public Category setCategory(Long userId, CategoryType categoryType,String name){
        log.info("Creating new category for user(ID): {}",userId);
        Category category = new Category();
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new NotFoundException("User not found (user id:"+userId+")"));
        category.setUser(user);
        category.setName(name);
        category.setType(categoryType);
        log.info("Created new category for user(ID): {}",userId);
        return categoryRepository.save(category);
    }

    public List<Category> getCategory(Long userId){
        log.info("Getting all category");
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        if(categoryList.isEmpty()){
            log.warn("category list is empty!");
        }
        log.info("Got all category");
        return categoryList;
    }

}
