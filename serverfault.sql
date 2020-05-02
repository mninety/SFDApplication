-- MySQL dump 10.10
--
-- Host: localhost    Database: serverfault
-- ------------------------------------------------------
-- Server version	5.0.18-nt-log

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
-- Table structure for table `alarmtracker`
--

DROP TABLE IF EXISTS `alarmtracker`;
CREATE TABLE `alarmtracker` (
  `atID` int(10) NOT NULL auto_increment,
  `atOSType` int(2) NOT NULL default '1',
  `atServerIP` varchar(15) NOT NULL,
  `atServiceID` int(1) NOT NULL,
  `atValue` decimal(7,2) NOT NULL,
  `atTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `atDayID` varchar(10) NOT NULL,
  PRIMARY KEY  (`atID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `alarmtracker`
--


/*!40000 ALTER TABLE `alarmtracker` DISABLE KEYS */;
LOCK TABLES `alarmtracker` WRITE;
INSERT INTO `alarmtracker` VALUES (1,1,'192.168.20.106',4,'8.92','2017-11-30 15:21:28','30112017'),(2,1,'192.168.20.106',2,'65.63','2017-11-30 15:21:38','30112017'),(3,1,'192.168.20.106',1,'4116.64','2017-11-30 15:21:38','30112017'),(4,1,'192.168.20.106',3,'108.07','2017-11-30 15:21:40','30112017'),(5,1,'192.168.20.106',3,'2.91','2017-11-30 15:21:57','30112017'),(6,1,'192.168.20.102',4,'0.25','2017-12-01 09:19:09','01122017'),(7,1,'192.168.20.102',2,'6.50','2017-12-01 09:19:20','01122017'),(8,1,'192.168.20.102',1,'2541.82','2017-12-01 09:19:20','01122017'),(9,1,'192.168.0.114',4,'3.25','2017-12-07 11:24:48','07122017'),(10,1,'192.168.0.114',2,'16.54','2017-12-07 11:24:57','07122017'),(11,1,'192.168.0.114',1,'5516.18','2017-12-07 11:24:57','07122017'),(12,1,'192.168.0.114',3,'106.24','2017-12-07 11:24:58','07122017'),(13,1,'192.168.0.114',3,'3.21','2017-12-07 11:25:13','07122017'),(14,1,'192.168.0.114',2,'1.89','2017-12-07 11:27:10','07122017'),(15,1,'192.168.0.114',1,'5558.45','2017-12-07 11:27:11','07122017'),(16,1,'192.168.0.114',4,'3.25','2017-12-07 11:27:14','07122017'),(17,1,'192.168.0.114',3,'107.45','2017-12-07 11:27:22','07122017'),(18,1,'192.168.0.114',3,'2.09','2017-12-07 11:27:36','07122017'),(19,1,'192.168.0.114',1,'4879.09','2017-12-07 11:29:22','07122017'),(20,1,'192.168.0.114',2,'8.60','2017-12-07 11:29:23','07122017'),(21,1,'192.168.0.114',4,'3.25','2017-12-07 11:29:37','07122017'),(22,1,'192.168.0.114',3,'108.11','2017-12-07 11:29:46','07122017'),(23,1,'192.168.0.114',3,'3.49','2017-12-07 11:30:02','07122017'),(24,1,'192.168.0.114',1,'4233.00','2017-12-07 11:31:34','07122017'),(25,1,'192.168.0.114',2,'30.71','2017-12-07 11:31:36','07122017'),(26,1,'192.168.0.114',4,'3.25','2017-12-07 11:32:03','07122017'),(27,1,'192.168.0.114',3,'109.64','2017-12-07 11:32:25','07122017'),(28,1,'192.168.0.114',3,'3.28','2017-12-07 11:32:51','07122017'),(29,1,'192.168.0.114',1,'3750.09','2017-12-07 11:33:46','07122017'),(30,1,'192.168.0.114',2,'6.98','2017-12-07 11:33:49','07122017');
UNLOCK TABLES;
/*!40000 ALTER TABLE `alarmtracker` ENABLE KEYS */;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `iSGUI` int(11) NOT NULL default '0',
  `isWindows` int(11) NOT NULL default '0',
  `isDynamicThreshold` int(11) NOT NULL default '0',
  `driverlog` varchar(100) NOT NULL default '/usr',
  `isLive` int(11) NOT NULL default '1'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `configuration`
--


/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
LOCK TABLES `configuration` WRITE;
INSERT INTO `configuration` VALUES (1,1,0,'D:',1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;

--
-- Table structure for table `cpudata`
--

DROP TABLE IF EXISTS `cpudata`;
CREATE TABLE `cpudata` (
  `cdID` int(10) NOT NULL auto_increment,
  `cdOSType` int(2) NOT NULL default '1',
  `cdServerIP` varchar(15) NOT NULL,
  `cdValue` decimal(4,2) NOT NULL,
  `cdTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `cdDayID` varchar(10) NOT NULL,
  PRIMARY KEY  (`cdID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cpudata`
--


/*!40000 ALTER TABLE `cpudata` DISABLE KEYS */;
LOCK TABLES `cpudata` WRITE;
INSERT INTO `cpudata` VALUES (1,1,'192.168.20.106','65.63','2017-11-30 15:21:37','30112017'),(2,1,'192.168.20.102','6.50','2017-12-01 09:19:19','01122017'),(3,1,'192.168.20.102','4.77','2017-12-01 09:21:35','01122017'),(4,1,'192.168.0.114','16.54','2017-12-07 11:24:56','07122017'),(5,1,'192.168.0.114','1.89','2017-12-07 11:27:10','07122017'),(6,1,'192.168.0.114','8.60','2017-12-07 11:29:23','07122017'),(7,1,'192.168.0.114','30.71','2017-12-07 11:31:36','07122017'),(8,1,'192.168.0.114','6.98','2017-12-07 11:33:49','07122017');
UNLOCK TABLES;
/*!40000 ALTER TABLE `cpudata` ENABLE KEYS */;

--
-- Table structure for table `diskdata`
--

DROP TABLE IF EXISTS `diskdata`;
CREATE TABLE `diskdata` (
  `ddID` int(10) NOT NULL auto_increment,
  `ddOSType` int(2) NOT NULL default '1',
  `ddServerIP` varchar(15) NOT NULL,
  `ddValue` decimal(7,2) NOT NULL,
  `ddUnit` varchar(10) NOT NULL default 'MB/s',
  `ddType` int(1) NOT NULL,
  `ddTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `ddDayID` varchar(10) NOT NULL,
  PRIMARY KEY  (`ddID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `diskdata`
--


/*!40000 ALTER TABLE `diskdata` DISABLE KEYS */;
LOCK TABLES `diskdata` WRITE;
INSERT INTO `diskdata` VALUES (1,1,'192.168.20.106','108.07','MB/s',1,'2017-11-30 15:21:40','30112017'),(2,1,'192.168.20.106','2.91','MB/s',2,'2017-11-30 15:21:57','30112017'),(3,1,'192.168.0.114','106.24','MB/s',1,'2017-12-07 11:24:58','07122017'),(4,1,'192.168.0.114','3.21','MB/s',2,'2017-12-07 11:25:13','07122017'),(5,1,'192.168.0.114','107.45','MB/s',1,'2017-12-07 11:27:22','07122017'),(6,1,'192.168.0.114','2.09','MB/s',2,'2017-12-07 11:27:36','07122017'),(7,1,'192.168.0.114','108.11','MB/s',1,'2017-12-07 11:29:46','07122017'),(8,1,'192.168.0.114','3.49','MB/s',2,'2017-12-07 11:30:02','07122017'),(9,1,'192.168.0.114','109.64','MB/s',1,'2017-12-07 11:32:25','07122017'),(10,1,'192.168.0.114','3.28','MB/s',2,'2017-12-07 11:32:51','07122017');
UNLOCK TABLES;
/*!40000 ALTER TABLE `diskdata` ENABLE KEYS */;

--
-- Table structure for table `diskspace`
--

DROP TABLE IF EXISTS `diskspace`;
CREATE TABLE `diskspace` (
  `dsID` int(11) NOT NULL auto_increment,
  `dsOSType` int(2) NOT NULL default '1',
  `dsServerIP` varchar(15) NOT NULL,
  `dsValue` decimal(7,2) NOT NULL,
  `dsTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `dsDayID` varchar(10) NOT NULL,
  PRIMARY KEY  (`dsID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `diskspace`
--


/*!40000 ALTER TABLE `diskspace` DISABLE KEYS */;
LOCK TABLES `diskspace` WRITE;
INSERT INTO `diskspace` VALUES (1,1,'192.168.20.106','8.92','2017-11-30 15:21:26','30112017'),(2,1,'192.168.20.102','0.25','2017-12-01 09:19:07','01122017'),(3,1,'192.168.20.102','0.00','2017-12-01 09:21:19','01122017'),(4,1,'192.168.0.114','3.25','2017-12-07 11:24:47','07122017'),(5,1,'192.168.0.114','3.25','2017-12-07 11:27:14','07122017'),(6,1,'192.168.0.114','3.25','2017-12-07 11:29:37','07122017'),(7,1,'192.168.0.114','3.25','2017-12-07 11:32:03','07122017');
UNLOCK TABLES;
/*!40000 ALTER TABLE `diskspace` ENABLE KEYS */;

--
-- Table structure for table `memorydata`
--

DROP TABLE IF EXISTS `memorydata`;
CREATE TABLE `memorydata` (
  `mdID` int(10) NOT NULL auto_increment,
  `mdOSType` int(2) NOT NULL default '1',
  `mdServerIP` varchar(15) NOT NULL,
  `mdValue` decimal(7,2) NOT NULL,
  `mdTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `mdDayID` varchar(10) NOT NULL,
  PRIMARY KEY  (`mdID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `memorydata`
--


/*!40000 ALTER TABLE `memorydata` DISABLE KEYS */;
LOCK TABLES `memorydata` WRITE;
INSERT INTO `memorydata` VALUES (1,1,'192.168.20.106','4116.64','2017-11-30 15:21:37','30112017'),(2,1,'192.168.20.102','2541.82','2017-12-01 09:19:19','01122017'),(3,1,'192.168.20.102','3015.45','2017-12-01 09:21:35','01122017'),(4,1,'192.168.0.114','5516.18','2017-12-07 11:24:56','07122017'),(5,1,'192.168.0.114','5558.45','2017-12-07 11:27:11','07122017'),(6,1,'192.168.0.114','4879.09','2017-12-07 11:29:22','07122017'),(7,1,'192.168.0.114','4233.00','2017-12-07 11:31:34','07122017'),(8,1,'192.168.0.114','3750.09','2017-12-07 11:33:46','07122017');
UNLOCK TABLES;
/*!40000 ALTER TABLE `memorydata` ENABLE KEYS */;

--
-- Table structure for table `otptoken`
--

DROP TABLE IF EXISTS `otptoken`;
CREATE TABLE `otptoken` (
  `otID` int(10) NOT NULL auto_increment,
  `otFor` varchar(20) NOT NULL,
  `otExpireDate` decimal(18,0) NOT NULL,
  `otOTPToken` int(10) NOT NULL,
  PRIMARY KEY  (`otID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `otptoken`
--


/*!40000 ALTER TABLE `otptoken` DISABLE KEYS */;
LOCK TABLES `otptoken` WRITE;
INSERT INTO `otptoken` VALUES (1,'test4','1511449159543',-845230),(3,'test4','1511454759829',-745231),(4,'test4','1511456189113',-687091),(5,'test4','1511458042031',-788732),(6,'test4','1511458134025',-213282),(7,'test4','1511458240708',-627568),(8,'test4','1511458761466',740394),(9,'test5','1511459270944',-199261),(10,'masum','1511499152312',-592797),(11,'masum','1511500504434',-485293),(12,'masum','1511501237893',-116981),(13,'masum','1511502023494',-587308),(14,'masum','1511503956140',-226081),(15,'masum','1511504148715',-443709),(16,'test15','1511975714151',611426),(17,'test2','1512119961003',-768430),(18,'test17','1512666610652',-115624),(19,'masum','1512669834656',-169830),(20,'masum','1512671603775',-901188),(21,'masum1','1512671692504',698287),(22,'masum','1512672584863',-688110),(23,'masum','1512672845002',-317778),(24,'masum','1512672891216',333255);
UNLOCK TABLES;
/*!40000 ALTER TABLE `otptoken` ENABLE KEYS */;

--
-- Table structure for table `serialkey`
--

DROP TABLE IF EXISTS `serialkey`;
CREATE TABLE `serialkey` (
  `skID` int(10) NOT NULL auto_increment,
  `skFor` varchar(20) NOT NULL,
  `skServerIP` varchar(15) NOT NULL,
  `skType` tinyint(1) NOT NULL default '1',
  `skKey` varchar(30) NOT NULL,
  `skExpireDate` decimal(18,0) NOT NULL,
  PRIMARY KEY  (`skID`),
  UNIQUE KEY `skKey` (`skKey`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `serialkey`
--


/*!40000 ALTER TABLE `serialkey` DISABLE KEYS */;
LOCK TABLES `serialkey` WRITE;
INSERT INTO `serialkey` VALUES (-1,'masum','192.168.20.103',1,'1RIH5-DS3RF-F262T-LCYLM-XYGIV','1542996000000'),(-2,'masum','192.168.20.103',2,'DW2TG-5AMC7-PGEF5-V94EC-E76RK','1574532000000'),(-3,'test17','192.168.20.117',2,'N1C9T-UAUOE-9DFRB-GA6T3-TY743','1575655200000'),(-4,'test17','192.168.20.118',2,'Q1O49-F7ZN8-CHQIS-S9BB2-NIFVF','1575655200000'),(5,'test27','192.168.20.118',2,'MJPT5-I2K38-YE2B2-AUWL8-3DYMX','1575655200000');
UNLOCK TABLES;
/*!40000 ALTER TABLE `serialkey` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uID` int(10) NOT NULL auto_increment,
  `uUsername` varchar(20) NOT NULL,
  `uPassword` varchar(50) NOT NULL,
  `uName` varchar(50) NOT NULL,
  `uServerIP` varchar(15) NOT NULL,
  `uIsOTPEnable` tinyint(1) NOT NULL default '0',
  `uIsLive` tinyint(1) NOT NULL default '0',
  `uExpireDate` decimal(18,0) NOT NULL,
  `uPhone` varchar(20) NOT NULL,
  `uEmail` varchar(20) NOT NULL,
  PRIMARY KEY  (`uID`),
  UNIQUE KEY `uUsername` (`uUsername`),
  UNIQUE KEY `uServerIP` (`uServerIP`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--


/*!40000 ALTER TABLE `user` DISABLE KEYS */;
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'masum','23734cd52ad4a4fb877d8a1e26e5df5f','Mizanur Rahman','192.168.20.103',1,1,'1574532000000','01912026219','mninety@gmail.com'),(6,'test4','23734cd52ad4a4fb877d8a1e26e5df5f','SFD','192.168.20.104',0,1,'1511805600000','88018','mninety@gmail.com'),(7,'test6','63872b5565b2179bd72ea9c339192543','SFD','192.168.20.106',1,0,'1511978400000','88018','mninety@gmail.com'),(8,'test15','23734cd52ad4a4fb877d8a1e26e5df5f','SFD','192.168.20.115',0,0,'1512496800000','88018','test15@gmail.com'),(9,'test2','23734cd52ad4a4fb877d8a1e26e5df5f','SFD','192.168.20.102',0,1,'1512669600000','88018','mninety@gmail.com'),(10,'test17','23734cd52ad4a4fb877d8a1e26e5df5f','SFD','192.168.20.117',0,1,'1513188000000','88018','test7@gmail.com');
UNLOCK TABLES;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

