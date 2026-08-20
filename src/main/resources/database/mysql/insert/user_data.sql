INSERT INTO user_data (
    id_register,
    fd_email,
    fd_login,
    fd_passd,
    fd_name,
    fd_srnm,
    id_role_data
)
VALUES
    (1, 'adison@gmail.com', 'adison', '$2y$12$lxymAy7CK2nNGYytx6Qg6.DOfFLhCwNlOkYS.U40as8vIoupJH1tK', 'Adison Jesus', 'Jimenez Beltran', 1),
    (2, 'miguel@gmail.com', 'miguel', '$2y$12$YmmiX.o340pReq9vruEk7.EfRc/XiuEh1dRdtQL6J2aCpPkdciIJG', 'Miguel Angel', 'Gracia Serrano', 2),
    (3, 'wendy@gmail.com', 'wendy', '$2y$12$obAufDW7vBd5wPdo4./6SuVmU6GSD9Hn.Z7VUQ4LGJgQri5evAJZe', 'Wendy Yulany', 'Ayala Hernandez', 1),
    (4, 'diana@gmail.com', 'diana', '$2y$12$9ZIAz2AGac7dv3HNzqhtdeJqmNh5XbgGpRS0.MgP2c1Ofi2bd.Ki.', 'Diana Iveth', 'Gomez Gomez', 2),
    (5, 'kyara@gmail.com', 'kyara', '$2y$12$WJnr8UgCBSCjLW5Ymm42iuUQ6/i8KWpTjoTrAs1j8kZ9vLvvK9IdC', 'Kyara Andrea', 'Sierra Reyes', 1)
ON DUPLICATE KEY UPDATE
    id_register = id_register;
