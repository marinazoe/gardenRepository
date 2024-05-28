package com.example.gardeningPlanner.data;

import java.util.Arrays;
import java.util.List;

import com.example.gardeningPlanner.Tables.Plant;

public class InitialPlants {

    public static List<Plant> getInitialPlants() {

        Plant monstera = new Plant("Monstera deliciosa", 
                        4, 
                        2,
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonne", 
                        18, //18-27 
                        60);

        Plant calathea = new Plant("Calathea", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Halbschatten - Schatten", 
                        18, //18-24 
                        70);

        Plant friedenslilie = new Plant("Friedenslilie (Spathiphyllum)", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonne", 
                        18, //18-24 
                        50);

        Plant gruenlilie = new Plant("Grünlilie", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Heller - halbschattiger Standort, keine direkte Sonne", 
                        18, //18-24 
                        50);

        Plant alocasia = new Plant("Alocasia Amazonica", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonneneinstrahlung", 
                        20, //20-25
                        60);

        Plant ufopflanze = new Plant("Ufopflanze (Pilea peperomioides)", 
                        4, 
                        3, 
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonne", 
                        18, //18-24 
                        50);

        Plant geigenfeige = new Plant("Geigen-Feige (Ficus lyrata)", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Heller Standort, etwas direkte Sonne", 
                        18, //18-24 
                        50);

        Plant gummibaum = new Plant("Gummibaum (Ficus elastica)", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Heller Standort, etwas direkte Sonne",
                        18, //18-24 
                        50);
        
        Plant efeutute = new Plant("Efeutute (Epipremnum aureum)", 
                        4, 2,
                        2,
                        1,
                        "Heller Standort, keine direkte Sonne", 
                        18, //18-24 
                        50);

        Plant yucca = new Plant("Yucca Palme", 
                        2, 
                        1, 
                        1,
                        0, 
                        "heller Standort, direkte Sonne", 
                        18, //18-24 
                        40);
        Plant aloeVera = new Plant("Aloe Vera", 
                        2, 
                        1, 
                        1, 
                        0, 
                        "Heller Standort, direkte Sonne", 
                        18, //18-24
                        40);
        Plant kaktus = new Plant("Kaktus", 
                        1, 
                        0, 
                        1, 
                        0, 
                        "Heller Standort, direkte Sonne", 
                        20, //20-30 
                        30);

        Plant oregano = new Plant("Oregano", 
                        2, 
                        1, 
                        1, 
                        0, 
                        "Heller Standort, direkt Sonne", 
                        18, //18-24 
                        40);

        Plant thymian = new Plant("Thymian", 
                        2, 
                        1, 
                        1, 
                        0, 
                        "Heller Standort, direkte Sonne", 
                        18, // 18-24 
                        40);

        Plant basilikum = new Plant("Basilikum", 
                        3, 
                        2, 
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonne", 
                        20, //20-25
                        60);

        Plant rosmarin = new Plant("Rosmarin", 
                        2, 
                        1, 
                        1, 
                        0, 
                        "Heller Standort, direkte Sonne",
                        15-25, //15-25
                        40);

        Plant minze = new Plant("Minze", 
                        4, 
                        2, 
                        2, 
                        1, 
                        "Halbschatten - Sonne",
                        18, //18-24
                        60);

        return Arrays.asList(monstera, calathea, friedenslilie, gruenlilie, 
                            alocasia, ufopflanze,geigenfeige, gummibaum, 
                            efeutute, yucca, aloeVera, kaktus, oregano,
                            thymian, basilikum, rosmarin, minze);           
    }
    
}
