package org.example.service;

import org.example.enums.Distance;
import org.example.enums.Fragile;
import org.example.enums.Size;
import org.example.enums.WorkLoad;
import org.example.exceptions.ArgumentIsNullException;
import org.example.exceptions.DistanceNotValidException;
import org.example.exceptions.MaximumFragileDistanceException;

public class DeliveryService {

    private static final Double MINIMUM_COST = 400.0; // минимальная стоимость доставки
    private static final Double MAXIMUM_FRAGILE_DISTANCE = 30.0; // максимальное расстояние доставки для хрупкого груза

    public Double calculateDeliveryCost(Double distance, Size size, Fragile fragile, WorkLoad workLoad) {
        validateData(distance, size, fragile, workLoad);
        validateDistance(distance, fragile);

        Double distanceCost = distanceCost(distance);
        Double sizeCost = sizeCost(size);
        Double fragileCost = fragileCost(fragile);

        Double subTotalCost = distanceCost + sizeCost + fragileCost;
        Double totalCost = subTotalCost * workLoad.getRatio();

        totalCost = Math.round(totalCost * 100.0) / 100.0; // округляем копейки

        return Math.max(totalCost, MINIMUM_COST);
    }

    private void validateData(Double distance, Size size, Fragile fragile, WorkLoad workLoad) {
        if (distance == null || size == null || fragile == null || workLoad == null) {
            throw new ArgumentIsNullException("All parameters must be not null");
        }
    }

    private void validateDistance(Double distance, Fragile fragile) {
        if (distance <= 0){
            throw new DistanceNotValidException("Distance must be more than 0");
        }
        if (fragile == Fragile.FRAGILE && distance > MAXIMUM_FRAGILE_DISTANCE) {
            throw new MaximumFragileDistanceException(String.format("Maximum fragile distance is %s", MAXIMUM_FRAGILE_DISTANCE));
        }
    }

    private Double distanceCost(Double distance) {
        if (distance > 30) {
            return Distance.OVER_30KM.getPrice();
        } else if (distance > 10) {
            return Distance.FROM_10KM_TO_30KM.getPrice();
        } else if (distance > 2) {
            return Distance.FROM_2KM_TO_10KM.getPrice();
        } else {
            return Distance.UP_T0_2KM.getPrice();
        }
    }

    private Double sizeCost(Size size) {
        if (size == Size.LARGE) {
            return Size.LARGE.getPrice();
        } else {
            return Size.SMALL.getPrice();
        }
    }

    private Double fragileCost(Fragile fragile) {
        if (fragile == Fragile.FRAGILE) {
            return Fragile.FRAGILE.getPrice();
        } else {
            return Fragile.NOT_FRAGILE.getPrice();
        }
    }
}
