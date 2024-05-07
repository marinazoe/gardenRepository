package Tables;

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

    @Column(length=32, nullable = true, unique = false)
    private String nickname;

    @Column(length=2048, nullable = true, unique = false)
    private String notes;

    @Column(nullable = false, unique = false)
    private boolean notifications_enabled;

    @ManyToOne
    @JoinColumn(name="plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
