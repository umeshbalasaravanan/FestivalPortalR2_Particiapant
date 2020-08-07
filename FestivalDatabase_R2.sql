CREATE DATABASE `festivaldatabase2`;

USE `festivaldatabase2`;

DROP TABLE IF EXISTS `festivaldatabase2`.`event`;
CREATE TABLE  `festivaldatabase2`.`event` (
  `eventid` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `description` varchar(40) NOT NULL,
  `places` varchar(40) NOT NULL,
  `duration` varchar(20) DEFAULT NULL,
  `eventtype` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`eventid`)
) ;

DROP TABLE IF EXISTS `festivaldatabase2`.`eventcoordinator`;
CREATE TABLE  `festivaldatabase2`.`eventcoordinator` (
  `eventcoordinatorid` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `firstname` varchar(40) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phonenumber` varchar(40) DEFAULT NULL,
  `place` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`eventcoordinatorid`)
); 

DROP TABLE IF EXISTS `festivaldatabase2`.`visitor`;
CREATE TABLE  `festivaldatabase2`.`visitor` (
  `visitorid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `firstname` varchar(40) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phonenumber` varchar(40) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `isadmin` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`visitorid`)
);

DROP TABLE IF EXISTS `festivaldatabase2`.`eventsession`;
CREATE TABLE  `festivaldatabase2`.`eventsession` (
  `eventsessionid` int(11) NOT NULL,
  `eventcoordinatorid` int(11) NOT NULL,
  `eventid` int(11) NOT NULL,  
  `seatsavailable` int(11) DEFAULT NULL,
  PRIMARY KEY (`eventsessionid`),
  KEY `eventsession_event` (`eventid`),
  KEY `eventsession_coordinator` (`eventcoordinatorid`),
  CONSTRAINT `eventsession_coordinator` FOREIGN KEY (`eventcoordinatorid`) REFERENCES `eventcoordinator` (`eventcoordinatorid`),
  CONSTRAINT `eventsession_event` FOREIGN KEY (`eventid`) REFERENCES `event` (`eventid`)
);

DROP TABLE IF EXISTS `festivaldatabase2`.`eventsessionsignup`;
CREATE TABLE  `festivaldatabase2`.`eventsessionsignup` (
  `signupid` int(11) NOT NULL AUTO_INCREMENT,
  `visitorid` int(11) NOT NULL,
  `eventsessionid` int(11) NOT NULL,
  `eventid` int(11) NOT NULL,
  PRIMARY KEY (`signupid`),
  KEY `esignup_visitor` (`visitorid`),
  KEY `esignup_esession` (`eventsessionid`),
  KEY `esignup_event` (`eventid`),
  CONSTRAINT `esignup_esession` FOREIGN KEY (`eventsessionid`) REFERENCES `eventsession` (`eventsessionid`),
  CONSTRAINT `esignup_visitor` FOREIGN KEY (`visitorid`) REFERENCES `visitor` (`visitorid`),
  CONSTRAINT `esignup_event` FOREIGN KEY (`eventid`) REFERENCES `event` (`eventid`)
); 

INSERT INTO EVENT (EVENTID,NAME,DESCRIPTION,PLACES,DURATION,EVENTTYPE)
VALUES ('1001','Rose Parade','Floats, Music and More','Rose Garden','0900-1400','Tour'),
 ('1002','NCCL Semi Finals','North Division Cricket Match','New Codington Stadium','1800-2200','Sporting Event'),
 ('1003','Fireworks Show','Spectacular Display at River','New Codington River Walk','1900-2000','Attraction'),
 ('1004','Garden Tour','Tour the Beautiful Rose Garden','Rose Garden','1000-1400','Tour'),
 ('1005','NCCL Championship','North vs. South Cricket Match','New Codington Stadium','1800-2200','Sporting Event'),
 ('1006','Pavlova  - All World Tour','Pavlova in Concert','New Codington Music Hall','1930-2130','Concert')
;  


INSERT INTO VISITOR (VISITORID,USERNAME,PASSWORD,FIRSTNAME,LASTNAME,EMAIL,PHONENUMBER,ISADMIN)
VALUES ('1001','bsmith','password','Bob','Smith','bsmith@email.com','748937487',0),
   ('1002','npatel','password','Nitin','Patel','npatel@email.com','3392382',0),
   ('1003','jjones','password','James','Jones','jjones@email.com','2342343244',0),
   ('1004','jfrancois','password','Jacques','Francois','jfrancois@email.com','1234567890',0),
   ('1005','rkreiger','password','Robert','Kreiger','rkreiger@email.com','49872938',0),
   ('1006','ylee','password','Yi-Hui','Lee','ylee@email.com','21239393',0),
   ('1007','admin','admin','F - Admin','L - Admin','admin@email.com','21239393', 1);
   
insert into eventcoordinator values 
(101,'admin','admin001','admin','admin','admin@gmail.com',9898,'P001'),
(102,'admin2','admin2001','admin2','admin2','admin2@gmail.com',2323,'P001'),
(103,'admin3','admin3001','admin3','admin3','admin3@gmail.com',2323,'P002'),
(104,'admin4','admin4001','admin4','admin4','admin4@gmail.com',2323,'P002'),
(105,'admin5','admin5001','admin5','admin5','admin5@gmail.com',2323,'P003'),
(106,'admin6','admin6001','admin6','admin6','admin6@gmail.com',2323,'P004'),
(107,'admin7','admin7001','admin7','admin7','admin7@gmail.com',2323,'P004');


insert into eventsession values
(10001,101,1001,300),
(10002,102,1002,250),
(10003,103,1001,300),
(10004,104,1002,250),
(10005,105,1003,400),
(10006,106,1003,400);
