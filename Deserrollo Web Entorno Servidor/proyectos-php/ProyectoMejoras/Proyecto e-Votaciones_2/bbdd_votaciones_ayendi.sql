-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 03-02-2026 a las 22:29:35
-- Versión del servidor: 10.6.22-MariaDB-0ubuntu0.22.04.1
-- Versión de PHP: 8.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bbdd_votaciones_ayendi`
--
CREATE DATABASE IF NOT EXISTS `bbdd_votaciones_ayendi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bbdd_votaciones_ayendi`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `candidato`
--

CREATE TABLE `candidato` (
  `id_candidato` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `numero_lista` int(11) NOT NULL,
  `id_partido` int(11) NOT NULL,
  `idLocalidad` int(11) DEFAULT NULL,
  `dni` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `candidato`
--

INSERT INTO `candidato` (`id_candidato`, `nombre`, `apellidos`, `numero_lista`, `id_partido`, `idLocalidad`, `dni`) VALUES
(22, 'Inma', 'Moreno', 1, 27, NULL, '11111111E'),
(23, 'Ayendi', 'Rosario Rojas', 1, 26, NULL, '55052460b');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comunidad_autonoma`
--

CREATE TABLE `comunidad_autonoma` (
  `id_comunidad` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comunidad_autonoma`
--

INSERT INTO `comunidad_autonoma` (`id_comunidad`, `nombre`) VALUES
(1, 'Andalucía'),
(2, 'Cataluña'),
(3, 'Madrid'),
(4, 'Valencia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eleccion`
--

CREATE TABLE `eleccion` (
  `id_eleccion` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `estado` enum('CERRADA','ABIERTA','FINALIZADA') DEFAULT 'CERRADA'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `eleccion`
--

INSERT INTO `eleccion` (`id_eleccion`, `nombre`, `fecha_inicio`, `fecha_fin`, `estado`) VALUES
(5, 'Jordy Steven ', '2025-11-12', '2025-11-30', 'FINALIZADA'),
(8, 'General 2026', '2026-01-29', '2026-02-01', 'FINALIZADA'),
(9, 'Pepe', '2026-01-31', '2026-02-01', 'FINALIZADA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `localidad`
--

CREATE TABLE `localidad` (
  `id_localidad` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `id_comunidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `localidad`
--

INSERT INTO `localidad` (`id_localidad`, `nombre`, `id_comunidad`) VALUES
(1, 'Albacete', 3),
(2, 'Madrid', 4),
(3, 'Barcelona', 2),
(4, 'Galicia', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `participacion`
--

CREATE TABLE `participacion` (
  `idParticipante` int(11) NOT NULL,
  `id_localidad` int(11) NOT NULL,
  `numero_censados` int(11) NOT NULL,
  `total_votos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido_politico`
--

CREATE TABLE `partido_politico` (
  `id_partido` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `siglas` varchar(20) NOT NULL,
  `logo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partido_politico`
--

INSERT INTO `partido_politico` (`id_partido`, `nombre`, `siglas`, `logo`) VALUES
(26, 'bachatero', 'B_O', 'romeo_santos.jpg'),
(27, 'Pepe', 'PP', 'PSOE_logo_2017.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona_censo`
--

CREATE TABLE `persona_censo` (
  `dni` char(9) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `id_localidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona_censo`
--

INSERT INTO `persona_censo` (`dni`, `nombre`, `apellidos`, `fecha_nacimiento`, `id_localidad`) VALUES
('11111111B', 'Andy', 'Moreno', '1999-02-01', 1),
('11111111C', 'Angel', 'Moreno', '1999-02-01', 3),
('11111111E', 'Inma', 'Moreno', '1999-02-01', 1),
('55052460b', 'Ayendi', 'Rosario Rojas', '2002-04-19', 2),
('55052460c', 'Arlinzon', 'Rosario Rojas', '2002-04-19', 2),
('55052460I', 'Idayelin', 'Rosario Rojas', '2002-04-19', 3),
('55052460j', 'Ayendi', 'Rosario Rojas', '2000-02-02', 3),
('55052460M', 'Ayendi', 'Rosario Rojas', '2002-04-19', 1),
('55052460s', '55052460p', 'aaa', '2002-01-02', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `dni` char(9) NOT NULL,
  `password_md5` char(32) NOT NULL,
  `rol` enum('ADMIN','VOTANTE','ANALISTA') DEFAULT 'VOTANTE',
  `ha_votado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `dni`, `password_md5`, `rol`, `ha_votado`) VALUES
(28, '11111111E', 'c4ca4238a0b923820dcc509a6f75849b', 'ADMIN', 0),
(29, '55052460M', 'c4ca4238a0b923820dcc509a6f75849b', 'VOTANTE', 0),
(30, '11111111B', 'c4ca4238a0b923820dcc509a6f75849b', 'VOTANTE', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `voto`
--

CREATE TABLE `voto` (
  `id_voto` int(11) NOT NULL,
  `id_eleccion` int(11) NOT NULL,
  `id_partido` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha_voto` datetime DEFAULT current_timestamp(),
  `id_localidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `candidato`
--
ALTER TABLE `candidato`
  ADD PRIMARY KEY (`id_candidato`),
  ADD KEY `id_partido` (`id_partido`),
  ADD KEY `dmo` (`dni`);

--
-- Indices de la tabla `comunidad_autonoma`
--
ALTER TABLE `comunidad_autonoma`
  ADD PRIMARY KEY (`id_comunidad`);

--
-- Indices de la tabla `eleccion`
--
ALTER TABLE `eleccion`
  ADD PRIMARY KEY (`id_eleccion`);

--
-- Indices de la tabla `localidad`
--
ALTER TABLE `localidad`
  ADD PRIMARY KEY (`id_localidad`),
  ADD KEY `id_comunidad` (`id_comunidad`);

--
-- Indices de la tabla `participacion`
--
ALTER TABLE `participacion`
  ADD PRIMARY KEY (`idParticipante`),
  ADD KEY `id_localidadRe` (`id_localidad`);

--
-- Indices de la tabla `partido_politico`
--
ALTER TABLE `partido_politico`
  ADD PRIMARY KEY (`id_partido`);

--
-- Indices de la tabla `persona_censo`
--
ALTER TABLE `persona_censo`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `id_localidad` (`id_localidad`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `dni` (`dni`);

--
-- Indices de la tabla `voto`
--
ALTER TABLE `voto`
  ADD PRIMARY KEY (`id_voto`),
  ADD UNIQUE KEY `id_eleccion` (`id_eleccion`,`id_usuario`),
  ADD KEY `id_partido` (`id_partido`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `fk_voto_localidad` (`id_localidad`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `candidato`
--
ALTER TABLE `candidato`
  MODIFY `id_candidato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `comunidad_autonoma`
--
ALTER TABLE `comunidad_autonoma`
  MODIFY `id_comunidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `eleccion`
--
ALTER TABLE `eleccion`
  MODIFY `id_eleccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `localidad`
--
ALTER TABLE `localidad`
  MODIFY `id_localidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `participacion`
--
ALTER TABLE `participacion`
  MODIFY `idParticipante` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `partido_politico`
--
ALTER TABLE `partido_politico`
  MODIFY `id_partido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `voto`
--
ALTER TABLE `voto`
  MODIFY `id_voto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `candidato`
--
ALTER TABLE `candidato`
  ADD CONSTRAINT `candidato_ibfk_1` FOREIGN KEY (`id_partido`) REFERENCES `partido_politico` (`id_partido`);

--
-- Filtros para la tabla `localidad`
--
ALTER TABLE `localidad`
  ADD CONSTRAINT `localidad_ibfk_1` FOREIGN KEY (`id_comunidad`) REFERENCES `comunidad_autonoma` (`id_comunidad`);

--
-- Filtros para la tabla `participacion`
--
ALTER TABLE `participacion`
  ADD CONSTRAINT `id_localidadRe` FOREIGN KEY (`id_localidad`) REFERENCES `localidad` (`id_localidad`);

--
-- Filtros para la tabla `persona_censo`
--
ALTER TABLE `persona_censo`
  ADD CONSTRAINT `persona_censo_ibfk_1` FOREIGN KEY (`id_localidad`) REFERENCES `localidad` (`id_localidad`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `persona_censo` (`dni`);

--
-- Filtros para la tabla `voto`
--
ALTER TABLE `voto`
  ADD CONSTRAINT `fk_voto_localidad` FOREIGN KEY (`id_localidad`) REFERENCES `localidad` (`id_localidad`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `voto_ibfk_1` FOREIGN KEY (`id_eleccion`) REFERENCES `eleccion` (`id_eleccion`),
  ADD CONSTRAINT `voto_ibfk_2` FOREIGN KEY (`id_partido`) REFERENCES `partido_politico` (`id_partido`),
  ADD CONSTRAINT `voto_ibfk_3` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
