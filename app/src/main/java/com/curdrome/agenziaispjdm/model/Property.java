package com.curdrome.agenziaispjdm.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Alex on 10/09/2015.
 */
public class Property implements Serializable {

    private Integer id;

    private String province;

    private String city;

    private String zone;

    private Integer rooms;

    private Integer bath;

    private String type;

    private String subtype;

    private Integer price;

    private Integer sqm;

    private String description;

    private String foto_link;

    //metodo per la conversione di un oggetto JSON a java
    public final static Property toJava(String json) {
        Gson gson = new Gson();

        Property property = gson.fromJson(json, Property.class);

        return property;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getBath() {
        return bath;
    }

    public void setBath(Integer bath) {
        this.bath = bath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSqm() {
        return sqm;
    }

    public void setSqm(Integer sqm) {
        this.sqm = sqm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoto_link() {
        return foto_link;
    }

    public void setFoto_link(String foto_link) {
        this.foto_link = foto_link;
    }

    //metodo per la conversione di un oggetto java a JSON
    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        try {
            jo.put("id", this.getId());
            jo.put("province", this.getProvince());
            jo.put("city", this.getCity());
            jo.put("zone", this.getZone());
            jo.put("rooms", this.getBath());
            jo.put("type", this.getType());
            jo.put("subtype", this.getSubtype());
            jo.put("prince", this.getPrice());
            jo.put("description", this.getDescription());
            jo.put("foto_link", this.getFoto_link());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jo;

    }

}
