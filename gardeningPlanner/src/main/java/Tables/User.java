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

    @Column(length=64, nullable = false, unique = false)
    private String email_address;

    @Column(length=256, nullable = false, unique = false)
    private String password_hash;

    @Column(length=64, nullable = false, unique = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<UserPlant> userPlants;
}
