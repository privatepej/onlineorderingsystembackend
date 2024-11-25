package org.acumen.training.codes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acumen.training.codes.dao.ProductDao;
import org.acumen.training.codes.dto.ProductDTO;
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
	
	
	
//	private Product mapDtoToEntity(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setId(productDTO.getId());
//        product.setPname(productDTO.getPname());
//        product.setPrice(productDTO.getPrice());
//        product.setDescription(productDTO.getDescription());
//        product.setCategortyname(productDTO.getCategortyname());
//        return product;
//    }

    private ProductDTO mapEntityToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPname(product.getPname());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategortyname(product.getCategortyname());
        return productDTO;
    }


}
