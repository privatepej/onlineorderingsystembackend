package org.acumen.training.codes.controller;

import java.util.List;

import org.acumen.training.codes.dto.CategoryDTO;
import org.acumen.training.codes.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean isInserted = categoryService.insertCategory(categoryDTO);
        if (isInserted) {
            return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCategoryName(@RequestParam Integer id, @RequestParam String newName) {
        boolean isUpdated = categoryService.updateCategoryName(id, newName);
        if (isUpdated) {
            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update category", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategoryByName(@RequestParam String categoryName) {
        boolean isDeleted = categoryService.deleteCategoryByName(categoryName);
        if (isDeleted) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete category", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteCategoryById(@RequestParam Integer categoryid) {
        boolean isDeleted = categoryService.deleteCategoryById(categoryid);
        if (isDeleted) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete category", HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories != null && !categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
