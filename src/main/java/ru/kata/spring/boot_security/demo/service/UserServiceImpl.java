package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        if (userDao == null) {
            throw new IllegalArgumentException("UserDao null");
        }
        this.userDao = userDao;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public List<User> allUsers() {
        return userDao.findAll();
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public User add(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getAuthorities());
        userDao.save(user);
        System.out.println("Roles userDto: " + userDto.getAuthorities());
        return user;
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public User edit(UserDto userDto) {
        User user = userDao.findByUsername(userDto.getUsername());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        if (userDto.getPassword() != null) {
            user.setPassword(encoder.encode(userDto.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }
        user.setRoles(userDto.getAuthorities());
        System.out.println("Roles userDto: " + userDto.getAuthorities());
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}