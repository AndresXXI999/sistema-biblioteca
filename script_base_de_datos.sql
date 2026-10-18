-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: biblioteca_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `multa_por_dia` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES (1,10);
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `autor` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `disponible` bit(1) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES (1,'Gabriel García Márquez','Novela',_binary '','978-0307474728','Cien años de soledad'),(2,'Antoine de Saint-Exupéry','Infantil',_binary '','978-0156012195','El principito'),(3,'Miguel de Cervantes','Clásico',_binary '','978-0060934347','Don Quijote de la Mancha'),(4,'George Orwell','Distopía',_binary '','978-0451524935','1984'),(5,'Thomas H. Cormen','Académico',_binary '','978-0262033848','Introducción a los algoritmos');
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_devolucion` date DEFAULT NULL,
  `fecha_limite` date DEFAULT NULL,
  `fecha_salida` date DEFAULT NULL,
  `multa` double NOT NULL,
  `multa_pagada` bit(1) NOT NULL,
  `libro_id` bigint DEFAULT NULL,
  `socio_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk7uwwn2ov4su2plcn1jh4dbi0` (`libro_id`),
  KEY `FKnnfrndxihs48ch9j7t7y6t9to` (`socio_id`),
  CONSTRAINT `FKk7uwwn2ov4su2plcn1jh4dbi0` FOREIGN KEY (`libro_id`) REFERENCES `libro` (`id`),
  CONSTRAINT `FKnnfrndxihs48ch9j7t7y6t9to` FOREIGN KEY (`socio_id`) REFERENCES `socio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (1,'2026-10-17','2026-10-24','2026-10-17',0,_binary '\0',1,1),(2,'2026-10-17','2026-10-01','2026-10-17',160,_binary '',4,2);
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socio`
--

DROP TABLE IF EXISTS `socio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `socio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `tipo` enum('DOCENTE','ESTUDIANTE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7wukeqbj89rnl7e0dd0kvsb10` (`usuario_id`),
  CONSTRAINT `FKfdj8erxkmnd66pjnkf54wajeh` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socio`
--

LOCK TABLES `socio` WRITE;
/*!40000 ALTER TABLE `socio` DISABLE KEYS */;
INSERT INTO `socio` VALUES (1,'Socio Prueba',2,'socio1@correo.com','5555-0001','ESTUDIANTE'),(2,'norman dia',3,'socio2@nose.com','12345678','ESTUDIANTE');
/*!40000 ALTER TABLE `socio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `rol` enum('ADMIN','SOCIO') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin123','ADMIN','admin'),(2,'socio123','SOCIO','socio1'),(3,'socio2','SOCIO','socio2');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-18  1:57:22
