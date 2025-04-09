package com.libertywallet.service;


import com.libertywallet.dto.CategoryDto;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.mapper.CategoryMapper;
import com.libertywallet.entity.Category;
import com.libertywallet.entity.CategoryType;
import com.libertywallet.entity.User;
import com.libertywallet.repository.CategoryRepository;
import com.libertywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public Category createCategory(Long userId, CategoryType type,String name) {
        log.info("Creating new category for user(ID): {}",userId);
        Category category = new Category();
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new NotFoundException("User not found (user id:"+userId+")"));


        category.setUser(user);
        category.setType(type);
        category.setName(name);
        log.info("Created new category for user(ID): {}",userId);
        return categoryRepository.save(category);
    }

    public List<CategoryDto> getCategory(Long userId){
        log.info("Getting all category");
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        List<CategoryDto> dto = categoryList.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        if(categoryList.isEmpty()){
            log.warn("category list is empty!");
            throw  new NotFoundException("Category list is empty!");
        }
        log.info("Got all category");
        return dto;
    }

}
