package org.acumen.training.codes.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.dto.ProductJoinImageDTO;
import org.acumen.training.codes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productService.getAllProduct();
        if (products != null && !products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
	
	@GetMapping("/listWithImage")
    public ResponseEntity<List<ProductJoinImageDTO>> getAllProductWithImage() {
        List<ProductJoinImageDTO> productswithimg = productService.getAllProductWithImage();
        if (productswithimg != null && !productswithimg.isEmpty()) {
            return new ResponseEntity<>(productswithimg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

	//addjson
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
	
	//addform
	@PostMapping("/addForm")
	public ResponseEntity<?> addProductForm(
			String pname,
	        Double price,
	        String description,
	        String categoryname,
	        @RequestParam(value = "imagename", required = false) MultipartFile image) {
		
	    try {
	        ProductJoinImageDTO productJoinImageDTO = new ProductJoinImageDTO();
	        productJoinImageDTO.setPname(pname);
	        productJoinImageDTO.setPrice(price);
	        productJoinImageDTO.setDescription(description);
	        productJoinImageDTO.setCategoryname(categoryname);

	        boolean isInserted = productService.insertProductForm(productJoinImageDTO, image);

	        if (isInserted) {
	            ProductJoinImageDTO savedProduct = productService.getProductByName(pname);

	            return ResponseEntity.ok(savedProduct);
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	@GetMapping("/images/{filename}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
	    try {
	    	Path filePath = Paths.get("src/main/resources/img").resolve(filename).normalize();
	        Resource resource = new UrlResource(filePath.toUri());

	        if (!resource.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok()
	        	    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
	        	    .body(resource);
	    } catch (MalformedURLException e) {
	        return ResponseEntity.badRequest().build();
	    }
	}


	
//	 @PutMapping(path = "/updates")
//	    public ResponseEntity<String> updateUser(@RequestBody ProductDTO productDTO) {
//	        boolean isUpdated = productService.updateProduct(productDTO);
//	        if (isUpdated) {
//	            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
//	        } else {
//	            return new ResponseEntity<>("User update failed", HttpStatus.BAD_REQUEST);
//	        }
//	    }
	
	@PutMapping("/updates")
	public ResponseEntity<ProductJoinImageDTO> updateProduct(
	        @RequestParam("id") Integer id,
	        @RequestParam("pname") String pname,
	        @RequestParam("price") Double price,
	        @RequestParam("description") String description,
	        @RequestParam("categoryname") String categoryname,
	        @RequestParam(value = "imagename", required = false) MultipartFile image) {

	    try {
	        // DTO mapping
	        ProductJoinImageDTO productJoinImageDTO = new ProductJoinImageDTO();
	        productJoinImageDTO.setId(id);
	        productJoinImageDTO.setPname(pname);
	        productJoinImageDTO.setPrice(price);
	        productJoinImageDTO.setDescription(description);
	        productJoinImageDTO.setCategoryname(categoryname);

	        boolean isUpdated = productService.updateProductForm(productJoinImageDTO, image);

	        if (isUpdated) {
	            return ResponseEntity.ok(productJoinImageDTO); // Return updated DTO
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
