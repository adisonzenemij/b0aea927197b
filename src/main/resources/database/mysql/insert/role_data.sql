INSERT INTO role_data (id_register, fd_name)
VALUES
    (1, 'Administrador'),
    (2, 'Comprador')
ON DUPLICATE KEY UPDATE
    id_register = id_register;
