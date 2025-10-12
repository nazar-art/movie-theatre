DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_auditorium;
DROP TABLE IF EXISTS t_event;
DROP TABLE IF EXISTS t_ticket;
DROP TABLE IF EXISTS t_aspectcounter;
DROP TABLE IF EXISTS t_useraccount;

----------------------
-- create t_user table
----------------------
CREATE TABLE t_user (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_user PRIMARY KEY,
      name      VARCHAR(60) NOT NULL,
      birthday  DATE,
      email     VARCHAR(60),
      password  VARCHAR(100),
      role      VARCHAR(300),
      enabled   SMALLINT(6)
);

----------------------------
-- create t_auditorium table
----------------------------
CREATE TABLE t_auditorium (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_auditorium PRIMARY KEY,
      name       VARCHAR(80) NOT NULL,
      seats      INT,
      vip        VARCHAR(80)
);

-----------------------
-- create t_event table
-----------------------
CREATE TABLE t_event (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_event PRIMARY KEY,
      name          VARCHAR(100) NOT NULL,
      airDate       TIMESTAMP,
      ticketPrice   DECIMAL(8,2),
      rating        VARCHAR(10),
      auditorium_id INT CONSTRAINT fk_event_auditorium
      				REFERENCES t_auditorium ON UPDATE RESTRICT ON DELETE RESTRICT
);

------------------------
-- create t_ticket table
------------------------
CREATE TABLE t_ticket (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_ticket PRIMARY KEY,
      name      VARCHAR(100) NOT NULL,
      price     DECIMAL(8,2),
      onDate    DATE,
      event_id  INT CONSTRAINT fk_ticket_event
                REFERENCES t_event ON UPDATE RESTRICT ON DELETE RESTRICT,
      user_id   INT CONSTRAINT fk_ticket_user
                REFERENCES t_user ON UPDATE RESTRICT ON DELETE CASCADE
);

-------------------------------
-- create t_aspectcounter table
-------------------------------
CREATE TABLE t_aspectcounter (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_aspectcounter PRIMARY KEY,
      name    VARCHAR(80),
      target  VARCHAR(300),
      aspectCount   INT
);

-----------------------------
-- create t_useraccount table
-----------------------------
CREATE TABLE t_useraccount (
      id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_useraccount PRIMARY KEY,
      balance   DECIMAL(8,2),
      user_id   INT CONSTRAINT fk_useraccount_user
                REFERENCES t_user ON UPDATE RESTRICT ON DELETE CASCADE
);


