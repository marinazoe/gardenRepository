package Tables;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 64, nullable = false, unique = false)
    private String email_address;

    @Column(length = 256, nullable = false, unique = false)
    private String password_hash;

    @Column(length = 64, nullable = false, unique = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<UserPlant> userPlants;

    // ------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email_address;
    }

    public String getPasswordHash() {
        return password_hash; // vorläufig
    }

    public List<UserPlant> getUserPlants() {
        return userPlants;
    }

    // ------------------------------------------------------------
    // Setters
    // ------------------------------------------------------------
    public void setId(int _id) {
        id = _id;
    }

    public void setEmail(String _email) {
        email_address = _email;
    }

    public void setPasswordHash(String _password) {
        password_hash = _password; // vorläufig
    }

    public void setUserPlants(List<UserPlant> _userPlants) {
        userPlants = _userPlants;
    }
}
