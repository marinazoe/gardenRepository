package com.example.gardeningPlanner.Tables;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 32, nullable = true, unique = false)
    private String nickname;

    @Column(length = 2048, nullable = true, unique = false)
    private String notes;

    @Column(nullable = false, unique = false)
    private boolean notifications_enabled;

    @Column(nullable = true, unique = false)
    private LocalDate added_date;
    
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "userAccount_id")
    private UserAccount userAccount;

    // ------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------
    protected UserPlant() {
    }

    public UserPlant(Plant plant, UserAccount userAccount) {
        this.notifications_enabled = false;
        this.plant = plant;
        this.userAccount = userAccount;

    }

    // ------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNotes() {
        return notes;
    }

    public boolean getNotifications_enabled() {
        return notifications_enabled;
    }

    public LocalDate getAdded_date() {
        return added_date;
    }

    public Plant getPlant() {
        return plant;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    // ------------------------------------------------------------
    // Setters
    // ------------------------------------------------------------
    public void setId(int _id) {
        id = _id;
    }

    public void setNickname(String _nickname) {
        nickname = _nickname;
    }

    public void setNotes(String _notes) {
        notes = _notes;
    }

    public void setNotifications_enabled(boolean _notifications_enabled) {
        notifications_enabled = _notifications_enabled;
    }

    public void setAdded_date(LocalDate _added_date) {
        added_date = _added_date;
    }

    public void setPlant(Plant _plant) {
        plant = _plant;
    }

    public void setUserAccount(UserAccount _userAccount) {
        userAccount = _userAccount;
    }
}
