package org.acumen.training.codes.controller;

import org.acumen.training.codes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;

	
	@GetMapping(path = "/sample", produces = MediaType.TEXT_PLAIN_VALUE)
	public String greet() {
		return "sample to check if controller is working";
	}
	
//	@GetMapping(path = "/all/list")
//	public List<ProductDTO> getAllProductList() {
//        List<ProductDTO> products = productService.getAllProduct();
//        return products;
//	}

	
}
