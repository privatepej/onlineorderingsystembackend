package org.acumen.training.codes.controller;

import java.util.List;

import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(allowCredentials = "true", 
origins = "http://localhost:3000/",
allowedHeaders = {"Authorization","Content-Type",
		 	"Access-Control-Allow-Credentials", 
		 	"Access-Control-Allow-Headers", 
		 	"Access-Control-Allow-Origin",		
		 	"Access-Control-Allow-Methods"},
methods = {RequestMethod.GET, 
		    RequestMethod.POST,
		    RequestMethod.PUT,
		    RequestMethod.DELETE,
		    RequestMethod.PATCH
		    })
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;

	
	@GetMapping(path = "/sample", produces = MediaType.TEXT_PLAIN_VALUE)
	public String greet() {
		return "sample to check if controller is working";
	}
	
	@GetMapping(path = "/all/list")
	public List<ProductDTO> getAllProductList() {
        List<ProductDTO> products = productService.getAllProduct();
        return products;
	}

	
}
