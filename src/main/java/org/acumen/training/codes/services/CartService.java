package org.acumen.training.codes.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.acumen.training.codes.dao.ProductDao;
import org.acumen.training.codes.dao.SalesDao;
import org.acumen.training.codes.dao.UserOrdersDao;
import org.acumen.training.codes.dto.CartDTO;
import org.acumen.training.codes.dto.CartItemDTO;
import org.acumen.training.codes.model.Product;
import org.acumen.training.codes.model.ProductImages;
import org.acumen.training.codes.model.Sales;
import org.acumen.training.codes.model.UserOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private SalesDao salesDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private UserOrdersDao userOrdersDao;

	public boolean addToCart(Integer userId, Integer productId, Integer quantity) {
    	    
        UserOrders cart = userOrdersDao.getPendingOrderByUserId(userId);
        if (cart == null) {
            cart = new UserOrders();
            cart.setUserid(userId);
            cart.setOrdert(0.0);
            cart.setOrderon(LocalDate.now());
            cart.setStatus("CART");
            userOrdersDao.createOrder(cart);
        }
       
        Sales existingItem = salesDao.getCartItem(cart.getOrderid(), productId);
        if (existingItem != null) {
            existingItem.setQty(existingItem.getQty() + quantity);
            return salesDao.updateCartItem(existingItem);
        } else {
            Sales newItem = new Sales();
            newItem.setOrderid(cart.getOrderid());
            newItem.setItemno(productId);
            newItem.setQty(quantity);
            newItem.setSoldon(LocalDate.now());
            newItem.setPayment("Pending");
            return salesDao.saveCartItem(newItem);
        }
    }

    public CartDTO viewCart(Integer userId) {
        UserOrders cart = userOrdersDao.getPendingOrderByUserId(userId);
        if (cart == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(userId);

        List<CartItemDTO> cartItems = new ArrayList<>();
        double cartTotal = 0.0;

        for (Sales sales : salesDao.getCartItems(cart.getOrderid())) {
            CartItemDTO item = new CartItemDTO();
            
            Product product = productDao.selectProductById(sales.getItemno());
            ProductImages productImage = productDao.findImageByProductName(product.getPname());

            item.setProductId(product.getId());
            item.setProductName(product.getPname());
            item.setPrice(product.getPrice());
            item.setQuantity(sales.getQty());
            item.setTotalPrice(product.getPrice() * sales.getQty());
            item.setImagename(productImage != null ? productImage.getImagename() : "default.png");
            cartItems.add(item);

            cartTotal += item.getTotalPrice();
        }

        cartDTO.setCartItems(cartItems);
        cartDTO.setCartTotal(cartTotal);
        return cartDTO;
    }
    
    public boolean removeCartItem(Integer userId, Integer productId) {
        UserOrders cart = userOrdersDao.getPendingOrderByUserId(userId);
        if (cart == null) {
            return false; // No cart to remove items from
        }

        return salesDao.removeCartItem(cart.getOrderid(), productId);
    }


    public boolean clearCart(Integer userId) {
        UserOrders cart = userOrdersDao.getPendingOrderByUserId(userId);
        if (cart != null) {
            return salesDao.clearCart(cart.getOrderid());
        }
        return false;
    }
}
