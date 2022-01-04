-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: biteme
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `UserID` int NOT NULL,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `PhoneNumber` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Role` varchar(45) NOT NULL,
  `Status` varchar(45) NOT NULL,
  `BranchManagerID` int DEFAULT NULL,
  `Area` varchar(45) DEFAULT NULL,
  `isLoggedIn` varchar(45) DEFAULT '0',
  `Debt` float NOT NULL DEFAULT '0',
  `isBusiness` tinyint DEFAULT NULL,
  PRIMARY KEY (`UserName`),
  KEY `fk_from_BM_account_idx` (`BranchManagerID`,`Area`),
  CONSTRAINT `fk_from_BM_account` FOREIGN KEY (`BranchManagerID`, `Area`) REFERENCES `branch_manager` (`BranchManagerID`, `Area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'a','a','a','a','a','a','Branch Manager','active',1,'north','0',0,0),(2,'b','b','b','b','055','a@b','Client','active',1,'north','0',0,1),(2,'c','b','b','b','055','a@b','Supplier','active',1,'north','0',0,0),(2,'d','d','b','b','055','a@b','Supplier','active',1,'north','0',0,0),(2,'e','e','b','b','055','a@b','Not Assigned','active',1,'north','0',0,0),(2,'i','i','b','b','055','a@b','HR','active',1,'north','0',0,0),(2,'mosh','mosh','a','a','a','a','Branch Manager','active',2,'south','0',0,0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_manager`
--

DROP TABLE IF EXISTS `branch_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_manager` (
  `BranchManagerID` int NOT NULL,
  `Area` varchar(45) NOT NULL,
  PRIMARY KEY (`BranchManagerID`,`Area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_manager`
--

LOCK TABLES `branch_manager` WRITE;
/*!40000 ALTER TABLE `branch_manager` DISABLE KEYS */;
INSERT INTO `branch_manager` VALUES (1,'north'),(2,'south');
/*!40000 ALTER TABLE `branch_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_account`
--

DROP TABLE IF EXISTS `business_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_account` (
  `UserName` varchar(45) NOT NULL,
  `MonthlyBillingCeling` float NOT NULL,
  `isApproved` tinyint NOT NULL,
  `BusinessName` varchar(45) NOT NULL,
  `CurrentSpent` float DEFAULT NULL,
  `W4C` varchar(60) NOT NULL,
  PRIMARY KEY (`UserName`),
  KEY `fk_from_employees_BA_idx` (`BusinessName`),
  CONSTRAINT `fk_from_account_BA` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`),
  CONSTRAINT `fk_from_employees_BA` FOREIGN KEY (`BusinessName`) REFERENCES `employer` (`businessName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_account`
--

LOCK TABLES `business_account` WRITE;
/*!40000 ALTER TABLE `business_account` DISABLE KEYS */;
INSERT INTO `business_account` VALUES ('b',1000,1,'intel',0,'64dcf7fe7f35d0a11f62b2e1bfcdeec99faae9cdc737a7354b');
/*!40000 ALTER TABLE `business_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit`
--

DROP TABLE IF EXISTS `credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit` (
  `UserName` varchar(45) NOT NULL,
  `AmountInCredit` float NOT NULL DEFAULT '0',
  `RestaurantID` int NOT NULL,
  PRIMARY KEY (`UserName`,`RestaurantID`),
  KEY `fk_from_restaurant_credit_idx` (`RestaurantID`),
  CONSTRAINT `fk_from_account_credit` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`),
  CONSTRAINT `fk_from_restaurant_credit` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit`
--

LOCK TABLES `credit` WRITE;
/*!40000 ALTER TABLE `credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivered`
--

DROP TABLE IF EXISTS `delivered`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivered` (
  `OrderNum` int NOT NULL,
  `DatrNTime` varchar(45) NOT NULL,
  PRIMARY KEY (`OrderNum`),
  CONSTRAINT `fk_from_order_delivered` FOREIGN KEY (`OrderNum`) REFERENCES `order` (`OrderNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivered`
--

LOCK TABLES `delivered` WRITE;
/*!40000 ALTER TABLE `delivered` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employer` (
  `businessName` varchar(50) NOT NULL,
  `isApproved` tinyint NOT NULL,
  `HrName` varchar(45) DEFAULT NULL,
  `HrUserName` varchar(45) NOT NULL,
  `BranchManagerID` int NOT NULL,
  PRIMARY KEY (`businessName`),
  KEY `HrUserName_idx` (`HrUserName`),
  KEY `BranchManagerID_idx` (`BranchManagerID`),
  CONSTRAINT `BranchManagerID` FOREIGN KEY (`BranchManagerID`) REFERENCES `branch_manager` (`BranchManagerID`),
  CONSTRAINT `HrUserName` FOREIGN KEY (`HrUserName`) REFERENCES `account` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employer`
--

LOCK TABLES `employer` WRITE;
/*!40000 ALTER TABLE `employer` DISABLE KEYS */;
INSERT INTO `employer` VALUES ('intel',1,'John','i',1);
/*!40000 ALTER TABLE `employer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `ItemID` int NOT NULL AUTO_INCREMENT,
  `Category` varchar(50) NOT NULL,
  `SubCategory` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Price` float NOT NULL,
  `Ingredients` varchar(45) NOT NULL,
  `RestaurantID` int NOT NULL,
  `Image` varchar(45) DEFAULT NULL,
  `Description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`RestaurantID`,`Name`),
  KEY `fk_from_restaurant_item_idx` (`RestaurantID`),
  CONSTRAINT `fk_from_restaurant_item` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (8,'italian','pizza','Margaritta',34,'cheese, tomatoes, etc.',10,NULL,'The best pizza in the world'),(9,'italian','pasta','carbonara',34,'cheese, oil, macaroni, etc.',10,NULL,'The best pasta in the world');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_category`
--

DROP TABLE IF EXISTS `item_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_category` (
  `Category` varchar(45) NOT NULL,
  `SubCategory` varchar(45) NOT NULL,
  PRIMARY KEY (`Category`,`SubCategory`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_category`
--

LOCK TABLES `item_category` WRITE;
/*!40000 ALTER TABLE `item_category` DISABLE KEYS */;
INSERT INTO `item_category` VALUES ('italian','pasta'),('italian','pizza');
/*!40000 ALTER TABLE `item_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_menu`
--

DROP TABLE IF EXISTS `item_in_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_in_menu` (
  `ItemID` int NOT NULL,
  `RestaurantID` int NOT NULL,
  `MenuName` varchar(45) NOT NULL,
  `Course` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`RestaurantID`,`MenuName`),
  KEY `fk_from_restaurant_IIM_idx` (`RestaurantID`),
  KEY `fk_from_menu_IIM_idx` (`MenuName`),
  KEY `fk_from_menu_IIM_idx1` (`RestaurantID`,`MenuName`),
  CONSTRAINT `fk_from_item_IIM` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`),
  CONSTRAINT `fk_from_menu_IIM` FOREIGN KEY (`RestaurantID`, `MenuName`) REFERENCES `menu` (`RestaurantID`, `MenuName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_menu`
--

LOCK TABLES `item_in_menu` WRITE;
/*!40000 ALTER TABLE `item_in_menu` DISABLE KEYS */;
INSERT INTO `item_in_menu` VALUES (8,10,'Day','first'),(8,10,'Night','first'),(9,10,'Day','first'),(9,10,'Night','first');
/*!40000 ALTER TABLE `item_in_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_menu_in_order`
--

DROP TABLE IF EXISTS `item_in_menu_in_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_in_menu_in_order` (
  `OrderNum` int NOT NULL,
  `ItemID` int NOT NULL,
  `OptionalType` varchar(45) NOT NULL,
  `OptionalSpecify` varchar(45) NOT NULL,
  `Amount` int NOT NULL,
  PRIMARY KEY (`OrderNum`,`ItemID`,`OptionalType`,`OptionalSpecify`),
  KEY `fk_from_IIM_IIMIO_idx` (`ItemID`),
  KEY `fk_from_OC_IIMIO_idx` (`ItemID`,`OptionalType`),
  CONSTRAINT `fk_from_IIM_IIMIO` FOREIGN KEY (`ItemID`) REFERENCES `item_in_menu` (`ItemID`),
  CONSTRAINT `fk_from_OC_IIMIO` FOREIGN KEY (`ItemID`, `OptionalType`) REFERENCES `optional_category` (`ItemID`, `OptionalType`),
  CONSTRAINT `fk_from_order_IIMIO` FOREIGN KEY (`OrderNum`) REFERENCES `order` (`OrderNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_menu_in_order`
--

LOCK TABLES `item_in_menu_in_order` WRITE;
/*!40000 ALTER TABLE `item_in_menu_in_order` DISABLE KEYS */;
INSERT INTO `item_in_menu_in_order` VALUES (4,8,'size','XL',1),(5,8,'size','XL',1),(5,8,'tona','yes',1),(5,9,'size','XL',1),(5,9,'tona','yes',1),(6,8,'size','XL',1),(6,8,'tona','yes',1),(6,9,'size','XL',1),(6,9,'tona','yes',1),(7,8,'size','XL',1),(7,8,'tona','yes',1),(7,9,'size','XL',1),(7,9,'tona','yes',1),(8,8,'size','XL',1),(8,8,'tona','yes',1),(8,9,'size','XL',1),(8,9,'tona','yes',1),(9,8,'size','XL',1),(9,8,'tona','yes',1),(9,9,'size','XL',1),(9,9,'tona','yes',1),(10,8,'size','XL',1),(10,8,'tona','yes',1),(10,9,'size','XL',1),(10,9,'tona','yes',1),(11,8,'size','XL',1),(11,8,'tona','yes',1),(11,9,'size','XL',1),(11,9,'tona','yes',1),(12,8,'size','XL',1),(12,8,'tona','yes',1),(12,9,'size','XL',1),(12,9,'tona','yes',1),(13,8,'size','XL',1),(13,8,'tona','yes',1),(13,9,'size','XL',1),(13,9,'tona','yes',1),(14,8,'size','XL',1),(14,8,'tona','yes',1),(14,9,'size','XL',1),(14,9,'tona','yes',1),(15,8,'size','XL',1),(15,8,'tona','yes',1),(15,9,'size','XL',1),(15,9,'tona','yes',1),(16,8,'size','XL',1),(16,8,'tona','yes',1),(16,9,'size','XL',1),(16,9,'tona','yes',1);
/*!40000 ALTER TABLE `item_in_menu_in_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `RestaurantID` int NOT NULL,
  `MenuName` varchar(45) NOT NULL,
  PRIMARY KEY (`RestaurantID`,`MenuName`),
  CONSTRAINT `fk_from_restaurant_menu` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (10,'Day'),(10,'Night');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optional_category`
--

DROP TABLE IF EXISTS `optional_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optional_category` (
  `OptionalType` varchar(45) NOT NULL DEFAULT 'None',
  `Specify` varchar(45) DEFAULT 'None',
  `ItemID` int NOT NULL,
  `price` double DEFAULT '0',
  `isDuplicatable` tinyint DEFAULT NULL,
  PRIMARY KEY (`OptionalType`,`ItemID`),
  KEY `fk_from_item_OC_idx` (`ItemID`),
  CONSTRAINT `fk_from_item_OC` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optional_category`
--

LOCK TABLES `optional_category` WRITE;
/*!40000 ALTER TABLE `optional_category` DISABLE KEYS */;
INSERT INTO `optional_category` VALUES ('olives','yes',8,5,0),('size','XL',8,10,0),('size','XL',9,10,0),('tona','yes',8,5,0),('tona','yes',9,5,0);
/*!40000 ALTER TABLE `optional_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `OrderNum` int NOT NULL AUTO_INCREMENT,
  `RestaurantID` int NOT NULL,
  `RestaurantName` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  `OrderTime` varchar(45) NOT NULL,
  `PhoneNumber` varchar(45) NOT NULL,
  `TypeOfOrder` varchar(45) NOT NULL,
  `Discount_for_early_order` int DEFAULT NULL,
  `Check_out_price` double NOT NULL,
  `isApproved` tinyint NOT NULL DEFAULT '0',
  `required_time` varchar(45) NOT NULL,
  `approve_time` varchar(45) DEFAULT NULL,
  `hasArrived` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`OrderNum`,`UserName`),
  KEY `fk_from_restaurant_order_idx` (`RestaurantID`,`RestaurantName`),
  KEY `fk_from_account_order_idx` (`UserName`),
  CONSTRAINT `fk_from_account_order` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`),
  CONSTRAINT `fk_from_restaurant_order` FOREIGN KEY (`RestaurantID`, `RestaurantName`) REFERENCES `restaurant` (`RestaurantNum`, `RestaurantName`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(2,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(3,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(4,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(5,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(6,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(7,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(8,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(9,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(10,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(11,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(12,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(13,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(14,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(15,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0),(16,10,'piz','b','2021-12-31 11:30','055','delivery',0,52.9,0,'2021-12-31 15:30',NULL,0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_in_shippment`
--

DROP TABLE IF EXISTS `orders_in_shippment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_in_shippment` (
  `ShippmentID` int NOT NULL,
  `OrderNum` int NOT NULL,
  `UserName` varchar(45) NOT NULL,
  `Price` float NOT NULL,
  PRIMARY KEY (`ShippmentID`),
  KEY `fk_from_order_OIS_idx` (`OrderNum`,`UserName`),
  CONSTRAINT `fk_from_order_OIS` FOREIGN KEY (`OrderNum`, `UserName`) REFERENCES `order` (`OrderNum`, `UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_in_shippment`
--

LOCK TABLES `orders_in_shippment` WRITE;
/*!40000 ALTER TABLE `orders_in_shippment` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_in_shippment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_account`
--

DROP TABLE IF EXISTS `private_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_account` (
  `UserName` varchar(50) NOT NULL,
  `CreditCardNumber` varchar(16) DEFAULT NULL,
  `CreditCardCVV` varchar(3) DEFAULT NULL,
  `CreditCardExp` varchar(10) DEFAULT NULL,
  `W4C` varchar(60) NOT NULL,
  PRIMARY KEY (`UserName`),
  CONSTRAINT `fk_from_account_PA` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_account`
--

LOCK TABLES `private_account` WRITE;
/*!40000 ALTER TABLE `private_account` DISABLE KEYS */;
INSERT INTO `private_account` VALUES ('b','1234567891234567','123','12-2026','2463daba4a677101b6c6968134744ce7dffb1d76d58fc8265b');
/*!40000 ALTER TABLE `private_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports_per_branch`
--

DROP TABLE IF EXISTS `reports_per_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports_per_branch` (
  `RestaurantID` int NOT NULL,
  `Type_of_report` varchar(45) NOT NULL,
  `Month_of_the_report` int NOT NULL,
  `report_data` varchar(45) DEFAULT NULL,
  `BranchManagerID` int DEFAULT NULL,
  PRIMARY KEY (`RestaurantID`,`Month_of_the_report`,`Type_of_report`),
  KEY `fk_from_branch_manager_RPB_idx` (`BranchManagerID`),
  CONSTRAINT `fk_from_branch_manager_RPB` FOREIGN KEY (`BranchManagerID`) REFERENCES `branch_manager` (`BranchManagerID`),
  CONSTRAINT `fk_from_restaurant_RPB` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports_per_branch`
--

LOCK TABLES `reports_per_branch` WRITE;
/*!40000 ALTER TABLE `reports_per_branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports_per_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `RestaurantNum` int NOT NULL AUTO_INCREMENT,
  `RestaurantName` varchar(45) NOT NULL,
  `IsApproved` tinyint NOT NULL,
  `BranchManagerID` int NOT NULL,
  `Area` varchar(45) NOT NULL,
  `Image` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  `Type` varchar(45) NOT NULL,
  `Address` varchar(45) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RestaurantNum`,`RestaurantName`,`UserName`),
  KEY `fk_from_account_restaurant_idx` (`UserName`),
  KEY `fk_from_BMt_restaurant_idx` (`BranchManagerID`,`Area`),
  CONSTRAINT `fk_from_account_restaurant` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`),
  CONSTRAINT `fk_from_BMt_restaurant` FOREIGN KEY (`BranchManagerID`, `Area`) REFERENCES `branch_manager` (`BranchManagerID`, `Area`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'a',1,1,'north','v','c','suchi','asdasd','asdasd'),(2,'steak',1,1,'north',NULL,'d','shushi','raines','this is carnivors restu'),(8,'moshi',1,2,'south',NULL,'d','shushi','raines','Only fish'),(9,'asd',1,2,'south',NULL,'d','shushi','raines','Only vegen'),(10,'piz',1,2,'south',NULL,'d','pizza','raines','Only potatoes');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `ShippmentID` int NOT NULL AUTO_INCREMENT,
  `WorkPlace` varchar(45) NOT NULL,
  `Address` varchar(45) NOT NULL,
  `reciever_name` varchar(45) NOT NULL,
  `reciever_phone_number` varchar(45) NOT NULL,
  `deliveryType` varchar(45) NOT NULL,
  `OrderNum` int NOT NULL,
  PRIMARY KEY (`ShippmentID`),
  KEY `OrderNum_idx` (`OrderNum`),
  CONSTRAINT `OrderNum` FOREIGN KEY (`OrderNum`) REFERENCES `order` (`OrderNum`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `w4c`
--

DROP TABLE IF EXISTS `w4c`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `w4c` (
  `W4C` varchar(5) NOT NULL,
  `UserName` varchar(45) NOT NULL,
  PRIMARY KEY (`W4C`,`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `w4c`
--

LOCK TABLES `w4c` WRITE;
/*!40000 ALTER TABLE `w4c` DISABLE KEYS */;
/*!40000 ALTER TABLE `w4c` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-31 14:10:35