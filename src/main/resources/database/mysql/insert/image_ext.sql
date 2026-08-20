INSERT INTO image_ext (id_register, fd_value)
VALUES
    (1, 'png'),
    (2, 'jpg'),
    (3, 'jpeg'),
    (4, 'webp'),
    (5, 'svg'),
    (6, 'gif'),
    (7, 'ico'),
    (8, 'avif')
ON DUPLICATE KEY UPDATE
    id_register = id_register;
