DROP SEQUENCE SEQ_ID_RESERVATION;
DROP SEQUENCE SEQ_ID_PRET;
DROP SEQUENCE SEQ_ID_LIVRE;
DROP SEQUENCE SEQ_ID_MEMBRE;

DROP TABLE reservation CASCADE CONSTRAINTS PURGE;
DROP TABLE pret        CASCADE CONSTRAINTS PURGE;
DROP TABLE livre       CASCADE CONSTRAINTS PURGE;
DROP TABLE membre      CASCADE CONSTRAINTS PURGE;

CREATE SEQUENCE SEQ_ID_MEMBRE      START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ID_LIVRE       START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ID_PRET        START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ID_RESERVATION START WITH 1 INCREMENT BY 1;

CREATE TABLE membre (idMembre   NUMBER(3)    CHECK (idMembre > 0),
                     nom        VARCHAR(100) NOT NULL,
                     telephone  NUMBER(10),
                     limitePret NUMBER(2)    CHECK (limitePret > 0 AND limitePret <= 10),
                     nbPret     NUMBER(2)    DEFAULT 0 CHECK (nbpret >= 0),
                     CONSTRAINT cleMembre    PRIMARY KEY (idMembre),
                     CONSTRAINT limiteNbPret CHECK (nbPret <= limitePret));

CREATE TABLE livre (idLivre         NUMBER(3)    CHECK (idLivre > 0),
                    titre           VARCHAR(100) NOT NULL,
                    auteur          VARCHAR(100) NOT NULL,
                    dateAcquisition TIMESTAMP    NOT NULL,
                    CONSTRAINT      cleLivre     PRIMARY KEY (idLivre));

CREATE TABLE pret (idPret     NUMBER(3)  CHECK (idPret > 0),
                   idMembre   NUMBER(3)  CHECK (idMembre > 0),
                   idLivre    NUMBER(3)  CHECK (idLivre > 0),
                   datePret   TIMESTAMP,
                   dateRetour TIMESTAMP,
                   CONSTRAINT clePrimairePret PRIMARY KEY (idPret),
                   CONSTRAINT refPretMembre   FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE,
                   CONSTRAINT refPretLivre    FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)   ON DELETE CASCADE);

CREATE TABLE reservation (idReservation   NUMBER(3)  CHECK (idReservation > 0),
                          idMembre        NUMBER(3)  CHECK (idMembre > 0),
                          idLivre         NUMBER(3)  CHECK (idLivre > 0),
                          dateReservation TIMESTAMP,
                          CONSTRAINT      clePrimaireReservation  PRIMARY KEY (idReservation),
                          CONSTRAINT      cleEtrangereReservation UNIQUE (idMembre, idLivre),
                          CONSTRAINT      refReservationMembre    FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE,
                          CONSTRAINT      refReservationLivre     FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)   ON DELETE CASCADE);
