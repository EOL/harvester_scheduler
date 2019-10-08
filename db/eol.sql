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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;
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
  `state` enum('succeed','failed','running','pending') DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

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
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2017-10-31 12:14:12

ALTER TABLE `eol`.`content_partner`
DROP INDEX `updated_at_UNIQUE` ;


ALTER TABLE `eol`.`resource`
CHANGE COLUMN `updated_at` `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;

ALTER TABLE `eol`.`resource`
CHANGE COLUMN `created_at` `created_at` TIMESTAMP NULL DEFAULT NULL,


ALTER TABLE `eol`.`resource`
ADD COLUMN `is_harvest_inprogress` TINYINT(1)  DEFAULT '0' AFTER `path`;



USE `eol`;
DROP procedure IF EXISTS `harvestResource`;

DELIMITER $$
USE `eol`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `harvestResource`(IN cDate datetime)
begin
 SELECT * FROM resource
    WHERE  `type` = 'file' AND `is_paused` = '0' AND is_harvest_inprogress != '1' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR
    `is_forced` = '1')
    UNION
    SELECT * FROM resourcePropertiesFile
    WHERE  `type` = 'url' AND `is_paused` = '0' AND is_harvest_inprogress != '1' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR
    `is_forced` = '1' OR DATE_ADD(`last_harvested_at`,INTERVAL `harvest_frequency` DAY) = cDate) ;




end$$

DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getHarvestedResources`(IN cDate datetime)
begin
 SELECT count(*) FROM resource
    WHERE `last_harvested_at` >= cDate AND is_harvest_inprogress != '1' ;
end$$
DELIMITER ;
