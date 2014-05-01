CREATE DATABASE  IF NOT EXISTS `app` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `app`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: ec2-54-200-98-161.us-west-2.compute.amazonaws.com    Database: app
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.13.10.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ParkingCoordinates`
--

DROP TABLE IF EXISTS `ParkingCoordinates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ParkingCoordinates` (
  `ParkingCoordinateId` int(11) NOT NULL AUTO_INCREMENT,
  `Latitude` float NOT NULL,
  `Longitude` float NOT NULL,
  `LotId` int(11) NOT NULL,
  PRIMARY KEY (`ParkingCoordinateId`),
  KEY `FK_PARKINGCOORDINATES_PARKINGLOTS` (`LotId`),
  CONSTRAINT `FK_PARKINGCOORDINATES_PARKINGLOTS` FOREIGN KEY (`LotId`) REFERENCES `ParkingLots` (`LotId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ParkingLots`
--

DROP TABLE IF EXISTS `ParkingLots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ParkingLots` (
  `LotId` int(11) NOT NULL AUTO_INCREMENT,
  `LotNumber` varchar(50) NOT NULL,
  `Active` bit(1) NOT NULL DEFAULT b'1',
  `TypeId` int(11) NOT NULL,
  `Rows` int(11) NOT NULL,
  `Columns` int(11) NOT NULL,
  PRIMARY KEY (`LotId`),
  KEY `FK_PARKINGTYPES_PARKINGLOTS` (`TypeId`),
  CONSTRAINT `FK_PARKINGTYPES_PARKINGLOTS` FOREIGN KEY (`TypeId`) REFERENCES `ParkingTypes` (`TypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ParkingSpaces`
--

DROP TABLE IF EXISTS `ParkingSpaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ParkingSpaces` (
  `SpaceId` int(11) NOT NULL AUTO_INCREMENT,
  `Number` int(11) NOT NULL,
  `Active` bit(1) NOT NULL,
  `StatusId` int(11) NOT NULL,
  `LotId` int(11) NOT NULL,
  PRIMARY KEY (`SpaceId`),
  KEY `FK_PARKINGSTATUSES_PARKINGSPACES` (`StatusId`),
  KEY `FK_PARKINGLOTS_PARKINGSPACES` (`LotId`),
  CONSTRAINT `FK_PARKINGLOTS_PARKINGSPACES` FOREIGN KEY (`LotId`) REFERENCES `ParkingLots` (`LotId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PARKINGSTATUSES_PARKINGSPACES` FOREIGN KEY (`StatusId`) REFERENCES `ParkingStatuses` (`StatusId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ParkingStatuses`
--

DROP TABLE IF EXISTS `ParkingStatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ParkingStatuses` (
  `StatusId` int(11) NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`StatusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ParkingTypes`
--

DROP TABLE IF EXISTS `ParkingTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ParkingTypes` (
  `TypeId` int(11) NOT NULL,
  `Description` varchar(50) NOT NULL,
  `Active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`TypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Reservations`
--

DROP TABLE IF EXISTS `Reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservations` (
  `ReservationId` int(11) NOT NULL AUTO_INCREMENT,
  `SpaceId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `TimeIn` datetime NOT NULL,
  `TimeOut` datetime NOT NULL,
  `Active` bit(1) DEFAULT b'1',
  `LotId` int(11) NOT NULL,
  PRIMARY KEY (`ReservationId`),
  KEY `Relationship2` (`UserId`),
  CONSTRAINT `Relationship2` FOREIGN KEY (`UserId`) REFERENCES `Users` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `RoleId` varchar(50) NOT NULL,
  `Description` varchar(50) NOT NULL,
  `Active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`RoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `Password` char(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `Email` varchar(50) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Active` bit(1) DEFAULT b'1',
  `RoleId` varchar(50) NOT NULL,
  `Salt` char(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `IsAdmin` bit(1) DEFAULT b'0',
  PRIMARY KEY (`UserId`),
  KEY `FK_ROLES_USERS` (`RoleId`),
  CONSTRAINT `FK_ROLES_USERS` FOREIGN KEY (`RoleId`) REFERENCES `Roles` (`RoleId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UsersSessions`
--

DROP TABLE IF EXISTS `UsersSessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UsersSessions` (
  `SessionId` int(11) NOT NULL AUTO_INCREMENT,
  `SessionKey` varchar(50) NOT NULL,
  `DateTimeIn` datetime NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`SessionId`),
  KEY `FK_USERSSESSIONS_USERS` (`UserId`),
  CONSTRAINT `FK_USERSSESSIONS_USERS` FOREIGN KEY (`UserId`) REFERENCES `Users` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `v_user_parking`
--

DROP TABLE IF EXISTS `v_user_parking`;
/*!50001 DROP VIEW IF EXISTS `v_user_parking`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_user_parking` (
  `UserName` tinyint NOT NULL,
  `FirstName` tinyint NOT NULL,
  `LastName` tinyint NOT NULL,
  `LotId` tinyint NOT NULL,
  `DateTimeIn` tinyint NOT NULL,
  `DateTimeOut` tinyint NOT NULL,
  `Duration` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_users`
--

DROP TABLE IF EXISTS `v_users`;
/*!50001 DROP VIEW IF EXISTS `v_users`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_users` (
  `UserId` tinyint NOT NULL,
  `UserName` tinyint NOT NULL,
  `LastName` tinyint NOT NULL,
  `FirstName` tinyint NOT NULL,
  `Email` tinyint NOT NULL,
  `RoleId` tinyint NOT NULL,
  `Role` tinyint NOT NULL,
  `Admin` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_user_parking`
--

/*!50001 DROP TABLE IF EXISTS `v_user_parking`*/;
/*!50001 DROP VIEW IF EXISTS `v_user_parking`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`app`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `v_user_parking` AS select `u`.`UserName` AS `UserName`,`u`.`FirstName` AS `FirstName`,`u`.`LastName` AS `LastName`,`r`.`LotId` AS `LotId`,concat(date_format(`r`.`TimeIn`,'%m %d %Y'),' ',date_format(`r`.`TimeIn`,'%T')) AS `DateTimeIn`,concat(date_format(`r`.`TimeOut`,'%m %d %Y'),' ',date_format(`r`.`TimeOut`,'%T')) AS `DateTimeOut`,timediff(`r`.`TimeOut`,`r`.`TimeIn`) AS `Duration` from (`Reservations` `r` join `Users` `u` on((`r`.`UserId` = `u`.`UserId`))) order by concat(date_format(`r`.`TimeOut`,'%m %d %Y'),' ',date_format(`r`.`TimeOut`,'%T')) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_users`
--

/*!50001 DROP TABLE IF EXISTS `v_users`*/;
/*!50001 DROP VIEW IF EXISTS `v_users`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`app`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `v_users` AS select `u`.`UserId` AS `UserId`,`u`.`UserName` AS `UserName`,`u`.`LastName` AS `LastName`,`u`.`FirstName` AS `FirstName`,`u`.`Email` AS `Email`,`r`.`RoleId` AS `RoleId`,`r`.`Description` AS `Role`,(case when (`u`.`IsAdmin` = 1) then 'Yes' else 'No' end) AS `Admin` from (`Users` `u` join `Roles` `r` on((`u`.`RoleId` = `r`.`RoleId`))) where ((`u`.`Active` = 1) and (`u`.`UserName` <> 'test')) order by `u`.`LastName`,`u`.`FirstName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-30 22:08:59
