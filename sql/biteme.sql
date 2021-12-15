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
  `AccountID` int NOT NULL,
  `UserName` varchar(10) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `FirstName` varchar(10) NOT NULL,
  `LastName` varchar(10) NOT NULL,
  `PhoneNumber` varchar(11) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Type` varchar(10) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `BranchManagerID` int DEFAULT NULL,
  `Area` varchar(10) DEFAULT NULL,
  `isLoggedIn` tinyint DEFAULT '0',
  `Debt` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`AccountID`,`UserName`),
  KEY `fk_from_BM_account_idx` (`BranchManagerID`),
  CONSTRAINT `fk_from_BM_account` FOREIGN KEY (`BranchManagerID`) REFERENCES `branch_manager` (`AccountID`)
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
  `AccountID` int NOT NULL,
  `Area` varchar(10) NOT NULL,
  PRIMARY KEY (`AccountID`,`Area`),
  CONSTRAINT `fk_from_account_BM` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`)
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
  `AccountID` int NOT NULL,
  `MonthBillingCeiling` float NOT NULL,
  `isApproved` tinyint NOT NULL,
  `BusinessName` varchar(10) NOT NULL,
  `CurrentSpent` float DEFAULT NULL,
  `W4C` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AccountID`),
  KEY `fk_from_employees_business_account_idx` (`BusinessName`),
  CONSTRAINT `fk_from_account_business_account` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_employees_business_account` FOREIGN KEY (`BusinessName`) REFERENCES `employees` (`Name`)
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
  `Account ID` int NOT NULL,
  `AmountInCredit` int NOT NULL,
  `RestaurantID` int NOT NULL,
  PRIMARY KEY (`Account ID`,`RestaurantID`),
  KEY `fk_from_restaurants_credit_idx` (`RestaurantID`),
  CONSTRAINT `fk_from_account_credit` FOREIGN KEY (`Account ID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_restaurants_credit` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
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
  `DateNTime` varchar(50) NOT NULL,
  PRIMARY KEY (`OrderNum`)
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
  `Name` varchar(10) NOT NULL,
  `isApproved` tinyint NOT NULL,
  `W4C` varchar(50) NOT NULL,
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
  `Category` varchar(10) NOT NULL,
  `SubCategory` varchar(45) NOT NULL,
  `Name` varchar(10) NOT NULL,
  `Price` float NOT NULL,
  `ItemID` int NOT NULL,
  `Ingredients` varchar(200) NOT NULL,
  `RestaurantID` int NOT NULL,
  `Image` varbinary(10000) DEFAULT NULL,
  `Description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`RestaurantID`),
  KEY `fk_from_restaurant_item_idx` (`RestaurantID`),
  KEY `fk_from_item_category_idx` (`Category`,`SubCategory`),
  CONSTRAINT `fk_from_item_category` FOREIGN KEY (`Category`, `SubCategory`) REFERENCES `item_category` (`Category`, `SubCategory`),
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
  `Category` varchar(10) NOT NULL,
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
  `MenuName` varchar(10) DEFAULT NULL,
  `Course` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ItemID`,`RestaurantID`),
  KEY `fk_from_menu_itemmenu_idx` (`MenuName`),
  KEY `fk_from_menu_itemmenu_idx1` (`RestaurantID`,`MenuName`),
  CONSTRAINT `fk_from_item_itemmenu` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`),
  CONSTRAINT `fk_from_menu_itemmenu` FOREIGN KEY (`RestaurantID`, `MenuName`) REFERENCES `menu` (`RestaurantID`, `MenuName`)
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
  `Item_name` varchar(10) NOT NULL,
  `OptionalType` varchar(10) NOT NULL,
  `OptionalSpecify` varchar(10) NOT NULL,
  `Amount` int NOT NULL,
  PRIMARY KEY (`OrderNum`,`ItemID`,`OptionalType`,`OptionalSpecify`),
  KEY `fk_item in menu_idx` (`ItemID`),
  KEY `fk_optional_idx` (`OptionalType`),
  CONSTRAINT `fk_from_iteminmenu_IIMIO` FOREIGN KEY (`ItemID`) REFERENCES `item_in_menu` (`ItemID`),
  CONSTRAINT `fk_from_optional_IIMIO` FOREIGN KEY (`OptionalType`) REFERENCES `optional_category` (`OptionalType`),
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
  `MenuName` varchar(10) NOT NULL,
  PRIMARY KEY (`RestaurantID`,`MenuName`),
  CONSTRAINT `fk_from_restaurant_menu` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `OptionalType` varchar(10) NOT NULL,
  `Specify` varchar(10) DEFAULT NULL,
  `itemID` int NOT NULL,
  PRIMARY KEY (`OptionalType`,`itemID`),
  KEY `fk_from_item_OC_idx` (`itemID`),
  CONSTRAINT `fk_from_item_OC` FOREIGN KEY (`itemID`) REFERENCES `item` (`ItemID`)
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
  `ResturantName` varchar(10) DEFAULT NULL,
  `AccountID` int NOT NULL,
  `OrderTime` varchar(50) NOT NULL,
  `PhoneNumber` varchar(11) NOT NULL,
  `TypeOfOrder` varchar(10) NOT NULL,
  `Discount_for_early_order` int DEFAULT NULL,
  `Check_out_price` float NOT NULL,
  `isBusiness` tinyint NOT NULL,
  `required_time` varchar(10) NOT NULL,
  PRIMARY KEY (`OrderNum`),
  KEY `fk_account_idx` (`AccountID`),
  KEY `fk_restaurantID_idx` (`RestaurantID`),
  CONSTRAINT `fk_from_account_order` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_restaurantID_order` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_in_shipment`
--

DROP TABLE IF EXISTS `orders_in_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_in_shipment` (
  `ShipmentID` int NOT NULL,
  `orderID` int NOT NULL,
  `AccountID` int NOT NULL,
  `Price` float NOT NULL,
  KEY `fk_from_order_idx` (`orderID`),
  KEY `fk_from_account_idx` (`AccountID`),
  KEY `fk_from_ship_OIS_idx` (`ShipmentID`),
  CONSTRAINT `fk_from_account_OIS` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_order_OIS` FOREIGN KEY (`orderID`) REFERENCES `order` (`OrderNum`),
  CONSTRAINT `fk_from_ship_OIS` FOREIGN KEY (`ShipmentID`) REFERENCES `shipment` (`ShipmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_in_shipment`
--

LOCK TABLES `orders_in_shipment` WRITE;
/*!40000 ALTER TABLE `orders_in_shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_in_shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_account`
--

DROP TABLE IF EXISTS `private_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_account` (
  `AccountID` int NOT NULL,
  `CreditCardNumber` varchar(16) NOT NULL,
  `CreditCardCVV` varchar(4) NOT NULL,
  `CreditCardExpDate` varchar(5) NOT NULL,
  `W4C` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AccountID`),
  CONSTRAINT `fk_from_account_private_account` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`)
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
-- Table structure for table `qr`
--

DROP TABLE IF EXISTS `qr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qr` (
  `QR code` varchar(100) NOT NULL,
  `UserName` varchar(10) NOT NULL,
  PRIMARY KEY (`QR code`),
  KEY `fk_users_idx` (`UserName`),
  CONSTRAINT `fk_from_users_qr` FOREIGN KEY (`UserName`) REFERENCES `users` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qr`
--

LOCK TABLES `qr` WRITE;
/*!40000 ALTER TABLE `qr` DISABLE KEYS */;
/*!40000 ALTER TABLE `qr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports_per_branch`
--

DROP TABLE IF EXISTS `reports_per_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports_per_branch` (
  `RestaurantID` int NOT NULL,
  `Type_of_report` int DEFAULT NULL,
  `Month_of_the_report` varchar(10) DEFAULT NULL,
  `Report_data` json DEFAULT NULL,
  PRIMARY KEY (`RestaurantID`),
  CONSTRAINT `fk_from_restaurant_reports` FOREIGN KEY (`RestaurantID`) REFERENCES `restaurant` (`RestaurantNum`)
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
  `RestaurantNum` int NOT NULL,
  `RestaurantName` varchar(10) NOT NULL,
  `IsApproved` tinyint NOT NULL,
  `BranchManagerID` int NOT NULL,
  `Area` varchar(10) NOT NULL,
  `Image` varbinary(10000) DEFAULT NULL,
  `AccountID` int NOT NULL,
  `Type` varchar(10) NOT NULL,
  PRIMARY KEY (`RestaurantNum`,`RestaurantName`),
  KEY `fk_from_branchmanager_restaurant_idx` (`BranchManagerID`),
  KEY `fk_from_account_supplier_idx` (`AccountID`),
  CONSTRAINT `fk_from_account_restaurant` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_account_supplier` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`),
  CONSTRAINT `fk_from_branchmanager_restaurant` FOREIGN KEY (`BranchManagerID`) REFERENCES `branch_manager` (`AccountID`)
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
  `ShipmentID` int NOT NULL AUTO_INCREMENT,
  `workPlace` varchar(50) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `reciever_name` varchar(50) NOT NULL,
  `reciever_phone_number` varchar(50) NOT NULL,
  `deliveryType` varchar(10) NOT NULL,
  PRIMARY KEY (`ShipmentID`),
  KEY `fk_from_employees_idx` (`workPlace`),
  CONSTRAINT `fk_from_employees_shipment` FOREIGN KEY (`workPlace`) REFERENCES `employees` (`Name`)
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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserName` varchar(10) NOT NULL,
  `Password` varchar(10) NOT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-15 11:37:23
