package org.example;

import java.util.concurrent.Semaphore;

public class Parking {
    private static final boolean[] PARKING_PLACES = new boolean[5];
    public static int carsCount = 10;
    // определите ваш семафор
    private static final Semaphore semaphore = new Semaphore(PARKING_PLACES.length, true);

    public static void main(String[] args) {
        // запустите процесс парковки
        for (int i = 0; i < carsCount; i++) {
            new Thread(new Car(i)).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                int parkingNumber = 0;
                synchronized (PARKING_PLACES) {
                    for (int i = 0; i < PARKING_PLACES.length; i++) {
                        if (!PARKING_PLACES[i]) {
                            PARKING_PLACES[i] = true;
                            parkingNumber = i;
                            break;
                        }
                    }
                }
                System.out.println("Машина " + carNumber + " въехала в парковочное место №" + parkingNumber);
                Thread.sleep(5000);
                System.out.println("Машина " + carNumber + " выехала из парковочного места №" + parkingNumber);
                synchronized (PARKING_PLACES) {
                    PARKING_PLACES[parkingNumber] = false;
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}