package com.bonfanti.leonardo.pei.Utils;

import java.util.Comparator;

/**
 * Created by Usuário on 4/26/2017.
 */

public final class CarComparators {

    private CarComparators() {
        //no instance
    }

    public static Comparator<UserDetails> getCarNameComparator() {
        return new CarNameComparator();
    }

    public static Comparator<UserDetails> getCarProfComparator() {
        return new CarProfComparator();
    }

    public static Comparator<UserDetails> getCarTestComparator() { return new CarTestComparator(); }

    public static Comparator<UserDetails> getCarResultComparator() { return new CarResultComparator(); }

    public static Comparator<UserDetails> getCarDataComparator() { return new CarDataComparator(); }

    private static class CarNameComparator implements Comparator<UserDetails> {

        @Override
        public int compare(final UserDetails userDetails1, final UserDetails userDetails2) {
            return userDetails1.getName().compareTo(userDetails2.getName());
        }
    }

    private static class CarProfComparator implements Comparator<UserDetails> {

        @Override
        public int compare(final UserDetails userDetails1, final UserDetails userDetails2) {
            return userDetails1.getProf().compareTo(userDetails2.getProf());
        }
    }

    private static class CarTestComparator implements Comparator<UserDetails> {

        @Override
        public int compare(final UserDetails userDetails1, final UserDetails userDetails2) {
            return userDetails1.getTest().compareTo(userDetails2.getTest());
        }
    }

    private static class CarDataComparator implements Comparator<UserDetails> {

        @Override
        public int compare(final UserDetails userDetails1, final UserDetails userDetails2) {
            return userDetails1.getData().compareTo(userDetails2.getData());
        }
    }

    private static class CarResultComparator implements Comparator<UserDetails> {

        @Override
        public int compare(final UserDetails userDetails1, final UserDetails userDetails2) {
            return userDetails1.getResult().compareTo(userDetails2.getResult());
        }
    }
}
