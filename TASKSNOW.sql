-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Tempo de geração: 12/07/2020 às 23:34
-- Versão do servidor: 10.4.11-MariaDB
-- Versão do PHP: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `TASKSNOW`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `TAREFAS`
--

CREATE TABLE `TAREFAS` (
  `ID` int(11) NOT NULL,
  `TITULO` varchar(50) DEFAULT NULL,
  `DESCRICAO` varchar(255) DEFAULT NULL,
  `PRIORIDADE` int(11) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Despejando dados para a tabela `TAREFAS`
--

INSERT INTO `TAREFAS` (`ID`, `TITULO`, `DESCRICAO`, `PRIORIDADE`, `STATUS`) VALUES
(22, 'Subir no git', '', 3, 'A FAZER'),
(23, 'Ajustar o código', '', 2, 'FEITO'),
(24, 'Testar o programa', '', 1, 'FEITO');

--
-- Índices de tabelas apagadas
--

--
-- Índices de tabela `TAREFAS`
--
ALTER TABLE `TAREFAS`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de tabelas apagadas
--

--
-- AUTO_INCREMENT de tabela `TAREFAS`
--
ALTER TABLE `TAREFAS`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
