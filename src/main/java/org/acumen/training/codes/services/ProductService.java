package org.acumen.training.codes.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.acumen.training.codes.dao.ProductDao;
import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.dto.ProductJoinImageDTO;
import org.acumen.training.codes.model.Product;
import org.acumen.training.codes.model.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Tuple;

@Service
public class ProductService {

	@Autowired
    private ProductDao productDao;
	
	@Autowired
	private FileService fileService;
	
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productDao.selectAllProduct();
        return products.stream().map(
        		product -> mapEntityToDto(product))
        		.collect(Collectors.toList());
    }
	
	public boolean insertProduct(ProductDTO productDTO) {
		if(productDao.doesProductExist(productDTO.getPname())) {
	        throw new IllegalArgumentException("Product with the same name already exists");
		}
		Product product = mapDtoToEntity(productDTO);
        return productDao.insertProduct(product);
    }
	
// PICUTRE
	public boolean insertProductForm(ProductJoinImageDTO productJoinImageDTO, MultipartFile image) {

		
	    // Validate product name
	    if (productDao.doesProductExist(productJoinImageDTO.getPname())) {
	        throw new IllegalArgumentException("Product with the same name already exists");
	    }
	    
	    if (image != null && !image.isEmpty() && productDao.doesImageExist(image.getOriginalFilename())) {
	        throw new IllegalArgumentException("Image name already exists: " + image.getOriginalFilename());
	    }

	    // Insert product into the database
	    Product product = new Product();
	    product.setPname(productJoinImageDTO.getPname());
	    product.setPrice(productJoinImageDTO.getPrice());
	    product.setDescription(productJoinImageDTO.getDescription());
	    product.setCategoryname(productJoinImageDTO.getCategoryname());

	    boolean isProductInserted = productDao.insertProduct(product);

	    if (isProductInserted) {
	        try {
	            String imageName;

	            if (image != null && !image.isEmpty()) {
	            	imageName = fileService.saveImage(image);
	            } else {
	                imageName = "default.png";
	            }

	            ProductImages productImage = new ProductImages();
	            productImage.setPname(productJoinImageDTO.getPname());
	            productImage.setImagename(imageName);
	            productDao.saveProductImage(productImage);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    return isProductInserted;
	}


	public void saveProductImage(ProductImages productImage) {
	    productDao.saveProductImage(productImage);
	}

	
// PICTURE 

	public ProductJoinImageDTO getProductByName(String pname) {
	    Product product = productDao.findByProductName(pname);
	    ProductImages productImage = productDao.findImageByProductName(pname);
	    return mapEntityToDtoImage(product, productImage);
	}



    public boolean updateProductName(Integer id, String newProductName) {
        return productDao.updateProductname(id, newProductName);
    }
    
    public boolean updateProductDescription(Integer id, String newProductDescription) {
        return productDao.updateProductDescription(id, newProductDescription);
    }
    
    public boolean updateProductPrice(Integer id, Double newProductPrice) {
        return productDao.updateProductPrice(id, newProductPrice);
    }

    public boolean deleteProductByName(String pname) {
        ProductImages productImage = productDao.findImageByProductName(pname);
        if (productImage != null && productImage.getImagename() != null) {
            String imagePath = "src/main/resources/img/" + productImage.getImagename();
            try {
                fileService.deleteImage(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to delete image file: " + productImage.getImagename());
            }
        }
        return productDao.deleteProductByName(pname);
    }
    
    public boolean deleteProductById(Integer productid) {
        return productDao.deleteById(productid);
    }
    
    public boolean updateProductImage(Integer id, String productImage) {
        return productDao.updateProductImage(id,productImage);
    }
    
    
    
    
    
    
//    public boolean updateProduct(ProductDTO productDTO) {
//        Product existingProduct = productDao.selectProductById(productDTO.getId());
//        if (existingProduct == null) {
//            return false;
//        }
//
//        if (productDTO.getPname() != null) {
//        	existingProduct.setPname(productDTO.getPname());
//        }
//        if (productDTO.getPrice() != null) {
//        	existingProduct.setPrice(productDTO.getPrice());
//        }
//        if (productDTO.getDescription() != null) {
//        	existingProduct.setDescription(productDTO.getDescription());
//        }
//        if (productDTO.getCategoryname() != null) {
//        	existingProduct.setCategoryname(productDTO.getCategoryname());
//        }
//        
//        return productDao.updateProduct(existingProduct);
//    }
    
    public boolean updateProductForm(ProductJoinImageDTO productJoinImageDTO, MultipartFile image) {
        Product existingProduct = productDao.selectProductById(productJoinImageDTO.getId());
//        if (existingProduct == null) {
//            throw new IllegalArgumentException("Product does not exist: " + productJoinImageDTO.getId());
//        }
        
//        if (productDao.doesProductExist(productJoinImageDTO.getPname())) {
//	        throw new IllegalArgumentException("Product with the same name already exists");
//	    }
//        
     // Check if the new product name already exists (but not for the same product)
        if (productJoinImageDTO.getPname() != null &&
            !existingProduct.getPname().equals(productJoinImageDTO.getPname()) &&
            productDao.doesProductExist(productJoinImageDTO.getPname())) {
            throw new IllegalArgumentException("Product with the same name already exists: " + productJoinImageDTO.getPname());
        }

      
	    if (image != null && !image.isEmpty() && productDao.doesImageExist(image.getOriginalFilename())) {
	        throw new IllegalArgumentException("Image name already exists: " + image.getOriginalFilename());
	    }
        
        

        // Update fields
        if (productJoinImageDTO.getPname() != null) {
            existingProduct.setPname(productJoinImageDTO.getPname());
        }
        if (productJoinImageDTO.getPrice() != null) {
            existingProduct.setPrice(productJoinImageDTO.getPrice());
        }
        if (productJoinImageDTO.getDescription() != null) {
            existingProduct.setDescription(productJoinImageDTO.getDescription());
        }
        if (productJoinImageDTO.getCategoryname() != null) {
            existingProduct.setCategoryname(productJoinImageDTO.getCategoryname());
        }

        boolean isProductUpdated = productDao.updateProduct(existingProduct);

        // Handle image update if provided
        if (isProductUpdated && image != null && !image.isEmpty()) {
            try {
                // Retrieve the associated ProductImages entity
                ProductImages productImage = productDao.findImageByProductName(existingProduct.getPname());
                String currentImageName = productImage != null ? productImage.getImagename() : "default.png";

                // Delete the old image if it's not default.png
                if (!"default.png".equals(currentImageName)) {
                    fileService.deleteImage("src/main/resources/img/" + currentImageName);
                }

                // Save the new image
                String imageName = fileService.saveImage(image);
                if (productImage != null) {
                    // Update the existing ProductImages entity
                    productImage.setImagename(imageName);
                    productDao.updateProductImage(productImage); // Make sure this method updates the image properly
//                  productDao.saveProductImage(productImage);
                } else {
                    // Create a new ProductImages entity
                    ProductImages newProductImage = new ProductImages();
                    newProductImage.setPname(existingProduct.getPname());
                    newProductImage.setImagename(imageName);
                    productDao.saveProductImage(newProductImage);
                }
                
                productJoinImageDTO.setImagename(imageName);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return isProductUpdated;
    }
    
//    public boolean updateProductForm(ProductJoinImageDTO productJoinImageDTO, MultipartFile image) {
//        Product existingProduct = productDao.selectProductById(productJoinImageDTO.getId());
//        if (existingProduct == null) {
//            throw new IllegalArgumentException("Product does not exist: " + productJoinImageDTO.getId());
//        }
//
//        // Update fields
//        if (productJoinImageDTO.getPname() != null) {
//            existingProduct.setPname(productJoinImageDTO.getPname());
//        }
//        if (productJoinImageDTO.getPrice() != null) {
//            existingProduct.setPrice(productJoinImageDTO.getPrice());
//        }
//        if (productJoinImageDTO.getDescription() != null) {
//            existingProduct.setDescription(productJoinImageDTO.getDescription());
//        }
//        if (productJoinImageDTO.getCategoryname() != null) {
//            existingProduct.setCategoryname(productJoinImageDTO.getCategoryname());
//        }
//
//        boolean isProductUpdated = productDao.updateProduct(existingProduct);
//
//        // Handle image update if provided
//        if (isProductUpdated && image != null && !image.isEmpty()) {
//            try {
//                // Delete the old image if it's not default.png
//                ProductImages productImage = productDao.findImageByProductName(existingProduct.getPname());
//                String currentImageName = productImage != null ? productImage.getImagename() : "default.png";
//                
//                if (!"default.png".equals(currentImageName)) {
//                    fileService.deleteImage("src/main/resources/img/" + currentImageName);
//                }
//
//                // Save the new image
//                String imageName = fileService.saveImage(image);
//                if (productImage != null) {
//                    productImage.setImagename(imageName);
//                    productDao.saveProductImage(productImage);
//                } else {
//                    ProductImages newProductImage = new ProductImages();
//                    newProductImage.setPname(existingProduct.getPname());
//                    newProductImage.setImagename(imageName);
//                    productDao.saveProductImage(newProductImage);
//                }
//
//                // Update the imagename in the DTO for response
//                productJoinImageDTO.setImagename(imageName);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//        } else if (productDao.findImageByProductName(existingProduct.getPname()) != null) {
//            ProductImages existingProductImage = productDao.findImageByProductName(existingProduct.getPname());
//            productJoinImageDTO.setImagename(existingProductImage.getImagename());
//        } else {
//            productJoinImageDTO.setImagename("default.png");
//        }
//
//        return isProductUpdated;
//    }



    
    
    public List<ProductJoinImageDTO> getAllProductWithImage() {
        List<Tuple> productTuples = productDao.JoinTableProductImage();

        return productTuples.stream()
            .map(tuple -> {
                Product product = tuple.get(0, Product.class); // Get Product entity
                ProductImages productImages = tuple.get(1, ProductImages.class); // Get ProductImages entity
                return mapEntityToDtoImage(product, productImages);
            })
            .collect(Collectors.toList());
    }
    
    private ProductJoinImageDTO mapEntityToDtoImage(Product product, ProductImages productImages) {
        ProductJoinImageDTO productJoinImageDTO = new ProductJoinImageDTO();
        productJoinImageDTO.setId(product.getId());
        productJoinImageDTO.setPname(product.getPname());
        productJoinImageDTO.setPrice(product.getPrice());
        productJoinImageDTO.setDescription(product.getDescription());
        productJoinImageDTO.setCategoryname(product.getCategoryname());
        productJoinImageDTO.setImagename(productImages != null ? productImages.getImagename() : "default.png"); // Handle null case
        return productJoinImageDTO;
    }
   
	
	private Product mapDtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setPname(productDTO.getPname());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategoryname(productDTO.getCategoryname());
        return product;
    }

    private ProductDTO mapEntityToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPname(product.getPname());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryname(product.getCategoryname());
        return productDTO;
    }


}
