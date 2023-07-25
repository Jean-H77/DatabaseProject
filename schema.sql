use comp440;

CREATE TABLE IF NOT EXISTS users(
                                    USERNAME VARCHAR(16) PRIMARY KEY NOT NULL,
                                    PASSWORD VARCHAR(20) NOT NULL,
                                    FIRST_NAME VARCHAR(255) NOT NULL,
                                    LAST_NAME VARCHAR(255) NOT NULL,
                                    EMAIL VARCHAR(255) NOT NULL,
                                    UNIQUE(EMAIL)
);