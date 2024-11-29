package org.acumen.training.codes.controller;

import org.acumen.training.codes.dto.CartDTO;
import org.acumen.training.codes.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestParam Integer userId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        boolean isAdded = cartService.addToCart(userId, productId, quantity);
        if (isAdded) {
            return new ResponseEntity<>("Item added to cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add item to cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view")
    public ResponseEntity<CartDTO> viewCart(@RequestParam Integer userId) {
        CartDTO cart = cartService.viewCart(userId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    @DeleteMapping("/remove-item")
    public ResponseEntity<String> removeCartItem(@RequestParam Integer userId, @RequestParam Integer productId) {
        boolean isRemoved = cartService.removeCartItem(userId, productId);
        if (isRemoved) {
            return new ResponseEntity<>("Item removed from cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove item or item not found", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam Integer userId) {
        boolean isCleared = cartService.clearCart(userId);
        if (isCleared) {
            return new ResponseEntity<>("Cart cleared successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to clear cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
