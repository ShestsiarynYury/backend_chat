package app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(
    name = "users"
)
@Setter
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Long id;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "password")
    private String password;
    @Lob
    @Column(name = "foto")
    private String foto;
}
