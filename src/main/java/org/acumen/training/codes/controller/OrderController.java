package org.acumen.training.codes.controller;

import org.acumen.training.codes.dto.UserOrderDTO;
import org.acumen.training.codes.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody UserOrderDTO orderDTO) {
        boolean isOrderCreated = orderService.createOrder(orderDTO);
        if (isOrderCreated) {
            return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<>("Failed to create order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/review")
    public ResponseEntity<UserOrderDTO> reviewOrder(@RequestParam Integer userId) {
        UserOrderDTO orderDTO = orderService.getOrderByUserId(userId);
        if (orderDTO != null) {
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    
    @DeleteMapping("/remove-item")
    public ResponseEntity<String> removeItemFromOrder(@RequestParam Integer salesId) {
        boolean isRemoved = orderService.removeItemFromOrder(salesId);
        if (isRemoved) {
            return new ResponseEntity<>("Item removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove item or item not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove-all-items")
    public ResponseEntity<String> removeAllItemsFromOrder(@RequestParam Integer orderId) {
        boolean isRemoved = orderService.removeAllItemsFromOrder(orderId);
        if (isRemoved) {
            return new ResponseEntity<>("All items and order removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove items or order not found", HttpStatus.NOT_FOUND);
        }
    }

}

