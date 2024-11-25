package org.acumen.training.codes.dto;

import java.util.List;

public class UserOrderDTO {
    private Integer userId;
    private List<OrderItemDTO> orderItems;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}



