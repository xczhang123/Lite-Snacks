USE Assignment2;
CREATE TABLE IF NOT EXISTS Snacks (
  `rollno`      INT(10) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL,
  `category`    VARCHAR(50) NOT NULL,
  `price`       FLOAT NOT NULL,
  `quantity`    INT(10) NOT NULL,
  `code`        INT(10) NOT NULL,
  PRIMARY KEY (`rollno`),
  CONSTRAINT CHK_Quantity CHECK (quantity<=15 AND quantity>=0)
)
