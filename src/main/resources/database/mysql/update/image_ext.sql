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
