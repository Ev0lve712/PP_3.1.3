package ru.kata.spring.boot_security.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.kata.spring.boot_security.demo.convertor.RoleDeserializer;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;


public class UserDto {
    private Long id;
    private String username;
    private String name;
    private Integer age;
    private String password;
    @JsonDeserialize(contentUsing = RoleDeserializer.class)
    private Collection<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getAuthorities() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
