package com.curdrome.agenziaispjdm.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 04/09/2015.
 */
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private double phone;
    private String agent_firstname;
    private String agent_lastname;
    private double agent_phone;
    private String email;
    private String role;
    private List<Property> properties;

    //Setters and Getters

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }

    public String getAgentFirstName() {
        return agent_firstname;
    }

    public void setAgentFirstName(String agent_firstname) {
        this.agent_firstname = agent_firstname;
    }

    public String getAgentLastName() {
        return agent_lastname;
    }

    public void setAgentLastName(String agent_lastname) {
        this.agent_lastname = agent_lastname;
    }

    public double getAgentPhone() {
        return agent_phone;
    }

    public void setAgentPhone(double agent_phone) {
        this.agent_phone = agent_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    //metodo per la conversione di un oggetto JSON a java
    public User toJava(String json) {
        /*Gson gson = new Gson();

        User user = gson.fromJson(json, User.class);*/

        User user = null;

        try {
            JSONObject jo = new JSONObject(json);
            user = new User();
            user.setId(jo.getInt("id"));
            user.setLogin(jo.getString("login"));
            user.setPassword(jo.getString("password"));
            user.setRole(jo.getString("role"));
            user.setFirstname(jo.getString("firstname"));
            user.setLastname(jo.getString("lastname"));
            user.setPhone(jo.getDouble("phone"));
            user.setEmail(jo.getString("email"));
            user.setAgentFirstName(jo.getString("agent_firstname"));
            user.setAgentLastName(jo.getString("agent_lastname"));
            user.setAgentPhone(jo.getDouble("agent_phone"));


            List<Property> properties = new ArrayList<Property>();
            Property temp = new Property();

            for (int i = 0; i < jo.getJSONArray("properties").length(); i++) {

                properties.add(temp.toJava(jo.getJSONArray("properties").get(i).toString()));
            }

            user.setProperties(properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return user;
    }

    //metodo per la conversione di un oggetto java a JSON

    public JSONObject toJSON() {

        JSONArray ja = new JSONArray();

        for (Property temp : this.getProperties()) {
            ja.put(temp.toJSON());
        }

        JSONObject jo = new JSONObject();
        try {
            jo.put("id", this.getId());
            jo.put("login", this.getLogin());
            jo.put("password", this.getPassword());
            jo.put("role", this.getRole());
            jo.put("firstname", this.getFirstname());
            jo.put("lastname", this.getLastname());
            jo.put("phone", this.getPhone());
            jo.put("email", this.getEmail());
            jo.put("agent_firstname", this.getAgentFirstName());
            jo.put("agent_lastname", this.getAgentLastName());
            jo.put("agent_phone", this.getAgentPhone());
            jo.put("properties", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jo;
    }

    //metodo per l'aggiunta di un preferito

    //metodo per l'eliminazione di un preferito
}
