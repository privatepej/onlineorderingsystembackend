package org.acumen.training.codes.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.acumen.training.codes.dao.ProductDao;
import org.acumen.training.codes.dao.SalesDao;
import org.acumen.training.codes.dao.UserOrdersDao;
import org.acumen.training.codes.dto.OrderItemDTO;
import org.acumen.training.codes.dto.UserOrderDTO;
import org.acumen.training.codes.model.Product;
import org.acumen.training.codes.model.Sales;
import org.acumen.training.codes.model.UserOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
    @Autowired
    private UserOrdersDao userOrdersDao;

    @Autowired
    private SalesDao salesDao;

    @Autowired
    private ProductDao productDao;

    public boolean createOrder(UserOrderDTO orderDTO) {
        try {
            UserOrders userOrder = new UserOrders();
            userOrder.setUserid(orderDTO.getUserId());
            userOrder.setOrderon(LocalDate.now());
            userOrder.setOrdert(0.0);

            userOrder.setPaymentid(null);

            boolean orderCreated = userOrdersDao.createOrder(userOrder);
            if (!orderCreated) {
                return false;
            }

            double totalOrderAmount = 0.0;
            for (OrderItemDTO item : orderDTO.getOrderItems()) {
                Product product = productDao.selectProductById(item.getProductId());
                if (product == null) {
                    return false; 
                }

                double itemTotal = product.getPrice() * item.getQuantity();
                totalOrderAmount += itemTotal;

                Sales sales = new Sales();
                sales.setOrderid(userOrder.getOrderid());
                sales.setItemno(item.getProductId());
                sales.setQty(item.getQuantity());
                sales.setSoldon(LocalDate.now());
                sales.setPayment("Pending");

                boolean salesCreated = salesDao.createSales(sales);
                if (!salesCreated) {
                    return false;
                }
            }

            userOrder.setOrdert(totalOrderAmount);
            return userOrdersDao.updateOrder(userOrder);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserOrderDTO getOrderByUserId(Integer userId) {
        List<UserOrders> userOrdersList = userOrdersDao.getOrdersByUserId(userId);
        if (userOrdersList == null || userOrdersList.isEmpty()) {
            return null;
        }

        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setUserId(userId);

        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (UserOrders userOrder : userOrdersList) {
            for (Sales sales : userOrder.getSales()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setProductId(sales.getItemno());
                orderItemDTO.setQuantity(sales.getQty());
                orderItems.add(orderItemDTO);
            }
        }
        userOrderDTO.setOrderItems(orderItems);
        return userOrderDTO;
    }
    
    public boolean removeItemFromOrder(Integer salesId) {
        return salesDao.deleteSalesItem(salesId);
    }


    public boolean removeAllItemsFromOrder(Integer orderId) {
        boolean itemsRemoved = salesDao.deleteAllSalesItemsByOrderId(orderId);
        if (itemsRemoved) {
            return userOrdersDao.deleteOrder(orderId);
        }
        return false;
    }

}
