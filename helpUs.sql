-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (arm64)
--
-- Host: 127.0.0.1    Database: helpUs
-- ------------------------------------------------------
-- Server version	8.0.28

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

CREATE DATABASE  IF NOT EXISTS `helpUs`;
USE `helpUs`;
--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `commentID` int NOT NULL,
  `body` text NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleteState` tinyint(1) NOT NULL DEFAULT '0',
  `userID` int NOT NULL,
  `templateID` int NOT NULL,
  PRIMARY KEY (`commentID`),
  UNIQUE KEY `comments_comentID_uindex` (`commentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (25,'{\"time\":1653815535957,\"blocks\":[{\"id\":\"WucNVYWadD\",\"type\":\"header\",\"data\":{\"text\":\"LASCIO UN COMMENTO BREVE.\",\"level\":2}},{\"id\":\"KiV8qTWy7I\",\"type\":\"paragraph\",\"data\":{\"text\":\"Otherwise, a&nbsp;DateTimeParseException&nbsp;will be thrown at runtime.\"}},{\"id\":\"NDYru7ZlQK\",\"type\":\"paragraph\",\"data\":{\"text\":\"In our first example, let\'s convert a&nbsp;String&nbsp;to a&nbsp;java.time.LocalDate:\"}},{\"id\":\"tDfq-vkci-\",\"type\":\"raw\",\"data\":{\"html\":\"LocalDate date = LocalDate.parse(\\\"2018-05-05\\\");\\n\"}},{\"id\":\"gNI13-Dw8e\",\"type\":\"paragraph\",\"data\":{\"text\":\"It is important to note that both the LocalDate and LocalDateTime objects are timezone agnostic. <b>However, when we need to deal with time zone specific dates and times,</b> we can use the ZonedDateTime parse method directly to get a time zone specific date time:\"}},{\"id\":\"lvuBqqCCNr\",\"type\":\"raw\",\"data\":{\"html\":\"DateTimeFormatter formatter = DateTimeFormatter.ofPattern(\\\"yyyy-MM-dd HH:mm:ss z\\\");\\nZonedDateTime zonedDateTime = ZonedDateTime.parse(\\\"2015-05-05 10:15:30 Europe/Paris\\\", formatter);\"}}],\"version\":\"2.24.3\"}','2022-05-29 11:12:16',0,5,20),(26,'{\"time\":1653819376609,\"blocks\":[{\"id\":\"zp4gDQzl6l\",\"type\":\"header\",\"data\":{\"text\":\"Molto interesante ...\",\"level\":2}}],\"version\":\"2.24.3\"}','2022-05-29 12:16:16',0,8,19),(27,'{\"time\":1653822706108,\"blocks\":[{\"id\":\"DmSBtfojg-\",\"type\":\"header\",\"data\":{\"text\":\"Buona.\",\"level\":2}}],\"version\":\"2.24.3\"}','2022-05-29 13:11:46',0,9,20);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `counter`
--

DROP TABLE IF EXISTS `counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `counter` (
  `counter_id` varchar(30) NOT NULL,
  `counter_value` int NOT NULL,
  PRIMARY KEY (`counter_id`),
  UNIQUE KEY `counter_counter_id_uindex` (`counter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `counter`
--

LOCK TABLES `counter` WRITE;
/*!40000 ALTER TABLE `counter` DISABLE KEYS */;
INSERT INTO `counter` VALUES ('commentCounterId',27),('noteCounterId',19),('prenotazioneCounterId',5),('templateCounterId',21),('userCounterId',9);
/*!40000 ALTER TABLE `counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Likes`
--

DROP TABLE IF EXISTS `Likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Likes` (
  `userID` int NOT NULL,
  `classType` varchar(100) NOT NULL,
  `valueID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Likes`
--

LOCK TABLES `Likes` WRITE;
/*!40000 ALTER TABLE `Likes` DISABLE KEYS */;
INSERT INTO `Likes` VALUES (5,'Template',16),(5,'Template',19),(5,'Template',20),(2,'Template',20),(2,'Template',19),(6,'Template',20),(6,'Template',19),(7,'Template',20),(8,'Template',19),(8,'Template',20),(9,'Template',20),(9,'Template',19),(9,'Template',21),(5,'Template',21);
/*!40000 ALTER TABLE `Likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `noteID` int NOT NULL,
  `userID` int NOT NULL,
  `templateID` int NOT NULL,
  `noteValue` int NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`noteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (5,2,20,4,'2022-05-27 15:13:01'),(7,2,19,4,'2022-05-27 15:13:33'),(9,5,20,5,'2022-05-27 15:21:16'),(10,5,19,4,'2022-05-27 15:40:57'),(11,6,20,5,'2022-05-27 15:50:25'),(12,6,19,4,'2022-05-27 23:45:46'),(13,7,20,4,'2022-05-29 12:12:16'),(15,8,19,5,'2022-05-29 12:16:35'),(17,9,20,3,'2022-05-29 13:11:18'),(18,9,21,5,'2022-05-29 13:22:15'),(19,5,21,4,'2022-05-29 13:23:02');
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotazioni`
--

DROP TABLE IF EXISTS `prenotazioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazioni` (
  `prenotazioneID` int NOT NULL,
  `mittenteID` int NOT NULL,
  `destinatarioID` int NOT NULL,
  `oggetto` varchar(500) NOT NULL,
  `body` text NOT NULL,
  `dataOra` datetime NOT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `viaIncontro` varchar(500) NOT NULL,
  `templateID` int NOT NULL,
  `deleteState` tinyint(1) NOT NULL DEFAULT '0',
  `accepted` tinyint(1) NOT NULL DEFAULT '0',
  `link` text,
  `response` text,
  PRIMARY KEY (`prenotazioneID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazioni`
--

LOCK TABLES `prenotazioni` WRITE;
/*!40000 ALTER TABLE `prenotazioni` DISABLE KEYS */;
INSERT INTO `prenotazioni` VALUES (2,6,5,'RICHIESTA AUITO NON CAPISCO NIENTE','{\"time\":1653732648413,\"blocks\":[{\"id\":\"OLrF2AmEzN\",\"type\":\"header\",\"data\":{\"text\":\"Format dati .\",\"level\":2}},{\"id\":\"1ZdbyqBAd2\",\"type\":\"paragraph\",\"data\":{\"text\":\"I just remember there is another way to pretty print&nbsp;<a href=\\\"http://java67.blogspot.sg/2012/10/best-way-to-convert-numbers-to-string-in-java-example.html\\\" style=\\\"text-decoration-line: none; color: rgb(153, 51, 0); font-family: Arial, Tahoma, Helvetica, FreeSans, sans-serif; font-size: 14px;\\\">floating-point number</a>&nbsp;in Java, by using the&nbsp;setMaximumFractionDigits(int places)&nbsp;method from&nbsp;NumberFormat&nbsp;class. you can just pass the number of digits you want to keep decimals, for example, to format a number up to&nbsp;4&nbsp;decimal places, pass&nbsp;4. Here is a quick example of this :Read more:&nbsp;<a href=\\\"https://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz7UWtXefQr\\\" style=\\\"text-decoration-line: none; color: rgb(0, 51, 153);\\\">https://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz7UWtXefQr</a>\"}},{\"id\":\"dsZjUVBo2h\",\"type\":\"raw\",\"data\":{\"html\":\"double simple = 4.0099; double round = 4.9999; NumberFormat nf = NumberFormat.getInstance(); nf.setMaximumFractionDigits(3); System.out.println(nf.format(simple)); // prints 4.01 System.out.println(nf.format(round)); // prints 5\\n\\nRead more: https://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz7UWtXefQr\"}},{\"id\":\"NT6gEHeI6y\",\"type\":\"list\",\"data\":{\"style\":\"unordered\",\"items\":[\"By using the String&nbsp;format()&nbsp;method\"]}},{\"id\":\"65DDVqkt4z\",\"type\":\"list\",\"data\":{\"style\":\"unordered\",\"items\":[\"By using the DecimalFormat&nbsp;format() method\"]}},{\"id\":\"rZi7Y0MuXn\",\"type\":\"list\",\"data\":{\"style\":\"unordered\",\"items\":[\"By using&nbsp;printf()&nbsp;method&nbsp;\"]}},{\"id\":\"Ly2ydK_0UC\",\"type\":\"list\",\"data\":{\"style\":\"unordered\",\"items\":[\"By using Formatter\'s&nbsp;format()&nbsp;method\"]}},{\"id\":\"OWlEyepDhh\",\"type\":\"list\",\"data\":{\"style\":\"unordered\",\"items\":[\"By using&nbsp;setMaximumFractionDigits()&nbsp;of&nbsp;NumberFormat&nbsp;class\"]}},{\"id\":\"HAeNBALL2R\",\"type\":\"paragraph\",\"data\":{\"text\":\"Read more:&nbsp;<a href=\\\"https://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz7UZlw8eZA\\\" style=\\\"text-decoration: none; color: rgb(0, 51, 153);\\\">https://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz7UZlw8eZA</a>\"}}],\"version\":\"2.24.3\"}','2022-05-28 12:10:00','2022-05-28 12:10:48','Zoom',19,0,0,NULL,NULL),(3,6,5,'sdfdòojsfdlk','{\"time\":1653738461216,\"blocks\":[{\"id\":\"jYLHfu9WFX\",\"type\":\"paragraph\",\"data\":{\"text\":\"fdgdbòojòoi\"}},{\"id\":\"1vi_IVWc59\",\"type\":\"paragraph\",\"data\":{\"text\":\"lkjbjk\"}}],\"version\":\"2.24.3\"}','2022-05-01 15:13:00','2022-05-28 12:13:37','Google meet',20,0,1,'http://localhost:8080/progettoSistemiWeb_war_exploded/Dispatcher','merci beaucoup'),(4,8,5,'non capisco tutto','{\"time\":1653819566122,\"blocks\":[{\"id\":\"1w_RVdHNu0\",\"type\":\"header\",\"data\":{\"text\":\"NON CAPISCO IL PRINCIPIO FONDAMENTALE DELL\'USO DI QUESTA APPLICAZIONE.\",\"level\":2}},{\"id\":\"UO_Q2xg5Kl\",\"type\":\"paragraph\",\"data\":{\"text\":\"sono una appazionata di programmazione e vorrei saperne di piu rispetto a questo argomento.\"}}],\"version\":\"2.24.3\"}','2022-05-08 12:17:00','2022-05-29 12:19:26','Google meet',19,0,1,'http://localhost:8080/progettoSistemiWeb_war_exploded/Dispatcher','sono una appazionata di programmazione e vorrei saperne di piu rispetto a questo argomento.');
/*!40000 ALTER TABLE `prenotazioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `templates`
--

DROP TABLE IF EXISTS `templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `templates` (
  `templateID` int NOT NULL,
  `userID` int NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` longtext NOT NULL,
  `code` varchar(5000) NOT NULL,
  `language` varchar(100) NOT NULL,
  `deleteState` tinyint(1) DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `link` text,
  PRIMARY KEY (`templateID`),
  UNIQUE KEY `templates_templateID_uindex` (`templateID`),
  UNIQUE KEY `templates_titre_uindex` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templates`
--

LOCK TABLES `templates` WRITE;
/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` VALUES (19,5,'Editor.js','Deserunt dicta sed rerum asperiores non est mollit et aperiam maiores laborum qui quis veritatis et ratione\r\n                    \r\n                    ','{\"time\":1653594323214,\"blocks\":[{\"id\":\"YDEZ-GxdTO\",\"type\":\"header\",\"data\":{\"text\":\"<a href=\\\"https://editorjs.io/configuration#passing-saved-data\\\" style=\\\"background-color: transparent; text-decoration: none !important; border: 0px; padding-bottom: 1px; color: inherit !important;\\\">Passing saved data</a>\",\"level\":2}},{\"id\":\"6LUerykrEP\",\"type\":\"paragraph\",\"data\":{\"text\":\"To initialize the Editor with previously saved data, pass it through the&nbsp;<code class=\\\"inline-code\\\">data</code>&nbsp;property:\"}},{\"id\":\"aQ4JH18rBz\",\"type\":\"code\",\"data\":{\"code\":\"var editor = new EditorJS();\\n\\neditor.isReady\\n  .then(() => {\\n    console.log(\'Editor.js is ready to work!\')\\n    /** Do anything you need after editor initialization */\\n  })\\n  .catch((reason) => {\\n    console.log(`Editor.js initialization failed because of ${reason}`)\\n  });\"}},{\"id\":\"JPgPDJRXPU\",\"type\":\"paragraph\",\"data\":{\"text\":\"Format of the&nbsp;<code class=\\\"inline-code\\\">data</code>&nbsp;object should be the same as returned by Editor saving.\"}},{\"id\":\"XUzeqkQTO5\",\"type\":\"header\",\"data\":{\"text\":\"<a href=\\\"https://editorjs.io/configuration#editor-ready-callback\\\" style=\\\"background-color: transparent; text-decoration: none !important; border: 0px; padding-bottom: 1px; color: inherit !important;\\\">Editor ready callback</a>\",\"level\":2}},{\"id\":\"3kRgO-v1An\",\"type\":\"paragraph\",\"data\":{\"text\":\"Editor.js needs a bit time to initialize. It is an asynchronous action so it won\'t block execution of your main script.\"}},{\"id\":\"1NE0OJYL-E\",\"type\":\"paragraph\",\"data\":{\"text\":\"If you need to know when editor instance is ready you can use one of the following ways:\"}},{\"id\":\"TQgUE2mSGo\",\"type\":\"header\",\"data\":{\"text\":\"<a href=\\\"https://editorjs.io/configuration#pass-onready-property-to-the-configuration-object\\\" style=\\\"background-color: transparent; text-decoration: none !important; border: 0px; padding-bottom: 1px; color: inherit !important;\\\">Pass&nbsp;onReady&nbsp;property to the configuration object</a>\",\"level\":4}},{\"id\":\"TnVdlqNTif\",\"type\":\"paragraph\",\"data\":{\"text\":\"It must be a function:\"}},{\"id\":\"FognUL-mwd\",\"type\":\"raw\",\"data\":{\"html\":\"var editor = new EditorJS({ \\n// Other configuration properties /** * onReady callback */ \\nonReady: () => { console.log(\'Editor.js is ready to work!\')\\n}});\"}},{\"id\":\"2QCuNmiZNk\",\"type\":\"header\",\"data\":{\"text\":\"<a href=\\\"https://editorjs.io/configuration#use-isready-promise\\\" style=\\\"background-color: transparent; text-decoration: none !important; border: 0px; padding-bottom: 1px; color: inherit !important;\\\">Use&nbsp;isReady&nbsp;promise</a>\",\"level\":4}},{\"id\":\"Y7qoBHwWmy\",\"type\":\"paragraph\",\"data\":{\"text\":\"After you create a new&nbsp;<code><code class=\\\"inline-code\\\">EditorJS</code></code>&nbsp;object, it will contain&nbsp;<code><code class=\\\"inline-code\\\">isReady</code></code>&nbsp;property. It is a Promise object that will be resolved when the Editor is ready for work and rejected otherwise. If there is an error during initialization the&nbsp;<code><code class=\\\"inline-code\\\">isReady</code></code>&nbsp;promise will be rejected with an error message.\"}},{\"id\":\"N8RwNg3Wbm\",\"type\":\"raw\",\"data\":{\"html\":\"var editor = new EditorJS();\\n\\ntry {\\n  await editor.isReady;\\n  console.log(\'Editor.js is ready to work!\')\\n  /** Do anything you need after editor initialization */\\n} catch (reason) {\\n  console.log(`Editor.js initialization failed because of ${reason}`)\\n}\"}},{\"id\":\"qkopLROo31\",\"type\":\"paragraph\",\"data\":{\"text\":\"You can use&nbsp;<code><code class=\\\"inline-code\\\">async/await</code></code>&nbsp;to keep your code looking more clear:\"}},{\"id\":\"c-yfHp1YX3\",\"type\":\"code\",\"data\":{\"code\":\"var editor = new EditorJS();\\n\\ntry {\\n  await editor.isReady;\\n  console.log(\'Editor.js is ready to work!\')\\n  /** Do anything you need after editor initialization */\\n} catch (reason) {\\n  console.log(`Editor.js initialization failed because of ${reason}`)\\n}\"}}],\"version\":\"2.24.3\"}','nextJs',0,'2022-05-26 15:13:21',NULL),(20,5,'Vue JS','After you create a new EditorJS object, it will contain isReady property. It is a Promise object that will be resolved when the Editor is ready for work and rejected otherwise. If there is an error during initialization the isReady promise will be rejected with an error message.\r\n                    ','{\"time\":1653815285255,\"blocks\":[{\"id\":\"UEECnuCxfV\",\"type\":\"paragraph\",\"data\":{\"text\":\"After you create a new <code class=\\\"inline-code\\\">EditorJS</code> object, it will contain isReady property. It is a Promise object that will be resolved when the Editor is ready for work and rejected otherwise. If there is an error during initialization the <code class=\\\"inline-code\\\">isReady</code> promise will be rejected with an error message.\"}},{\"id\":\"hfXjTzE5Xd\",\"type\":\"header\",\"data\":{\"text\":\"bro casting\",\"level\":2}},{\"id\":\"oCUsixkZNq\",\"type\":\"raw\",\"data\":{\"html\":\"var editor = new EditorJS({\\n   // Other configuration properties\\n\\n   /**\\n    * onReady callback\\n    */\\n   onReady: () => {console.log(\'Editor.js is ready to work!\')},\\n   \\n   /**\\n    * onChange callback\\n    */\\n   onChange: (api, event) => {\\n     console.log(\'Now I know that Editor\\\\\'s content changed!\', event)\\n   }\\n});\"}}],\"version\":\"2.24.3\"}','others',0,'2022-05-26 22:24:26','https://www.youtube.com/embed/aSB77Wwfo7k'),(21,9,'New Section Code.','After you create a new EditorJS object, it will contain isReady property. It is a Promise object that will be resolved when the Editor is ready for work and rejected otherwise. If there is an error during initialization the isReady promise will be rejected with an error message.','{\"time\":1653823301605,\"blocks\":[{\"id\":\"LeK_S_x32B\",\"type\":\"paragraph\",\"data\":{\"text\":\"After you create a new&nbsp;&lt;code class=\\\"inline-code\\\">EditorJS&lt;/code>&nbsp;object, it will contain isReady property. It is a Promise object that will be resolved when the Editor is ready for work and rejected otherwise. If there is an error during initialization the&nbsp;&lt;code class=\\\"inline-code\\\">isReady&lt;/code>&nbsp;promise will be rejected with an error message.\"}},{\"id\":\"o2Ty7jUW4h\",\"type\":\"raw\",\"data\":{\"html\":\"var editor = new EditorJS({\\n   // Other configuration properties\\n\\n   /**\\n    * onReady callback\\n    */\\n   onReady: () => {console.log(\'Editor.js is ready to work!\')},\\n   \\n   /**\\n    * onChange callback\\n    */\\n   onChange: (api, event) => {\\n     console.log(\'Now I know that Editor\\\\\'s content changed!\', event)\\n   }\\n});\"}}],\"version\":\"2.24.3\"}','nextJs',0,'2022-05-29 13:21:41','https://www.youtube.com/embed/Sklc_fQBmcs');
/*!40000 ALTER TABLE `templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `secondName` varchar(50) NOT NULL,
  `languageCodePreference` varchar(200) NOT NULL DEFAULT 'all languages',
  `deleteState` tinyint(1) NOT NULL DEFAULT '0',
  `password` varchar(400) NOT NULL DEFAULT '123456',
  `role` varchar(20) NOT NULL DEFAULT 'lettore',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `users_email_uindex` (`email`),
  UNIQUE KEY `users_userID_uindex` (`userID`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--


LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'b@b.com','@b_b_2','b','b','react js',0,'123456','admin','2022-05-25 00:18:10'),(5,'a@a.com','@Anders_planck_5','Anders','planck','all languages',0,'123456','creatore','2022-05-25 13:04:17'),(6,'c@c.com','@c_c_6','c','c','react js',0,'123456','lettore','2022-05-27 15:44:25'),(7,'d@d.com','@Troy_Neal_7','Troy','Neal','all language',0,'123456','lettore','2022-05-29 12:10:33'),(8,'e@e.com','@Jillian_Herrera_8','Jillian','Herrera','all languages',0,'123456','lettore','2022-05-29 12:14:58'),(9,'f@f.com','@Dorian_Chang_9','Dorian','Chang','all languages ',0,'123456','creatore','2022-05-29 13:07:31');
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

-- Dump completed on 2022-05-29 14:24:31
