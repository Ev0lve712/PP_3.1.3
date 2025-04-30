package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static Role fromString(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    private void setName(String name) {
        this.name = name;
    }
}