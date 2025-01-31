-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-12-2024 a las 05:11:05
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dentistas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `id` varchar(11) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`id`, `user`, `password`) VALUES
('1', 'coco', '1234'),
('2', 'gusa', '1234'),
('3', 'emilio', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dentistas`
--

CREATE TABLE `dentistas` (
  `id` int(11) NOT NULL,
  `nombre_completo` varchar(150) NOT NULL,
  `licencia` varchar(50) NOT NULL,
  `fecha_nacimiento` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `calificacion` float NOT NULL,
  `especialidad` varchar(100) NOT NULL,
  `hora_apertura` varchar(50) NOT NULL,
  `hora_cierre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dentistas`
--

INSERT INTO `dentistas` (`id`, `nombre_completo`, `licencia`, `fecha_nacimiento`, `telefono`, `email`, `direccion`, `calificacion`, `especialidad`, `hora_apertura`, `hora_cierre`) VALUES
(0, 'Pedro Fernandez Alcaraz', 'LIC2019A29JS', '11/12/1986', '3330362181', 'pedrofernandez@gmail.com', 'Calle alcantarilla 251', 4.5, 'Endodoncia', '12:00', '20:00'),
(1, 'Claudia Ramírez Santos', 'LIC2022C56RS', '1992-05-18', '3345678901', 'claudiaramirez@gmail.com', 'Avenida Sol 654', 4.6, 'Odontología pediátrica', '13:00', '21:00'),
(2, 'Luis García López', 'LIC2022L56GL', '1985-03-12', '5554321987', 'luisgarcia@gmail.com', 'Calle Luna 432', 4.8, 'Endodoncia', '09:00', '17:00'),
(3, 'María Fernández Ruiz', 'LIC2022M98FR', '1990-11-25', '5523419876', 'mariafernandez@gmail.com', 'Paseo del Río 321', 4.7, 'Ortodoncia y ortopedia dentofacial', '10:00', '18:00'),
(4, 'Carlos López Martínez', 'LIC2023C78LM', '1982-07-18', '5532109876', 'carloslopez@gmail.com', 'Boulevard del Sol 123', 4.9, 'Periodoncia', '08:00', '16:00'),
(5, 'Ana Pérez Torres', 'LIC2022A34PT', '1995-02-14', '5534129876', 'anaperez@gmail.com', 'Avenida Las Palmas 567', 4.5, 'Prostodoncia', '11:00', '19:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dentistas`
--
ALTER TABLE `dentistas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `dentistas`
--
ALTER TABLE `dentistas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
