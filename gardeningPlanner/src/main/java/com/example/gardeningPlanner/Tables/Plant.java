package com.example.gardeningPlanner.Tables;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 64, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = false)
    private int water_summer;

    @Column(nullable = false, unique = false)
    private int water_winter;

    @Column(nullable = false, unique = false)
    private int fertilize_summer;

    @Column(nullable = false, unique = false)
    private int fertilize_winter;

    @Column(length = 64, nullable = false, unique = false)
    private String spot;

    @Column(nullable = false, unique = false)
    private float temperature;

    @Column(nullable = false, unique = false)
    private int humidity;

    @OneToMany(mappedBy = "plant")
    private List<UserPlant> userPlants;

    // ------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------
    protected Plant() {

    }

    public Plant(String name, int water_summer, int water_winter, int fertilize_summer, int fertilize_winter, String spot, float temperature, int humidity){
        this.name = name;
        this.water_summer = water_summer;
        this.water_winter = water_winter;
        this.fertilize_summer = fertilize_summer;
        this.fertilize_winter = fertilize_winter;
        this.spot = spot;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // ------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWater_summer() {
        return water_summer;
    }

    public int getWater_winter() {
        return water_winter;
    }

    public int getFertilize_summer() {
        return fertilize_summer;
    }

    public int getFertilize_winter() {
        return fertilize_winter;
    }

    public String getSpot() {
        return spot;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    // ------------------------------------------------------------
    // Setters
    // ------------------------------------------------------------
    public void setId(int _id) {
        id = _id;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setWater_summer(int _water_summer) {
        water_summer = _water_summer;
    }

    public void setWater_winter(int _water_winter) {
        water_winter = _water_winter;
    }

    public void setFertilize_summer(int _fertilize_summer) {
        fertilize_summer = _fertilize_summer;
    }

    public void setFertilize_winter(int _fertilize_winter) {
        fertilize_winter = _fertilize_winter;
    }

    public void setSpot(String _spot) {
        spot = _spot;
    }

    public void setTemperature(float _temperature) {
        temperature = _temperature;
    }
    
    public void setHumidity(int _humidity) {
        humidity = _humidity;
    }

}
