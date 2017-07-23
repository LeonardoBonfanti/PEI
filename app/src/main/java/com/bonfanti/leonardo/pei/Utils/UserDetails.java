package com.bonfanti.leonardo.pei.Utils;

import java.util.ArrayList;

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
    private ArrayList<ArrayList> respostas;

    public UserDetails(final String prof, final String name, final String test, final String data, final String result, final ArrayList<ArrayList> respostas)
    {
        this.name = name;
        this.prof = prof;
        this.test = test;
        this.data = data;
        this.result = result;
        this.respostas = respostas;
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

    public ArrayList<ArrayList> getRespostas() {
        return respostas;
    }

    public void setRespostas(ArrayList<ArrayList> respostas) {
        this.respostas = respostas;
    }
}