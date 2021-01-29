USE Assignment2;

DROP TABLE IF EXISTS Card CASCADE;
DROP TABLE IF EXISTS Cash CASCADE;
DROP TABLE IF EXISTS BoughtSnack CASCADE;
DROP TABLE IF EXISTS Snacks CASCADE;
DROP TABLE IF EXISTS ValidTransaction CASCADE;
DROP TABLE IF EXISTS CancelledTransaction CASCADE;
DROP TABLE IF EXISTS Transaction CASCADE;
DROP TABLE IF EXISTS User CASCADE;

CREATE TABLE IF NOT EXISTS Snacks (
  `rollno`      INT(10) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL,
  `category`    VARCHAR(50) NOT NULL,
  `price`       FLOAT NOT NULL,
  `quantity`    INT(10) NOT NULL,
  `code`        INT(10) NOT NULL,
  `image`       VARCHAR(50) NOT NULL,
  PRIMARY KEY (`rollno`),
  CONSTRAINT CHK_Quantity CHECK (quantity<=15 AND quantity>=0)
);

CREATE TABLE IF NOT EXISTS User (
  `userid`      INT(10) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL,
  `userType`    VARCHAR(50) NOT NULL,
  `password`    VARCHAR(50) NOT NULL,
  PRIMARY KEY (`userid`),
  CONSTRAINT UC_User UNIQUE (`name`,`password`)
);

CREATE TABLE IF NOT EXISTS Card (
  `userid`      INT(10) NOT NULL,
  `name`        VARCHAR(50) NOT NULL,
  `number`      VARCHAR(50) NOT NULL,
  PRIMARY KEY (`userid`,`name`),
  FOREIGN KEY (`userid`) REFERENCES User(`userid`)
);

CREATE TABLE IF NOT EXISTS Cash (
  `price`       VARCHAR(10) NOT NULL,
  `quantity`    INT(10) NOT NULL,
  PRIMARY KEY (`price`)
);

CREATE TABLE IF NOT EXISTS Transaction (
  `id`          INT(10) NOT NULL AUTO_INCREMENT,
  `status`      VARCHAR(10) NOT NULL,
  `userid`      INT(10),
  `date`        TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userid`) REFERENCES User(`userid`)
);

CREATE TABLE IF NOT EXISTS ValidTransaction (
  `id`          INT(10),
  `payment`     VARCHAR(10),
  `paid`        FLOAT,
  `change`      FLOAT,
  FOREIGN KEY (`id`) REFERENCES Transaction(`id`)
);

CREATE TABLE IF NOT EXISTS CancelledTransaction (
  `id`          INT(10),
  `reason`      VARCHAR(10) NOT NULL,
  FOREIGN KEY (`id`) REFERENCES Transaction(`id`)
);

CREATE TABLE IF NOT EXISTS BoughtSnack (
  `id`          INT(10),
  `rollno`      INT(10),
  `quantity`    INT(10),
  FOREIGN KEY (`id`) REFERENCES Transaction(`id`),
  FOREIGN KEY (`rollno`) REFERENCES Snacks(`rollno`)
);
