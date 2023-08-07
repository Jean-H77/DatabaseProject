use comp440;

CREATE TABLE IF NOT EXISTS users(
                                    USERNAME VARCHAR(16) PRIMARY KEY NOT NULL,
                                    PASSWORD VARCHAR(20) NOT NULL,
                                    FIRST_NAME VARCHAR(255) NOT NULL,
                                    LAST_NAME VARCHAR(255) NOT NULL,
                                    EMAIL VARCHAR(255) NOT NULL,
                                    UNIQUE(EMAIL)
);

CREATE TABLE IF NOT EXISTS items (
                                     ID INT AUTO_INCREMENT PRIMARY KEY,
                                     TITLE VARCHAR(100) NOT NULL,
                                     DESCRIPTION TEXT NOT NULL,
                                     CATEGORY VARCHAR(50) NOT NULL,
                                     PRICE DOUBLE NOT NULL,
                                     USERNAME VARCHAR(50) NOT NULL,
                                     POSTED_TIMESTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);