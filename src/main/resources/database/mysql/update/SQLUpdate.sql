UPDATE role_data
SET fd_name = CASE id_register
    WHEN 1 THEN 'Administrador'
    WHEN 2 THEN 'Comprador'
END
WHERE id_register IN (1, 2);

UPDATE user_data
SET
    fd_email = CASE id_register
        WHEN 1 THEN 'adison@gmail.com'
        WHEN 2 THEN 'miguel@gmail.com'
        WHEN 3 THEN 'wendy@gmail.com'
        WHEN 4 THEN 'diana@gmail.com'
        WHEN 5 THEN 'kyara@gmail.com'
    END,
    fd_login = CASE id_register
        WHEN 1 THEN 'adison'
        WHEN 2 THEN 'miguel'
        WHEN 3 THEN 'wendy'
        WHEN 4 THEN 'diana'
        WHEN 5 THEN 'kyara'
    END,
    fd_passd = CASE id_register
        WHEN 1 THEN '$2y$12$lxymAy7CK2nNGYytx6Qg6.DOfFLhCwNlOkYS.U40as8vIoupJH1tK'
        WHEN 2 THEN '$2y$12$YmmiX.o340pReq9vruEk7.EfRc/XiuEh1dRdtQL6J2aCpPkdciIJG'
        WHEN 3 THEN '$2y$12$obAufDW7vBd5wPdo4./6SuVmU6GSD9Hn.Z7VUQ4LGJgQri5evAJZe'
        WHEN 4 THEN '$2y$12$9ZIAz2AGac7dv3HNzqhtdeJqmNh5XbgGpRS0.MgP2c1Ofi2bd.Ki.'
        WHEN 5 THEN '$2y$12$WJnr8UgCBSCjLW5Ymm42iuUQ6/i8KWpTjoTrAs1j8kZ9vLvvK9IdC'
    END,
    fd_name = CASE id_register
        WHEN 1 THEN 'Adison Jesus'
        WHEN 2 THEN 'Miguel Angel'
        WHEN 3 THEN 'Wendy Yulany'
        WHEN 4 THEN 'Diana Iveth'
        WHEN 5 THEN 'Kyara Andrea'
    END,
    fd_srnm = CASE id_register
        WHEN 1 THEN 'Jimenez Beltran'
        WHEN 2 THEN 'Gracia Serrano'
        WHEN 3 THEN 'Ayala Hernandez'
        WHEN 4 THEN 'Gomez Gomez'
        WHEN 5 THEN 'Sierra Reyes'
    END,
    id_role_data = CASE id_register
        WHEN 1 THEN 1
        WHEN 2 THEN 2
        WHEN 3 THEN 1
        WHEN 4 THEN 2
        WHEN 5 THEN 1
    END
WHERE id_register IN (1, 2, 3, 4, 5);

UPDATE image_ext
SET fd_value = CASE id_register
    WHEN 1 THEN 'png'
    WHEN 2 THEN 'jpg'
    WHEN 3 THEN 'jpeg'
    WHEN 4 THEN 'webp'
    WHEN 5 THEN 'svg'
    WHEN 6 THEN 'gif'
    WHEN 7 THEN 'ico'
    WHEN 8 THEN 'avif'
END
WHERE id_register IN (1, 2, 3, 4, 5, 6, 7, 8);
