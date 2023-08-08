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
                                     PRIMARY KEY (CATEGORY_ID)
);

INSERT INTO categories (CATEGORY_NAME) VALUES ('apparel'), ('appliances'), ('electronics');

CREATE TABLE IF NOT EXISTS items (
									 ITEM_ID INT NOT NULL AUTO_INCREMENT,
									 TITLE varchar(100) NOT NULL,
									 DESCRIPTION TEXT NOT NULL,
									 PRICE DOUBLE NOT NULL,
									 USERNAME varchar(50) NOT NULL,
									 POSTED_TIMESTAMP timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
									 PRIMARY KEY (ITEM_ID)
);

CREATE TABLE IF NOT EXISTS item_categories (
                                     ITEM_ID INT NOT NULL,
                                     CATEGORY_ID INT NOT NULL,
                                     PRIMARY KEY (ITEM_ID, CATEGORY_ID),
                                     FOREIGN KEY (ITEM_ID) REFERENCES items (ITEM_ID),
                                     FOREIGN KEY (CATEGORY_ID) REFERENCES categories (CATEGORY_ID)
);