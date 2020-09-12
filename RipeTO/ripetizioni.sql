-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 30, 2020 alle 18:48
-- Versione del server: 10.4.11-MariaDB
-- Versione PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ripetizioni`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `corsi`
--

CREATE TABLE `corsi` (
  `id_corso` int(8) NOT NULL,
  `titolo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corsi`
--

INSERT INTO `corsi` (`id_corso`, `titolo`) VALUES
(111, 'IUM'),
(123, 'RO'),
(222, 'Matematica Discreta'),
(333, 'Reti I'),
(444, 'Sistemi Intelligenti'),
(456, 'Prog I'),
(666, 'Logica'),
(777, 'Prog 3'),
(888, 'Android');

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti`
--

CREATE TABLE `docenti` (
  `id_docente` int(8) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenti`
--

INSERT INTO `docenti` (`id_docente`, `nome`) VALUES
(111, 'Viviana Patti'),
(222, 'Andrea Mori'),
(333, 'Marco Botta'),
(444, 'Cristina Baroglio'),
(555, 'Mario Coppo'),
(777, 'Liliana Ardissono'),
(888, 'Marino Segnan'),
(999, 'Roberto Aringhieri');

-- --------------------------------------------------------

--
-- Struttura della tabella `lezioni`
--

CREATE TABLE `lezioni` (
  `id_lezione` int(8) NOT NULL,
  `id_docente` int(8) NOT NULL,
  `id_corso` int(8) NOT NULL,
  `data` varchar(20) NOT NULL,
  `ora` varchar(20) NOT NULL,
  `stato` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `lezioni`
--

INSERT INTO `lezioni` (`id_lezione`, `id_docente`, `id_corso`, `data`, `ora`, `stato`) VALUES
(933, 333, 222, 'Martedi', 'dalle 16 alle 17', 'disponibile'),
(935, 111, 888, 'Venerdi', 'dalle 15 alle 16', 'disponibile'),
(936, 777, 456, 'Lunedi', 'dalle 15 alle 16', 'disponibile');

-- --------------------------------------------------------

--
-- Struttura della tabella `ripetizioni`
--

CREATE TABLE `ripetizioni` (
  `id_prenotazione` int(8) NOT NULL,
  `id_utente` int(8) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `data` varchar(50) NOT NULL,
  `ora` varchar(50) NOT NULL,
  `stato` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ripetizioni`
--

INSERT INTO `ripetizioni` (`id_prenotazione`, `id_utente`, `nome`, `titolo`, `data`, `ora`, `stato`) VALUES
(46, 858344, 'Marino Segnan', 'Android', 'Lunedi', 'dalle 15 alle 16', 'effettuata');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `id_utente` int(8) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `ruolo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id_utente`, `email`, `password`, `nome`, `cognome`, `ruolo`) VALUES
(849566, 'riccardovicario@gmail.com', 'riccardovicario1', 'riccardo', 'vicario', 'utente'),
(858344, 'danielrusso@gmail.com', 'danielrusso1', 'daniel', 'russo', 'utente'),
(868455, 'leonardomontagna@gmail.com', 'leonardomontagna1', 'leonardo', 'montagna', 'amministratore');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corsi`
--
ALTER TABLE `corsi`
  ADD PRIMARY KEY (`id_corso`);

--
-- Indici per le tabelle `docenti`
--
ALTER TABLE `docenti`
  ADD PRIMARY KEY (`id_docente`);

--
-- Indici per le tabelle `lezioni`
--
ALTER TABLE `lezioni`
  ADD PRIMARY KEY (`id_lezione`,`id_docente`,`id_corso`,`data`,`ora`);

--
-- Indici per le tabelle `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD PRIMARY KEY (`id_prenotazione`,`id_utente`,`nome`,`titolo`,`data`,`ora`,`stato`),
  ADD KEY `d_utente_key` (`id_utente`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id_utente`,`email`,`password`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `lezioni`
--
ALTER TABLE `lezioni`
  MODIFY `id_lezione` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=937;

--
-- AUTO_INCREMENT per la tabella `ripetizioni`
--
ALTER TABLE `ripetizioni`
  MODIFY `id_prenotazione` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `d_utente_key` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id_utente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
