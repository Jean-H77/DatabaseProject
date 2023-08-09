use comp440;

CREATE TABLE IF NOT EXISTS users(
    USERNAME VARCHAR(16) PRIMARY KEY NOT NULL,
    PASSWORD VARCHAR(20) NOT NULL,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    UNIQUE(EMAIL)
);

CREATE TABLE IF NOT EXISTS categories (
    CATEGORY_ID int NOT NULL AUTO_INCREMENT,
    CATEGORY_NAME varchar(50) NOT NULL,
    PRIMARY KEY (CATEGORY_ID, CATEGORY_NAME)
);

CREATE TABLE IF NOT EXISTS items (
    ITEM_ID INT NOT NULL AUTO_INCREMENT,
    TITLE varchar(100) NOT NULL,
    DESCRIPTION TEXT NOT NULL,
    PRICE DOUBLE NOT NULL,
    USERNAME varchar(50) NOT NULL,
    POSTED_TIMESTAMP timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ITEM_ID)
);

-- maker table
CREATE TABLE IF NOT EXISTS categoryMakers (
                                      MAKER_ID INT NOT NULL AUTO_INCREMENT,
                                      MAKER_NAME VARCHAR(100) NOT NULL,
                                      CATEGORY_ID INT NOT NULL,
                                      PRIMARY KEY (MAKER_ID),
                                      FOREIGN KEY (CATEGORY_ID) REFERENCES categories (CATEGORY_ID)
);


-- types table
CREATE TABLE IF NOT EXISTS categoryTypes (
    TYPE_ID INT NOT NULL AUTO_INCREMENT,
    TYPE_NAME VARCHAR(50) NOT NULL,
    CATEGORY_ID INT NOT NULL,
    PRIMARY KEY (TYPE_ID),
    FOREIGN KEY (CATEGORY_ID) REFERENCES categories (CATEGORY_ID)
);

-- item_categories table
CREATE TABLE IF NOT EXISTS item_categories (
    ITEM_ID INT NOT NULL,
    TYPE_ID INT NOT NULL,
    CAT_MAKER_ID INT NOT NULL,
    CAT_TYPE_ID INT NOT NULL,
    UNIQUE (ITEM_ID),
    FOREIGN KEY (ITEM_ID) REFERENCES items (ITEM_ID),
    FOREIGN KEY (TYPE_ID) REFERENCES categories (CATEGORY_ID),
    FOREIGN KEY (CAT_MAKER_ID) REFERENCES categoryMakers(MAKER_ID),
    FOREIGN KEY (CAT_TYPE_ID) REFERENCES categoryTypes(TYPE_ID)
);

-- review table
CREATE TABLE IF NOT EXISTS reviews (
                                       ID INT NOT NULL AUTO_INCREMENT,
                                       ITEM_ID INT NOT NULL,
                                       USERNAME varchar(50) NOT NULL,
                                       POSTED_TIMESTAMP timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (ID),
                                       FOREIGN KEY (ITEM_ID) REFERENCES items(ITEM_ID),
                                       FOREIGN KEY (USERNAME) REFERENCES users(USERNAME)
);

INSERT INTO categories (CATEGORY_NAME) VALUES ('Apparel'), ('Appliances'), ('Electronics'), ('Footwear'), ('Jewelry');


INSERT INTO categoryMakers (MAKER_NAME, CATEGORY_ID) VALUES
('None',1), ('Nike', 1), ('LVMH', 1), ('Adidas', 1), ('PUMA', 1), ('Levi', 1), ('Gap', 1), ('J. Crew', 1), ('Guess', 1), ('True Religion', 1), ('Under Armour', 1),
('None',2), ('LG', 2), ('Panasonic', 2), ('Haier', 2), ('Gree', 2), ('Whirlpool', 2), ('Bosh-Siemens', 2), ('Electrolux AB', 2),
('None',3), ('Apple', 3), ('Samsung', 3), ('Microsoft', 3), ('Google', 3), ('Vivo', 3), ('Huawei', 3), ('Motorola', 3), ('Nokia', 3), ('LG', 3),
('None',4), ('Nike', 4), ('Adidas', 4), ('Sketchers', 4), ('New Balance', 4), ('Under Armour', 4), ('Asics', 4), ('Vans', 4),
('None',5), ('Mejuri', 5), ('BaubleBar', 5), ('Blue Nile', 5), ('Brilliant Earth', 5), ('Vrai', 5), ('Net-A-Porter', 5), ('Gorjana', 5)
;

INSERT INTO categoryTypes (TYPE_NAME, CATEGORY_ID) VALUES
('None',1), ('Shirt', 1), ('Pants', 1), ('Jacket', 1),
('None',2), ('Fridge', 2), ('Blender', 2), ('Washing machine', 2),
('None',3), ('Phone', 3), ('TV', 3), ('Computer', 3),
('None',4), ('Running', 4), ('Tennis', 4), ('Sandals', 4),
('None',5), ('Earring', 5), ('Bracelet', 5), ('Necklace', 5)
