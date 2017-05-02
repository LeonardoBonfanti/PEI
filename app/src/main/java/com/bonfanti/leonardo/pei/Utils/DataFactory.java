package com.bonfanti.leonardo.pei.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuário on 4/26/2017.
 */

public final class DataFactory {

    /**
     * Creates a list of cars.
     *
     * @return The created list of cars.
     */
    public static List<Car> createCarList()
    {
        final Car audiA1 = new Car("Abigail", "C", 1, "Alfa");
        final Car audiA2 = new Car("Abigail2", "C", 1, "Alfa");
        final Car audiA3 = new Car("Gertrudes", "A", 3, "Beta");
        final Car audiA4 = new Car("Zildo", "B", 4, "Alfa");
        final Car audiA5 = new Car("Leonardo", "C", 2, "Gama");

        final List<Car> cars = new ArrayList<>();
        cars.add(audiA3);
        cars.add(audiA1);
        cars.add(audiA2);
        cars.add(audiA4);
        cars.add(audiA5);

        return cars;
    }

}
