CREATE DATABASE  IF NOT EXISTS `eol` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eol`;
-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: eol
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `content_partner`
--

DROP TABLE IF EXISTS `content_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_partner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `url` text,
  `description` text,
  `logo_path` text,
  `logo_type` varchar(45) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `updated_at_UNIQUE` (`updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_partner`
--

LOCK TABLES `content_partner` WRITE;
/*!40000 ALTER TABLE `content_partner` DISABLE KEYS */;
INSERT INTO `content_partner` VALUES (1,'x2','a2','u2','d2','p2','','2017-08-16 14:45:05','2017-08-16 14:45:05'),(2,'x1','a1','u1','d1','p1/p1.txt','p1.txt','2017-08-16 14:46:17','2017-08-16 14:46:17'),(3,'xxx',NULL,'ABC Company',NULL,NULL,NULL,'2017-08-17 14:54:47','2017-08-17 14:54:47'),(4,'xxx',NULL,'ABC Company',NULL,NULL,NULL,'2017-08-17 15:05:26','2017-08-17 15:05:26'),(5,'cccccc','a1','u1','d1','p1/p1.txt','txt','2017-08-24 11:15:20','2017-08-24 11:15:20'),(6,'x2','a1','u1','d1','p1/p1.txt','txt','2017-08-26 09:12:35','2017-08-26 09:12:35'),(7,'x2','a1','u1','d1','p1/p1.txt','txt','2017-08-26 09:12:45','2017-08-26 09:12:45'),(8,'x100','a1','u1','d1','p1/p1.txt','txt','2017-08-26 13:46:39','2017-08-26 13:46:39'),(9,'cccccc','a1','u1','d1','p1/p1.txt','txt','2017-10-03 08:58:25','2017-10-03 08:58:25'),(10,'cccccc','a1','u1','d1','p1/p1.txt','txt','2017-10-03 09:08:37','2017-10-03 09:08:37'),(11,'cccccc','a1','u1','d1','p1/p1.txt','txt','2017-10-26 10:08:33','2017-10-26 10:08:33');
/*!40000 ALTER TABLE `content_partner` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `my_table_bi` BEFORE INSERT ON `content_partner` FOR EACH ROW
BEGIN
    SET NEW.created_at = NOW();
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `harvest`
--

DROP TABLE IF EXISTS `harvest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `harvest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL,
  `start_at` datetime DEFAULT NULL,
  `validated_at` datetime DEFAULT NULL,
  `deltas_created_at` datetime DEFAULT NULL,
  `completed_at` datetime DEFAULT NULL,
  `state` enum('succeed','failed','running','pending																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																												') DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `harvest`
--

LOCK TABLES `harvest` WRITE;
/*!40000 ALTER TABLE `harvest` DISABLE KEYS */;
INSERT INTO `harvest` VALUES (1,2,'2017-11-07 15:23:39',NULL,NULL,'2017-11-07 15:23:39','succeed','2017-11-07 15:23:39','2017-11-07 15:23:39'),(2,4,'2017-11-07 15:23:40',NULL,NULL,'2017-11-07 15:23:40','succeed','2017-11-07 15:23:40','2017-11-07 15:23:40'),(3,3,'2017-11-07 15:24:09',NULL,NULL,'2017-11-07 15:24:09','succeed','2017-11-07 15:24:08','2017-11-07 15:24:08'),(4,4,'2017-11-07 15:24:09',NULL,NULL,'2017-11-07 15:24:09','succeed','2017-11-07 15:24:09','2017-11-07 15:24:09'),(5,3,'2017-11-07 15:24:39',NULL,NULL,'2017-11-07 15:24:39','succeed','2017-11-07 15:24:38','2017-11-07 15:24:38'),(6,4,'2017-11-07 15:24:39',NULL,NULL,'2017-11-07 15:24:39','succeed','2017-11-07 15:24:39','2017-11-07 15:24:39'),(7,3,'2017-11-07 15:24:39',NULL,NULL,'2017-11-07 15:24:39','succeed','2017-11-07 15:24:39','2017-11-07 15:24:39'),(8,2,'2017-11-07 15:25:09',NULL,NULL,'2017-11-07 15:25:09','succeed','2017-11-07 15:25:08','2017-11-07 15:25:08'),(9,4,'2017-11-07 15:25:09',NULL,NULL,'2017-11-07 15:25:09','succeed','2017-11-07 15:25:09','2017-11-07 15:25:09'),(10,3,'2017-11-07 15:25:09',NULL,NULL,'2017-11-07 15:25:09','succeed','2017-11-07 15:25:09','2017-11-07 15:25:09'),(11,2,'2017-11-07 15:25:39',NULL,NULL,'2017-11-07 15:25:39','succeed','2017-11-07 15:25:38','2017-11-07 15:25:38'),(12,4,'2017-11-07 15:25:39',NULL,NULL,'2017-11-07 15:25:39','succeed','2017-11-07 15:25:39','2017-11-07 15:25:39'),(13,3,'2017-11-07 15:25:39',NULL,NULL,'2017-11-07 15:25:39','succeed','2017-11-07 15:25:39','2017-11-07 15:25:39'),(14,2,'2017-11-07 15:26:09',NULL,NULL,'2017-11-07 15:26:09','succeed','2017-11-07 15:26:08','2017-11-07 15:26:08'),(15,4,'2017-11-07 15:26:09',NULL,NULL,'2017-11-07 15:26:09','succeed','2017-11-07 15:26:09','2017-11-07 15:26:09'),(16,3,'2017-11-07 15:26:09',NULL,NULL,'2017-11-07 15:26:09','succeed','2017-11-07 15:26:09','2017-11-07 15:26:09'),(17,2,'2017-11-07 15:26:39',NULL,NULL,'2017-11-07 15:26:39','succeed','2017-11-07 15:26:38','2017-11-07 15:26:38'),(18,4,'2017-11-07 15:26:39',NULL,NULL,'2017-11-07 15:26:39','succeed','2017-11-07 15:26:39','2017-11-07 15:26:39'),(19,3,'2017-11-07 15:26:39',NULL,NULL,'2017-11-07 15:26:39','succeed','2017-11-07 15:26:39','2017-11-07 15:26:39'),(20,2,'2017-11-07 15:27:09',NULL,NULL,'2017-11-07 15:27:09','succeed','2017-11-07 15:27:08','2017-11-07 15:27:08'),(21,4,'2017-11-07 15:27:09',NULL,NULL,'2017-11-07 15:27:09','succeed','2017-11-07 15:27:09','2017-11-07 15:27:09'),(22,3,'2017-11-07 15:27:09',NULL,NULL,'2017-11-07 15:27:09','succeed','2017-11-07 15:27:09','2017-11-07 15:27:09'),(23,2,'2017-11-07 15:27:39',NULL,NULL,'2017-11-07 15:27:39','succeed','2017-11-07 15:27:38','2017-11-07 15:27:38'),(24,4,'2017-11-07 15:27:39',NULL,NULL,'2017-11-07 15:27:39','succeed','2017-11-07 15:27:39','2017-11-07 15:27:39'),(25,3,'2017-11-07 15:27:39',NULL,NULL,'2017-11-07 15:27:39','succeed','2017-11-07 15:27:39','2017-11-07 15:27:39'),(26,2,'2017-11-07 15:28:09',NULL,NULL,'2017-11-07 15:28:09','succeed','2017-11-07 15:28:08','2017-11-07 15:28:08'),(27,4,'2017-11-07 15:28:09',NULL,NULL,'2017-11-07 15:28:09','succeed','2017-11-07 15:28:09','2017-11-07 15:28:09'),(28,3,'2017-11-07 15:28:09',NULL,NULL,'2017-11-07 15:28:09','succeed','2017-11-07 15:28:09','2017-11-07 15:28:09'),(29,2,'2017-11-07 15:28:39',NULL,NULL,'2017-11-07 15:28:39','succeed','2017-11-07 15:28:38','2017-11-07 15:28:38'),(30,4,'2017-11-07 15:28:39',NULL,NULL,'2017-11-07 15:28:39','succeed','2017-11-07 15:28:39','2017-11-07 15:28:39'),(31,3,'2017-11-07 15:28:39',NULL,NULL,'2017-11-07 15:28:39','succeed','2017-11-07 15:28:39','2017-11-07 15:28:39'),(32,2,'2017-11-07 16:45:24',NULL,NULL,'2017-11-07 16:45:24','succeed','2017-11-07 16:45:24','2017-11-07 16:45:24'),(33,4,'2017-11-07 16:45:25',NULL,NULL,'2017-11-07 16:45:25','succeed','2017-11-07 16:45:24','2017-11-07 16:45:24'),(34,3,'2017-11-07 16:45:54',NULL,NULL,'2017-11-07 16:45:54','succeed','2017-11-07 16:45:53','2017-11-07 16:45:53'),(35,4,'2017-11-07 16:45:54',NULL,NULL,'2017-11-07 16:45:54','succeed','2017-11-07 16:45:54','2017-11-07 16:45:54'),(36,2,'2017-11-07 17:15:21',NULL,NULL,'2017-11-07 17:15:21','succeed','2017-11-07 17:15:21','2017-11-07 17:15:21'),(37,2,'2017-11-07 17:29:41',NULL,NULL,'2017-11-07 17:29:41','succeed','2017-11-07 17:29:44','2017-11-07 17:29:44'),(38,2,'2017-11-07 17:54:54',NULL,NULL,'2017-11-07 17:54:54','succeed','2017-11-07 17:54:53','2017-11-07 17:54:53'),(39,2,'2017-11-07 18:00:17',NULL,NULL,'2017-11-07 18:00:17','succeed','2017-11-07 18:00:16','2017-11-07 18:00:16'),(40,2,'2017-11-07 18:10:44',NULL,NULL,'2017-11-07 18:10:44','succeed','2017-11-07 18:10:43','2017-11-07 18:10:43'),(41,4,'2017-11-07 18:11:13',NULL,NULL,'2017-11-07 18:11:13','succeed','2017-11-07 18:11:13','2017-11-07 18:11:13'),(42,4,'2017-11-07 18:11:43',NULL,NULL,'2017-11-07 18:11:43','succeed','2017-11-07 18:11:43','2017-11-07 18:11:43'),(43,2,'2017-11-07 18:24:14',NULL,NULL,'2017-11-07 18:24:14','succeed','2017-11-07 18:24:13','2017-11-07 18:24:13'),(44,4,'2017-11-07 18:24:14',NULL,NULL,'2017-11-07 18:24:14','succeed','2017-11-07 18:24:14','2017-11-07 18:24:14'),(45,3,'2017-11-07 18:24:44',NULL,NULL,'2017-11-07 18:24:44','succeed','2017-11-07 18:24:43','2017-11-07 18:24:43'),(46,3,'2017-11-07 18:25:14',NULL,NULL,'2017-11-07 18:25:14','succeed','2017-11-07 18:25:13','2017-11-07 18:25:13'),(47,2,'2017-11-07 18:40:24',NULL,NULL,'2017-11-07 18:40:24','succeed','2017-11-07 18:40:24','2017-11-07 18:40:24'),(48,4,'2017-11-07 18:40:24',NULL,NULL,'2017-11-07 18:40:24','succeed','2017-11-07 18:40:24','2017-11-07 18:40:24'),(49,3,'2017-11-07 18:40:25',NULL,NULL,'2017-11-07 18:40:25','succeed','2017-11-07 18:40:24','2017-11-07 18:40:24'),(50,2,'2017-11-07 18:47:30',NULL,NULL,'2017-11-07 18:47:30','succeed','2017-11-07 18:47:30','2017-11-07 18:47:30'),(51,2,'2017-11-08 11:23:19',NULL,NULL,'2017-11-08 11:23:21','succeed','2017-11-08 11:23:21','2017-11-08 11:23:21'),(52,3,'2017-11-08 13:57:17',NULL,NULL,'2017-11-08 13:57:18','succeed','2017-11-08 13:57:17','2017-11-08 13:57:17'),(53,4,'2017-11-08 13:57:18',NULL,NULL,'2017-11-08 13:57:18','succeed','2017-11-08 13:57:17','2017-11-08 13:57:17'),(54,2,'2017-11-09 17:13:23',NULL,NULL,'2017-11-09 17:13:26','succeed','2017-11-09 17:13:26','2017-11-09 17:13:26'),(55,101,'2017-11-09 17:13:27',NULL,NULL,'2017-11-09 17:13:27','succeed','2017-11-09 17:13:26','2017-11-09 17:13:26'),(56,100,'2017-11-09 17:13:27',NULL,NULL,'2017-11-09 17:13:27','succeed','2017-11-09 17:13:27','2017-11-09 17:13:27'),(57,6,'2017-11-09 17:13:27',NULL,NULL,'2017-11-09 17:13:27','succeed','2017-11-09 17:13:27','2017-11-09 17:13:27'),(58,5,'2017-11-09 17:13:27',NULL,NULL,'2017-11-09 17:13:27','succeed','2017-11-09 17:13:27','2017-11-09 17:13:27'),(59,4,'2017-11-09 17:13:28',NULL,NULL,'2017-11-09 17:13:28','succeed','2017-11-09 17:13:27','2017-11-09 17:13:27'),(60,3,'2017-11-09 17:13:28',NULL,NULL,'2017-11-09 17:13:28','succeed','2017-11-09 17:13:27','2017-11-09 17:13:27'),(61,110,'2017-11-14 08:58:38',NULL,NULL,'2017-11-14 08:58:48','succeed','2017-11-14 08:58:48','2017-11-14 08:58:48'),(62,110,'2017-11-14 10:52:23',NULL,NULL,'2017-11-14 10:52:34','succeed','2017-11-14 10:52:33','2017-11-14 10:52:33');
/*!40000 ALTER TABLE `harvest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_partner_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `origin_url` text,
  `uploaded_url` text,
  `type` enum('url','file') DEFAULT NULL,
  `path` text,
  `last_harvested_at` datetime DEFAULT NULL,
  `harvest_frequency` enum('0','7','30','60','90') DEFAULT NULL,
  `day_of_month` int(11) unsigned DEFAULT '0',
  `nodes_count` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT '-1',
  `is_paused` tinyint(1) DEFAULT '0',
  `is_approved` tinyint(1) DEFAULT '0',
  `is_trusted` tinyint(1) DEFAULT '0',
  `is_autopublished` tinyint(1) DEFAULT '0',
  `is_forced` tinyint(1) DEFAULT '0',
  `forced_internally` tinyint(1) DEFAULT '0',
  `dataset_license` int(11) DEFAULT '47',
  `dataset_rights_statement` text,
  `dataset_rights_holder` text,
  `default_license_string` int(11) DEFAULT NULL,
  `default_rights_statement` text,
  `default_rights_holder` text,
  `default_language_id` int(11) DEFAULT '152',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (2,1,'test3','u1',NULL,'file','p1',NULL,'30',28,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-08-17 13:33:22','2017-11-12 13:36:33'),(3,1,'p1r1','u1',NULL,'url','p1',NULL,'30',28,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-08-22 16:26:46','2017-11-12 13:36:33'),(4,1,'p3r3','u1',NULL,'url','p1',NULL,'30',28,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-08-22 16:45:45','2017-11-12 13:36:33'),(5,1,'test3','u1',NULL,NULL,'p1',NULL,'30',0,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-10-03 11:09:04','2017-11-12 13:36:33'),(6,1,'test3','u1',NULL,NULL,'p1',NULL,'30',0,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-10-03 11:29:39','2017-11-12 13:36:33'),(100,1,'test3','u1',NULL,NULL,'p1',NULL,'30',0,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-10-03 11:29:41','2017-11-12 13:36:33'),(110,1,'resourceX','http://species.com','tesuyfrgt',NULL,NULL,NULL,'7',0,0,-1,0,0,0,0,0,0,47,NULL,NULL,0,NULL,NULL,152,'2017-10-26 12:03:43','2017-11-14 10:48:07');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'eol'
--
/*!50003 DROP PROCEDURE IF EXISTS `harvestResource` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `harvestResource`(IN cDate datetime)
begin
 SELECT * FROM resource
    WHERE  `type` = 'file' AND `is_paused` = '0' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR 
    `is_forced` = '1') 
    UNION
    SELECT * FROM resource
    WHERE  `type` = 'url' AND `is_paused` = '0' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR 
    `is_forced` = '1' OR DATE_ADD(`last_harvested_at`,INTERVAL `harvest_frequency` DAY) = cDate) ;

  
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-19 15:20:57
