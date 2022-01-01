-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: biteme
-- ------------------------------------------------------
-- Server version	8.0.27

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
  `W4C` varchar(50) NOT NULL,
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
  PRIMARY KEY (`UserName`),
  KEY `fk_from_employees_BA_idx` (`BusinessName`),
  CONSTRAINT `fk_from_account_BA` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`),
  CONSTRAINT `fk_from_employees_BA` FOREIGN KEY (`BusinessName`) REFERENCES `employees` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_account`
--

LOCK TABLES `business_account` WRITE;
/*!40000 ALTER TABLE `business_account` DISABLE KEYS */;
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
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `Name` varchar(50) NOT NULL,
  `isApproved` tinyint NOT NULL,
  `Hr_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
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
  `MenuName` varchar(45) DEFAULT NULL,
  `Course` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`RestaurantID`),
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
  `Price` double DEFAULT NULL,
  PRIMARY KEY (`OptionalType`,`ItemID`),
  KEY `fk_from_item_OC_idx` (`ItemID`),
  CONSTRAINT `fk_from_item_OC` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optional_category`
--

LOCK TABLES `optional_category` WRITE;
/*!40000 ALTER TABLE `optional_category` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
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
  PRIMARY KEY (`UserName`),
  CONSTRAINT `fk_from_account_PA` FOREIGN KEY (`UserName`) REFERENCES `account` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_account`
--

LOCK TABLES `private_account` WRITE;
/*!40000 ALTER TABLE `private_account` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `ShippmentID` int NOT NULL,
  `WorkPlace` varchar(45) NOT NULL,
  `Address` varchar(45) NOT NULL,
  `reciever_name` varchar(45) NOT NULL,
  `reciever_phone_number` varchar(45) NOT NULL,
  `deliveryType` varchar(45) NOT NULL,
  PRIMARY KEY (`ShippmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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

-- Dump completed on 2021-12-19 22:52:57
