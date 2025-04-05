package com.libertywallet.controllers;

import com.libertywallet.dto.CategoryRequest;
import com.libertywallet.models.Category;
import com.libertywallet.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getcategory/{userId}")
    public ResponseEntity<List<Category>> getCategory(@PathVariable  Long userId){
        List<Category> categoryList = categoryService.getCategory(userId);
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/setcategory/{userId}")
    public ResponseEntity<String> setCategory(@PathVariable Long userId, @RequestBody CategoryRequest categoryRequest){
        categoryService.setCategory(
                userId,
                categoryRequest.getCategoryType(),
                categoryRequest.getName()
        );

        return ResponseEntity.ok("Category was created");
    }

}
