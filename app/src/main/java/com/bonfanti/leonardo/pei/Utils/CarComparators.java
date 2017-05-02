package com.bonfanti.leonardo.pei.Utils;

import java.util.Comparator;

/**
 * Created by Usuário on 4/26/2017.
 */

public final class CarComparators {

    private CarComparators() {
        //no instance
    }

    public static Comparator<Car> getCarNameComparator() {
        return new CarNameComparator();
    }

    public static Comparator<Car> getCarProfComparator() {
        return new CarNameComparator();
    }

    public static Comparator<Car> getCarTestComparator() {
        return new CarNameComparator();
    }

    public static Comparator<Car> getCarResultComparator() {
        return new CarNameComparator();
    }

    private static class CarNameComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getName().compareTo(car2.getName());
        }
    }

    private static class CarProfComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getProf().compareTo(car2.getProf());
        }
    }

    private static class CarTestComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            if (car1.getTest() < car2.getTest()) return -1;
            if (car1.getTest() > car2.getTest()) return 1;
            return 0;
        }
    }

    private static class CarResultComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getResult().compareTo(car2.getResult());
        }
    }
}
