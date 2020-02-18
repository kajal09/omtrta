--Creates all the tables for the OMTRTA database


-- Drop
DROP TABLE IF EXISTS bccb;
DROP TABLE IF EXISTS ccb;
DROP TABLE IF EXISTS tob;
DROP TABLE IF EXISTS bridgeAttachment;
DROP TABLE IF EXISTS ebean;
DROP TABLE IF EXISTS Emails;
DROP TABLE IF EXISTS folder;
DROP TABLE IF EXISTS Attachment;



-- Create
CREATE TABLE Attachment (
    aid serial NOT NULL,
    file mediumblob NULL,
    name varchar(100) NOT NULL,
    embedded bool NOT NULL,
    CONSTRAINT Attachment_pk PRIMARY KEY (aid)
);

CREATE TABLE folder (
    fid serial NOT NULL,
    name varchar(100) NOT NULL,
    CONSTRAINT folder_pk PRIMARY KEY (fid)
);

CREATE TABLE Emails (
    eid serial NOT NULL,
    email varchar(254) NULL,
    CONSTRAINT Emails_pk PRIMARY KEY (eid)
);

CREATE TABLE ebean (
    ebid serial NOT NULL,
    `from` varchar(254) NOT NULL,
    subject varchar(200) NULL,
    textMessage mediumtext NULL,
    htmlMessage mediumtext NOT NULL,
    fid BIGINT UNSIGNED NOT NULL,
    date timestamp NOT NULL,
    CONSTRAINT ebean_pk PRIMARY KEY (ebid),
    FOREIGN KEY (fid) REFERENCES folder (fid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bridgeAttachment (
    aid BIGINT UNSIGNED NOT NULL,
    ebid BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (aid) REFERENCES Attachment (aid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ebid) REFERENCES ebean (ebid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bccb (
    eid BIGINT UNSIGNED NOT NULL,
    ebid BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (eid) REFERENCES Emails (eid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY bcc_ebean (ebid) REFERENCES ebean (ebid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ccb (
    eid BIGINT UNSIGNED NOT NULL,
    ebid BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (eid) REFERENCES Emails (eid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ebid) REFERENCES ebean (ebid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tob (
    eid BIGINT UNSIGNED NOT NULL,
    ebid BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (eid) REFERENCES Emails (eid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ebid) REFERENCES ebean(ebid) ON DELETE CASCADE ON UPDATE CASCADE
    
);

INSERT INTO folder (name)
VALUES ('Inbox');

INSERT INTO folder (name)
VALUES ('Sent');