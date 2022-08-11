CREATE TABLE PUBLIC.USER_ENTITY (
                                    ID BIGINT NOT NULL,
                                    ACCOUNT_NUMBER CHARACTER VARYING(255),
                                    ADDRESS CHARACTER VARYING(255),
                                    PASSWORD CHARACTER VARYING(255),
                                    "ROLE" CHARACTER VARYING(255),
                                    USERNAME CHARACTER VARYING(255),
                                    CONSTRAINT CONSTRAINT_F PRIMARY KEY (ID),
                                    CONSTRAINT UK_2JSK4EAKD0RMVYBO409WGWXUW UNIQUE (USERNAME)
);