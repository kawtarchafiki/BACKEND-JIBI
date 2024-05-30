package web.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_admin;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "email",unique = true)
    private String email;



    @Column(name="password",nullable = false)
    private String password;
    @Column(name="role")
    private String role = "ROLE_ADMIN";
    public Admin(String username, String password,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
