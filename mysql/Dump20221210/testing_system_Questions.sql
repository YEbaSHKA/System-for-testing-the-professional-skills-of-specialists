-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: testing_system
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

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
-- Table structure for table `Questions`
--

DROP TABLE IF EXISTS `Questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `first_answer` text NOT NULL,
  `second_answer` text NOT NULL,
  `third_answer` text NOT NULL,
  `correct_answer_number` int NOT NULL,
  `id_test` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_test` (`id_test`),
  CONSTRAINT `Questions_ibfk_1` FOREIGN KEY (`id_test`) REFERENCES `Tests` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Questions`
--

LOCK TABLES `Questions` WRITE;
/*!40000 ALTER TABLE `Questions` DISABLE KEYS */;
INSERT INTO `Questions` VALUES (1,'Что покажет данный код? int const a = 5; a++; cout<<a;','5','6','Ошибку',1,1),(2,'Основными принципами ООП являются:','Абстракция, инкапсуляция, полиморфизм и наследование','Абстракция, инкапсуляция и полиморфизм','Абстракция, инкапсуляция и наследование',1,3),(3,'Какое наименование раздела можно явно не указывать в описании класса?','protected','public','private',3,1),(4,'Класс - это:','любой тип данных, определяемый пользователем','тип данных, определяемый пользователем и сочетающий в себе данные и функции их обработки','структура, для которой в программе имеются функции работы с нею',2,3),(5,'Объект - это:','переменная, содержащая указатель на класс','экземпляр класса','класс, который содержит в себе данные и методы их обработки',2,3),(6,'Что называется деструктором?','метод, который удаляет объект','системная функция, которая освобождает память, занимаемую объектом','метод, который освобождает память, занимаемую объектом',3,3),(7,'Полиморфизм - это :','средство, позволяющее использовать одно имя для обозначения действий, общих для родственных классов','средство, позволяющее в одном классе испоьзовать методы с разными именами для выполнения одинаковых действий','средство, позволяющее в одном классе использовать методы с одинаковыми именами',1,3),(8,'Что такое массив?','таблица, хранящая различные значения','структура данных, хранящая набор значений одного типа, объединенных под одним единым именем и индентифицируемым по индексу','ячейка в памяти компьютера, где может храниться одно значение',2,1),(9,'Допускается ли перегрузка деструкторов?','Нет','Да','Дес... Что?',1,1),(10,'Можно ли перегружать операции для встроенных типов данных?','Нет','Да','Не знаю',1,1),(11,'Что определяет операция sizeof(str);?','число символов в заданной строке','сколько байтов занимает переменная','операции, допустимые с данной переменной',2,4),(12,'Что понимается под сопровождением программного обеспечения?','внесение изменений','устранение ошибок','установка и настройка',2,4),(13,'Отметьте правильное создание объекта вложенного класса cls2, если имеется следующее объявление: class cls1 { public : int yy; class cls2 { public : int zz; }; };','cls2 c2;','cls1::cls2 c2;','cls2::cls1 c2;',2,4),(14,'Чем механизм шаблонов отличается от механизма перегрузки','перегрузка не требует единообразия алгоритмов перегружаемых функций','ничем не отличается, это просто разные наименования одного и того же','шаблон создается для функций, различающихся типами данных',1,4),(15,'Какая функция будет первой вызвана, если функция запустила особую ситуацию, не указанную в ее описании','abort()','terminate()','unexpected()',3,4);
/*!40000 ALTER TABLE `Questions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-10 16:02:07
