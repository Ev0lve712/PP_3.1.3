package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> allUsers();
    User add(UserDto userDto); // добавить новый метод
    void delete(Long id);
    User edit(UserDto userDto);
    User getById(Long id);
    User findByUsername(String username);
}
