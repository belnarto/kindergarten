CREATE TABLE PUBLIC.CHILD_ENTITY_CONTACT_PHONES (
                                                    CHILD_ENTITY_ID BIGINT NOT NULL,
                                                    CONTACT_PHONES CHARACTER VARYING(255),
                                                    CONSTRAINT FKHCR6T72PVLDUA0DKXDWCC9NR1 PRIMARY KEY (CHILD_ENTITY_ID),
                                                    CONSTRAINT FKHCR6T72PVLDUA0DKXDWCC9NR2 FOREIGN KEY (CHILD_ENTITY_ID) REFERENCES PUBLIC.CHILD_ENTITY(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);