CREATE TABLE PUBLIC.CHILD_ENTITY (
                                     ID BIGINT NOT NULL,
                                     AGE INTEGER NOT NULL,
                                     BIRTHDATE DATE,
                                     CATEGORY CHARACTER VARYING(255),
                                     FIRST_NAME CHARACTER VARYING(255),
                                     LAST_NAME CHARACTER VARYING(255),
                                     SEX CHARACTER VARYING(255),
                                     UPDATED_AT TIMESTAMP,
                                     USER_ID BIGINT NOT NULL,
                                     CONSTRAINT CONSTRAINT_4 PRIMARY KEY (ID),
                                     CONSTRAINT FK1PLYAPQQUBN1P586PLI9DRUIT FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USER_ENTITY(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);