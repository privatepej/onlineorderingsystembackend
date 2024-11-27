package org.acumen.training.codes.controller;

import java.util.List;

import org.acumen.training.codes.dto.CategoryDTO;
import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.dto.UserDTO;
import org.acumen.training.codes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAllCategories() {
        List<ProductDTO> products = productService.getAllProduct();
        if (products != null && !products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
	    try {
	        boolean isInserted = productService.insertProduct(productDTO);
	        if (isInserted) {
	            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("Failed to add Product", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An unexpected error occurred while adding the product.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	 @PutMapping(path = "/updates")
	    public ResponseEntity<String> updateUser(@RequestBody ProductDTO productDTO) {
	        boolean isUpdated = productService.updateProduct(productDTO);
	        if (isUpdated) {
	            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User update failed", HttpStatus.BAD_REQUEST);
	        }
	    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProductName(@RequestParam Integer id, @RequestParam String newName) {
        boolean isUpdated = productService.updateProductName(id, newName);
        if (isUpdated) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Product", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/update/description")
    public ResponseEntity<String> updateProductDescription(@RequestParam Integer id, @RequestParam String newDescription) {
        boolean isUpdated = productService.updateProductDescription(id, newDescription);
        if (isUpdated) {
            return new ResponseEntity<>("Product Description updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Product Description", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/update/price")
    public ResponseEntity<String> updateProductPrice(@RequestParam Integer id, @RequestParam Double newPrice) {
        boolean isUpdated = productService.updateProductPrice(id, newPrice);
        if (isUpdated) {
            return new ResponseEntity<>("Product Price updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Product Price", HttpStatus.NOT_FOUND);
        }
    }
    
   
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductByName(@RequestParam String productName) {
        boolean isDeleted = productService.deleteProductByName(productName);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete Product", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteCategoryById(@RequestParam Integer productid) {
        boolean isDeleted = productService.deleteProductById(productid);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete Product", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/update/image")
    public ResponseEntity<String> updateProductImage(@RequestParam Integer id, @RequestParam String newImage) {
        boolean isUpdated = productService.updateProductImage(id, newImage);
        if (isUpdated) {
            return new ResponseEntity<>("Product Price updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Product Price", HttpStatus.NOT_FOUND);
        }
    }
    
    
    
	
}
