package com.sdpteam13.barbender;

import java.util.ArrayList;

public class Order {
    int price = 0;
    ArrayList<String> componenets = new ArrayList<>();
    String spirit;
    String mixer;
    String name;

    public void addComponent(String component)
    {
        this.componenets.add(component);
    }
    public ArrayList<String> getComponents()
    {
        return this.componenets;
    }

    public String getMixer() {
        return mixer;
    }

    public void setMixer(String mixer) {
        this.mixer = mixer;
    }

    public String getSpirit() {

        return spirit;
    }

    public void setSpirit(String spirit) {
        this.spirit = spirit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price)
    {
        this.price += price;
    }
}
