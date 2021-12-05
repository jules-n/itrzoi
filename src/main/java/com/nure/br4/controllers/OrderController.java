package com.nure.br4.controllers;


import com.nure.br4.domain.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nure.br4.persistent.*;

import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private DrinksService service;

    @GetMapping(path = "menu", produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getMenu() {
        var menu = Menu.builder().positions(service.getAll()).build();
        return new ResponseEntity(menu, HttpStatus.OK);
    }

    @GetMapping(path = "order-status", produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OrderStatus> gOrderStatus() {
        var status = OrderStatus.JustCreated;
        /*
         * create service for order
         * implement method getById
         */
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @PostMapping(path = "make-order", consumes=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity createOrder(@RequestBody Order order) {
        /*
        * use service for order
        * implement method save
        */
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "update-menu", consumes=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity addDrink(@RequestBody Drink drink) {
        service.save(drink);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(path = "update-order-status")
    public ResponseEntity updateStatus(@RequestAttribute String orderId, @RequestAttribute OrderStatus orderStatus) {
        /*
         * use service for order
         * implement method update
         * send to client
         */
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(path = "update-menu", consumes=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity updateMenu(@RequestBody Drink drink, @RequestAttribute String drinkId) {
        /*
         * add to drink service method update
         */
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(path = "remove-drink")
    public ResponseEntity updateMenu(@RequestAttribute String drinkId) {
        /*
         * use service for order
         * implement method update
         * send to client
         */
        service.delete(UUID.fromString(drinkId));
        return new ResponseEntity(HttpStatus.OK);
    }
}
