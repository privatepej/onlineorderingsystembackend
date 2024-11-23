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
        return products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setPname(product.getPname());
            dto.setPrice(product.getPrice());
            dto.setDescription(product.getDescription());
            dto.setCategoryid(product.getCategortyid());
            return dto;
        }).collect(Collectors.toList());
    }

}
