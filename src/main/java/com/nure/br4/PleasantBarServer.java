package com.nure.br4;


import com.nure.br4.persistent.DrinksService;
import com.nure.br4.persistent.OrderService;
import com.nure.br4.services.BarService;

import javax.xml.ws.Endpoint;
import java.util.Scanner;

public class PleasantBarServer {
    final static String address = "http://localhost:9090/bar-server";

    public static void main(String[] args) throws InterruptedException {

            Endpoint.publish(address, new BarService(new DrinksService(), new OrderService()));
            System.out.println("Server ready at " + address + " ...");

            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            sc.close();
            Thread.sleep(3 * 60 * 1000); // wait before exit
            System.out.println("Server exiting");
            System.exit(0);
    }
}
