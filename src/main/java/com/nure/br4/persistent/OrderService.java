package com.nure.br4.persistent;

import com.nure.br4.domain.dto.OrderPostDTO;
import com.nure.br4.domain.models.Order;
import com.nure.br4.domain.models.OrderStatus;

import java.util.List;

public class OrderService {

    private List<Order> orders = List.of();

    public Order getById(int id) {
        return orders.get(id);
    }

    public void save(OrderPostDTO dto) {
        var order = Order.builder()
                .id(orders.size() + 1)
                .place(dto.getPlace())
                .positions(dto.getPositions())
                .status(OrderStatus.JustCreated)
                .build();
        orders.add(order);
    }

    public void updateStatus(int id, OrderStatus status) {
        var order = orders.get(id);
        order.setStatus(status);
        orders.remove(id);
        orders.add(id, order);
    }
}
