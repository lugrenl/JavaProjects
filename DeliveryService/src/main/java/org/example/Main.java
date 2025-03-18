package org.example;

import org.example.enums.Fragile;
import org.example.enums.Size;
import org.example.enums.WorkLoad;
import org.example.service.DeliveryService;

public class Main {
    public static void main(String[] args) {
        DeliveryService deliveryService = new DeliveryService();

        Double deliveryCost = deliveryService.calculateDeliveryCost(
                100.0,
                Size.SMALL,
                Fragile.NOT_FRAGILE,
                WorkLoad.HEAVY
        );
        System.out.println(deliveryCost);
    }
}
