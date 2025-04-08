DELETE FROM user_roles;
DELETE FROM roles;
DELETE FROM users;

INSERT INTO roles (id, name) VALUES
                                 (1, 'ROLE_ADMIN'),
                                 (2, 'ROLE_USER');

INSERT INTO users (id, username, name, age, password) VALUES
                                                          (1, 'admin', 'Admin', 30, '$2a$12$RqmeVxuRZVlCw54hVhY0a.7.o5FO48YDddEldOk9OzYZrNe/Ui40S'),
                                                          (2, 'user', 'User', 25, '$2a$12$MlS4NVCRquZPkBdCUg2ZzOatOHYdGAzTFnqQzk3Ron2tI8KGQ9lJS');

INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),
                                              (1, 2),
                                              (2, 2);