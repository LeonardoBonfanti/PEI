package com.bonfanti.leonardo.pei.Utils;

import android.app.Application;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Usuário on 4/28/2017.
 */

public class FireApp extends Application
{
    private String userKey;
    private String userName;
    private String userSala;
    private String userTurma;
    private Integer admin;
    private Integer professor;
    private ArrayList<String> tests;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
        tests = new ArrayList<>();
    }

    public String getUserKey() { return userKey; }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserSala() {
        return userSala;
    }

    public void setUserSala(String userSala) {
        this.userSala = userSala;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getProfessor() {
        return professor;
    }

    public void setProfessor(Integer professor) {
        this.professor = professor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests.add(tests);
    }

    public String getUserTurma() {
        return userTurma;
    }

    public void setUserTurma(String userTurma) {
        this.userTurma = userTurma;
    }
}



