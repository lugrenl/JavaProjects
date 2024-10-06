public class Main {
    public static void main(String[] args) {
        Car mazdaCx5 = new CombustionCar("Mazda", 120, 2200, CarType.SUV);
        Car bmwm3 = new CombustionCar("BMW M3" , 220, 1800, CarType.COUPE);
        Car tesla = new ElectricCar("Tesla", 500, 2000, CarType.SEDAN);

        testTheCar(mazdaCx5);
        testTheCar(bmwm3);
        testTheCar(tesla);

        ElectricCar teslaModel3 = new ElectricCar("TeslaM3", 550, 2200, CarType.COUPE);
        testTheCar(teslaModel3);
    }

    public static void testTheCar(Car car){
        System.out.println("--------------");
        describeCar(car);
        driveCar(car);
        System.out.println("--------------");
    }

    public static void describeCar(Car car){
        System.out.println("Name: " + car.getName() + "\n" +
                "Type: " + car.getCarType() + "\n" +
                "HP: " + car.getHp() + "\n" +
                "Weight: " + car.getWeight());
    }

    public static void driveCar(Drivable drivable){
        drivable.drive();
    }
}
