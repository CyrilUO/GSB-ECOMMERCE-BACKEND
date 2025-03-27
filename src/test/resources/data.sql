INSERT INTO users (user_name, user_surname, user_email, user_password, role_id)
VALUES ('John', 'Doe', 'john.doe@gsb.com', '$2a$10$0JZPRaJ3H9rLn0cpfmYW7u0sCZFShJEgot5PjuAESFVL2G9JdrK72', 1),
       ('Foo', 'Bar', 'foo.bar@gsb.com','$2a$10$s5YGEZksFgmGsqXSB7bs..ItE.J80nlmEYkFHv1H4q7JGJYkW99Wu', 2),
       ('Jean','Valjean','jean.valjean@gsb.com','$2a$10$rou1.5ewgt8Sbq19M5mtkOvrIc2n2HoKtIhpd6EFkuYByB.sm4mOm', 3);

INSERT INTO delivery_address (delivery_address_city, delivery_address_street, delivery_address_zip_code, delivery_address_country)
VALUES
    ('Paris', '56 Avenue des Champs-Elysées', 75008, 'France'),
    ('Marseille', '78 Boulevard de la Canebière', 13001, 'France'),
    ('Lyon', '123 Rue de la République', 69001, 'France'),
    ('Bordeaux', '45 Quai des Chartrons', 33000, 'France');

INSERT INTO roles (role_name) VALUES ('admin'), ('salesperson'), ('medical-employee');

INSERT INTO products (product_name, product_description, product_price, product_stock) VALUES
                                                                                           ('Paracetol', 'Antalgique léger', 2.50, 120),
                                                                                           ('Ibuprex', 'Anti-inflammatoire', 4.20, 85),
                                                                                           ('Tussilux', 'Sirop pour la toux', 3.80, 60),
                                                                                           ('Nocafor', 'Antidouleur puissant', 6.00, 30),
                                                                                           ('VitaZen', 'Complément vitamines B', 7.50, 200),
                                                                                           ('Energex', 'Boisson énergétique', 2.90, 150),
                                                                                           ('Dorsamine', 'Somnifère naturel', 5.20, 40),
                                                                                           ('Stresstop', 'Anti-stress léger', 4.50, 90),
                                                                                           ('Respibien', 'Spray nasal', 3.00, 75),
                                                                                           ('Fievrol', 'Réducteur de fièvre', 4.80, 110),
                                                                                           ('Digesto', 'Améliore la digestion', 3.60, 95),
                                                                                           ('Antimicrox', 'Antiseptique externe', 6.70, 50),
                                                                                           ('Panomix', 'Pommade anti-douleur', 4.00, 130),
                                                                                           ('Hydrogel+', 'Gel hydratant peau', 3.90, 100),
                                                                                           ('Cardioplus', 'Régulateur tension', 8.50, 20),
                                                                                           ('Zenacol', 'Relaxant musculaire', 5.70, 45),
                                                                                           ('Rhinitex', 'Anti-allergique nasal', 4.30, 70),
                                                                                           ('Magnesion', 'Supplément magnésium', 6.20, 140),
                                                                                           ('Cicatrix', 'Cicatrisant crème', 5.10, 60),
                                                                                           ('Protecal', 'Soutien immunitaire', 7.20, 80);


