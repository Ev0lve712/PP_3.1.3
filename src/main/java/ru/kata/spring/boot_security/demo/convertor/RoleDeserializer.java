package ru.kata.spring.boot_security.demo.convertor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.io.IOException;

public class RoleDeserializer extends StdDeserializer<Role> {

    private final RoleDao roleDao;

    public RoleDeserializer(RoleDao roleDao) {
        super(Role.class);
        this.roleDao = roleDao;
    }

    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String roleName = p.getText();
        return roleDao.findByName(roleName);
    }
}
