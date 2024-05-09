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

    @Column(length = 64, nullable = false, unique = false)
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
}
