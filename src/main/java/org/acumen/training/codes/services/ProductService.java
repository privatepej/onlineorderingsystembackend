package org.acumen.training.codes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acumen.training.codes.dao.ProductDao;
import org.acumen.training.codes.dto.CategoryDTO;
import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.model.Category;
import org.acumen.training.codes.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
    private ProductDao productDao;
	
	@Transactional
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productDao.selectAllProduct();
        return products.stream().map(
        		product -> mapEntityToDto(product))
        		.collect(Collectors.toList());
    }
	
	public boolean insertProduct(ProductDTO productDTO) {
		Product product = mapDtoToEntity(productDTO);
        return productDao.insertProduct(product);
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

    public boolean deleteProductByName(String productName) {
        return productDao.deleteProductByName(productName);
    }
    
    public boolean deleteProductById(Integer productid) {
        return productDao.deleteById(productid);
    }
    
    public boolean updateProductImage(Integer id, String productImage) {
        return productDao.updateProductImage(id,productImage);
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
