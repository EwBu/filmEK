package pl.kowalska.filmek.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String userName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 1)
    private char gender;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
         /*   inverseJoinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"))*/
    private Collection<Role> roles;

    public User() {
    }

    public User(String userName, String email, String password, char gender, Collection<Role> roles) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public char getGender() {
        return gender;
    }

    public Collection<Role> getRoles() {
        return roles;
    }
}
