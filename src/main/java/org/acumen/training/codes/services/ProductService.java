package org.acumen.training.codes.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.acumen.training.codes.dao.CategoryDao;
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
    private CategoryDao categoryDao;
	
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
    
    
    
    
    
    
    public boolean updateProduct(ProductDTO productDTO) {
        Product existingProduct = productDao.selectProductById(productDTO.getId());
        if (existingProduct == null) {
            return false;
        }

        if (productDTO.getPname() != null) {
        	existingProduct.setPname(productDTO.getPname());
        }
        if (productDTO.getPrice() != null) {
        	existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDescription() != null) {
        	existingProduct.setDescription(productDTO.getDescription());
        }
        if (productDTO.getCategoryname() != null) {
        	existingProduct.setCategoryname(productDTO.getCategoryname());
        }
        
        return productDao.updateProduct(existingProduct);
    }
    
    
    
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
