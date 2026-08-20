UPDATE role_data
SET fd_name = CASE id_register
    WHEN 1 THEN 'Administrador'
    WHEN 2 THEN 'Comprador'
END
WHERE id_register IN (1, 2);
