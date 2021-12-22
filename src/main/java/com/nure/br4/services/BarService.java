package com.nure.br4.services;


import com.nure.br4.domain.dto.OrderPostDTO;
import com.nure.br4.domain.models.*;
import com.nure.br4.persistent.*;
import lombok.extern.java.Log;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.UUID;

@WebService(targetNamespace = "http://com.nure.br4/bar/service", endpointInterface = "com.nure.br4.services.BarService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Log
public class BarService {

    private DrinksService drinksService;
    private OrderService orderService;

    public BarService (DrinksService drinksService, OrderService orderService) {
        this.drinksService = drinksService;
        this.orderService = orderService;
    }

    @WebMethod
    public Menu getMenu() {
        var menu = Menu.builder().positions(drinksService.getAll()).build();
        return menu;
    }

    @WebMethod
    public OrderStatus getOrderStatus(int orderId) {
        return orderService.getById(orderId).getStatus();
    }

    @WebMethod
    public boolean createOrder(OrderPostDTO dto) {
        try {
            orderService.save(dto);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
        return true;
    }

    @WebMethod
    public boolean addDrink(Drink drink) {
        try {
            drinksService.save(drink);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
        return true;
    }

    @WebMethod
    public boolean updateStatus(int orderId, OrderStatus status) {
        try {
            orderService.updateStatus(orderId, status);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
        return true;
    }

    @WebMethod
    public boolean updateMenu(Drink drink, String drinkId) {
        try {
            drinksService.update(UUID.fromString(drinkId), drink);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
        return true;
    }

    @WebMethod
    public boolean removePosition(String drinkId) {
        try {
            drinksService.delete(UUID.fromString(drinkId));
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
        return true;
    }
}
