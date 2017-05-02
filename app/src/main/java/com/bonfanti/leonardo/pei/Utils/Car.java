package com.bonfanti.leonardo.pei.Utils;

/**
 * Created by Usuário on 4/26/2017.
 */

public class Car
{
    private String name;
    private String prof;
    private final int test;
    private String result;

    public Car(final String name, final String prof, final int test, final String result)
    {
        this.name = name;
        this.prof = prof;
        this.test = test;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getTest() {
        return test;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}