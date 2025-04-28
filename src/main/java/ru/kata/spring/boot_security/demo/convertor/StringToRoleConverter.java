package ru.kata.spring.boot_security.demo.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    private final RoleDao roleRepository;

    public StringToRoleConverter(RoleDao roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role convert(String source) {
        return roleRepository.findByName(source);
    }
}