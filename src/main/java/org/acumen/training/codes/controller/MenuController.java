package org.acumen.training.codes.controller;

import java.util.List;

import org.acumen.training.codes.dto.ProductDTO;
import org.acumen.training.codes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
    private ProductService productService;
	
	@GetMapping(path = "/list")
    public ResponseEntity<List<ProductDTO>> getRestaurantMenu() {
        List<ProductDTO> products = productService.getAllProduct();
        if (products != null && !products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
