package com.bonfanti.leonardo.pei.Utils;

/**
 * Created by Usuário on 4/26/2017.
 */

public class UserDetails
{
    private String name;
    private String prof;
    private String test;
    private String data;
    private String result;

    public static final int PROF = 0;
    public static final int TESTE = 1;
    public static final int DATA = 2;
    public static final int RESULT = 3;

    public UserDetails(final String name, final String prof, final String test, final String data, final String result)
    {
        this.name = name;
        this.prof = prof;
        this.test = test;
        this.data = data;
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

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}