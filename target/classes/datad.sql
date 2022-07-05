
INSERT INTO
    utente
VALUES
    (99999, 'aprilia', 'admin', '2016-06-22 19:10:25-07' , 'r3.cate@gmail.com','via','admin','123');

INSERT INTO
    credentials
VALUES
    (99999, '$2a$12$u05KxnLT1TDgBP.8xZMzDOY7e86e/FwKEYEI9ChcikhgoSMD7Fd0q', 'LOCAL', 'ADMIN', 'admin', 99999);

INSERT INTO
    utente
VALUES
    (999991, 'aprilia', 'prova', '2016-06-22 19:10:25-07' , 'email','via','user','123');

INSERT INTO
    credentials
VALUES
    (999991, '$2a$12$g1zyFaYti3uNtoW2NAJE4O6QJbHFOS2J0CbRnb6d/4toWCV9ww0tO', 'LOCAL', 'DEFAULT', 'user', 999991);

/* nazioni */
INSERT INTO nazione (id, iso, nome, numcode, phonecode)
    VALUES
        (1, 'AF', 'AFGHANISTAN', 4, 93),
        (2, 'AL', 'ALBANIA', 8, 355),
        (3, 'DZ', 'ALGERIA', 12, 213),
        (4, 'AR', 'ARGENTINA', 32, 54),
        (5, 'AM', 'ARMENIA', 51, 374),
        (6, 'AU', 'AUSTRALIA', 36, 61),
        (7, 'AT', 'AUSTRIA', 40, 43),
        (8, 'BE', 'BELGIUM', 56, 32),
        (9, 'BR', 'BRAZIL', 76, 55),
        (10, 'FR', 'FRANCE', 250, 33),
        (80, 'DE', 'GERMANY', 276, 49),
        (83, 'GR', 'GREECE', 300, 30),
        (103, 'IE', 'IRELAND', 372, 353),
        (104, 'IL', 'ISRAEL', 376, 972),
        (105, 'IT', 'ITALY', 380, 39),
        (150, 'NL', 'NETHERLANDS', 528, 31),
        (160, 'NO', 'NORWAY', 578, 47),
        (172, 'PT', 'PORTUGAL', 620, 351),
        (205, 'SE', 'SWEDEN', 752, 46),
        (206, 'CH', 'SWITZERLAND', 756, 41),
        (225, 'GB', 'UNITED KINGDOM', 826, 44),
        (226, 'US', 'UNITED STATES', 840, 1);

INSERT INTO tipologia VALUES
                          (1, 'Buffet freddo'),
                          (2, 'Buffet di dolci'),
                          (3, 'Buffet salato'),
                          (4, 'Colazione a buffet'),
                          (5, 'Compleanno'),
                          (6, 'Matrimonio'),
                          (7, 'Laurea'),
                          (8, 'Brunch');

