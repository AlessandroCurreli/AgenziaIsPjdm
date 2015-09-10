package com.curdrome.agenziaispjdm.utility;

/**
 * Created by Alex on 04/09/2015.
 */
public class User {
    private String login;
    private String password;
    private int id;
    private String Role;

    //Setters and Getters

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
