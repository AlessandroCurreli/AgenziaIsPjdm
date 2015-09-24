package com.curdrome.agenziaispjdm.model;

import android.content.Context;
import android.content.ContextWrapper;

import com.curdrome.agenziaispjdm.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
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
    private File directory;
    private File file;

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
        Gson gson = new Gson();

        User user = gson.fromJson(json, User.class);
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
    public void addBookmark(Property property) {
        properties.add(property);
    }

    //metodo per l'eliminazione di un preferito
    public void removeBookmark(Property property) {

        Iterator<Property> iterator = properties.iterator();

        while (iterator.hasNext()) {
            Property element = iterator.next();
            if (element.getId() == property.getId()) {
                iterator.remove();
            }
        }
    }

    public String loadData(Context context) {
        String login = "";
        try {
            ContextWrapper contextWrapper = new ContextWrapper(context);
            directory = contextWrapper.getDir(context.getString(R.string.file_dir), Context.MODE_PRIVATE);
            file = new File(directory, context.getString(R.string.file_name));
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                login = login + strLine;
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

    public void saveData(String login, String password, Context context) {
        try {
            ContextWrapper contextWrapper = new ContextWrapper(context);
            directory = contextWrapper.getDir(context.getString(R.string.file_dir), Context.MODE_PRIVATE);
            file = new File(directory, context.getString(R.string.file_name));
            FileOutputStream fos = new FileOutputStream(file);
            JSONObject jo = new JSONObject();
            jo.put("login", login);
            jo.put("password", password);
            fos.write(jo.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
