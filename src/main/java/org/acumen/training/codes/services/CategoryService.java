package org.acumen.training.codes.services;

import org.acumen.training.codes.dao.CategoryDao;
import org.acumen.training.codes.model.Category;
import org.acumen.training.codes.model.Product;
import org.acumen.training.codes.model.Users;
import org.acumen.training.codes.dto.CategoryDTO;
import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public boolean insertCategory(CategoryDTO categoryDTO) {
        Category category = mapDtoToEntity(categoryDTO);
        return categoryDao.insertCategory(category);
    }

    public boolean updateCategoryName(Integer id, String newCategoryName) {
        return categoryDao.updateCategoryname(id, newCategoryName);
    }

    public boolean deleteCategoryByName(String categoryName) {
        return categoryDao.deleteCategoryByName(categoryName);
    }
    
    public boolean deleteCategoryById(Integer categoryid) {
        return categoryDao.deleteById(categoryid);
    }
    

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryDao.getAllCategories();
        return categories.stream().map(
        		product -> mapEntityToDto(product))
        		.collect(Collectors.toList());
    }
    
	private Category mapDtoToEntity(CategoryDTO categoryDTO) {
     Category category = new Category();
     category.setCname(categoryDTO.getCname());
	  return category;
	}
    
    private CategoryDTO mapEntityToDto(Category category) {
    	CategoryDTO categoryDTO = new CategoryDTO();
    	categoryDTO.setId(category.getId());
    	categoryDTO.setCname(category.getCname());
        return categoryDTO;
    }
}
