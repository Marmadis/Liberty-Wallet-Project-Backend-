package com.libertywallet.controller;

import com.libertywallet.dto.CategoryDto;
import com.libertywallet.service.CategoryService;
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

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<CategoryDto>> getCategory(@PathVariable  Long userId){
        List<CategoryDto> categoryList = categoryService.getCategory(userId);
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createCategory(@PathVariable Long userId, @RequestBody CategoryDto categoryDto)  {
        categoryService.createCategory(
                userId,
                categoryDto.getType(),
                categoryDto.getName()
        );

        return ResponseEntity.ok("Category was created");
    }

}
