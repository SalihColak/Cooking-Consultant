-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Dez 2019 um 17:50
-- Server-Version: 10.3.16-MariaDB
-- PHP-Version: 7.1.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `syp14`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `beitrag`
--

CREATE TABLE `beitrag` (
  `beitid` int(11) NOT NULL,
  `titel` varchar(40) NOT NULL,
  `kategorie` varchar(40) NOT NULL,
  `inhalt` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `userid` int(11) NOT NULL,
  `titel` enum('Herr','Frau','Divers') NOT NULL,
  `name` varchar(40) NOT NULL,
  `vorname` varchar(40) NOT NULL,
  `geschlecht` enum('m','w','d') NOT NULL,
  `geburtsdatum` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `passwort` varchar(40) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`userid`, `titel`, `name`, `vorname`, `geschlecht`, `geburtsdatum`, `email`, `passwort`, `admin`) VALUES
(1, 'Herr', 'Colak', 'Salih', 'm', '1900-01-01', 'admin.syp14@email.de', '123', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer2rezept`
--

CREATE TABLE `benutzer2rezept` (
  `ben2rezid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `rezid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `einkaufsliste`
--

CREATE TABLE `einkaufsliste` (
  `einkid` int(11) NOT NULL,
  `zustand` varchar(40) NOT NULL,
  `userid` int(11) NOT NULL,
  `rezid` int(11) NOT NULL,
  `menge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `einkaufsliste2zutatstate`
--

CREATE TABLE `einkaufsliste2zutatstate` (
  `ein2zutid` int(11) NOT NULL,
  `einkid` int(11) NOT NULL,
  `zutid` int(11) NOT NULL,
  `state` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rezept`
--

CREATE TABLE `rezept` (
  `rezid` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `schritte` mediumtext NOT NULL,
  `menge` mediumtext NOT NULL,
  `kochzeit` int(11) NOT NULL,
  `art` enum('FRUEHSTUECK','BRUNCH','MITTAGESSEN','KAFFEETAFEL','ABENDESSEN','SNACK') NOT NULL,
  `anlass` enum('FAMILIENESSEN','FEIER','KINDERGEBURTSTAG','GEBURTSTAG','ESSEN ZU ZWEIT','FREUNDE') DEFAULT NULL,
  `praeferenz` enum('ITALIENISCH','ASIATISCH','ORIENTALISCH','TUERKISCH','DEUTSCH','AMERIKANISCH','INDISCH','HAUSMANNSKOST','INTERNATIONAL') NOT NULL,
  `bild` varchar(40) NOT NULL,
  `beschreibung` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `rezept`
--

INSERT INTO `rezept` (`rezid`, `name`, `schritte`, `menge`, `kochzeit`, `art`, `anlass`, `praeferenz`, `bild`, `beschreibung`) VALUES
(1, 'Rührei', 'Die Eier in eine Schüssel schlagen und mit einem Schneebesen kräftig rühren. Dann die Milch hinzufügen und erneut rühren.;Zum Schluss Schnittlauch und die Gewürze hinzufügen und nochmal leicht durchrühren.;Dann die Masse in einer erhitzten Pfanne unter leichtem Rühren (sobald sich was gesetzt hat) zum Stocken bringen und leicht bräunen. Nach Belieben mit Kräutern garnieren.', '2;50;1;1;1', 10, 'FRUEHSTUECK', 'FAMILIENESSEN', 'DEUTSCH', 'bilder/rezepte/bananenkuchen.png', 'Ob als Frühstück, Low Carb-Abendessen oder kleine Zwischenmahlzeit, wenn es schnell und lecker sein soll, liegt man mit Rührei immer richtig. Doch wie macht man Rührei? Einfach ein paar Eier in die heiße Pfanne hauen. Denkst du! Ein paar Tricks solltest du beachten, damit das Rührei auch perfekt wird. ');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rezept2zutat`
--

CREATE TABLE `rezept2zutat` (
  `rez2zutid` int(11) NOT NULL,
  `rezid` int(11) NOT NULL,
  `zutid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `rezept2zutat`
--

INSERT INTO `rezept2zutat` (`rez2zutid`, `rezid`, `zutid`) VALUES
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `zutat`
--

CREATE TABLE `zutat` (
  `zutid` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `einheit` varchar(40) NOT NULL,
  `bild` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `zutat`
--

INSERT INTO `zutat` (`zutid`, `name`, `einheit`, `bild`) VALUES
(7, 'Ei', 'Stück', ''),
(8, 'Milch 3,5%', 'ml', ''),
(9, 'Schnittlauch', 'Priese', ''),
(10, 'Salz', 'Priese', ''),
(11, 'Pfeffer', 'Priese', '');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `beitrag`
--
ALTER TABLE `beitrag`
  ADD PRIMARY KEY (`beitid`);

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indizes für die Tabelle `benutzer2rezept`
--
ALTER TABLE `benutzer2rezept`
  ADD PRIMARY KEY (`ben2rezid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `rezid` (`rezid`);

--
-- Indizes für die Tabelle `einkaufsliste`
--
ALTER TABLE `einkaufsliste`
  ADD PRIMARY KEY (`einkid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `rezid` (`rezid`);

--
-- Indizes für die Tabelle `einkaufsliste2zutatstate`
--
ALTER TABLE `einkaufsliste2zutatstate`
  ADD PRIMARY KEY (`ein2zutid`),
  ADD KEY `einkid` (`einkid`),
  ADD KEY `zutid` (`zutid`);

--
-- Indizes für die Tabelle `rezept`
--
ALTER TABLE `rezept`
  ADD PRIMARY KEY (`rezid`);

--
-- Indizes für die Tabelle `rezept2zutat`
--
ALTER TABLE `rezept2zutat`
  ADD PRIMARY KEY (`rez2zutid`),
  ADD KEY `rezid` (`rezid`),
  ADD KEY `zutid` (`zutid`);

--
-- Indizes für die Tabelle `zutat`
--
ALTER TABLE `zutat`
  ADD PRIMARY KEY (`zutid`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `beitrag`
--
ALTER TABLE `beitrag`
  MODIFY `beitid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `benutzer2rezept`
--
ALTER TABLE `benutzer2rezept`
  MODIFY `ben2rezid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `einkaufsliste`
--
ALTER TABLE `einkaufsliste`
  MODIFY `einkid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT für Tabelle `einkaufsliste2zutatstate`
--
ALTER TABLE `einkaufsliste2zutatstate`
  MODIFY `ein2zutid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT für Tabelle `rezept`
--
ALTER TABLE `rezept`
  MODIFY `rezid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `rezept2zutat`
--
ALTER TABLE `rezept2zutat`
  MODIFY `rez2zutid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT für Tabelle `zutat`
--
ALTER TABLE `zutat`
  MODIFY `zutid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `benutzer2rezept`
--
ALTER TABLE `benutzer2rezept`
  ADD CONSTRAINT `benutzer2rezept_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `benutzer` (`userid`),
  ADD CONSTRAINT `benutzer2rezept_ibfk_2` FOREIGN KEY (`rezid`) REFERENCES `rezept` (`rezid`);

--
-- Constraints der Tabelle `einkaufsliste`
--
ALTER TABLE `einkaufsliste`
  ADD CONSTRAINT `einkaufsliste_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `benutzer` (`userid`),
  ADD CONSTRAINT `einkaufsliste_ibfk_2` FOREIGN KEY (`rezid`) REFERENCES `rezept` (`rezid`);

--
-- Constraints der Tabelle `einkaufsliste2zutatstate`
--
ALTER TABLE `einkaufsliste2zutatstate`
  ADD CONSTRAINT `einkaufsliste2zutatstate_ibfk_1` FOREIGN KEY (`einkid`) REFERENCES `einkaufsliste` (`einkid`),
  ADD CONSTRAINT `einkaufsliste2zutatstate_ibfk_2` FOREIGN KEY (`zutid`) REFERENCES `zutat` (`zutid`);

--
-- Constraints der Tabelle `rezept2zutat`
--
ALTER TABLE `rezept2zutat`
  ADD CONSTRAINT `rezept2zutat_ibfk_1` FOREIGN KEY (`rezid`) REFERENCES `rezept` (`rezid`),
  ADD CONSTRAINT `rezept2zutat_ibfk_2` FOREIGN KEY (`zutid`) REFERENCES `zutat` (`zutid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
