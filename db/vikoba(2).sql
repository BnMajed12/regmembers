-- phpMyAdmin SQL Dump
-- version 3.3.9.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 03, 2013 at 04:45 PM
-- Server version: 5.1.48
-- PHP Version: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vikoba`
--

-- --------------------------------------------------------

--
-- Table structure for table `huduma_zetu`
--

CREATE TABLE IF NOT EXISTS `huduma_zetu` (
  `huduma_id` int(11) NOT NULL AUTO_INCREMENT,
  `jina_la_huduma` varchar(250) NOT NULL,
  `tarehe` datetime NOT NULL,
  PRIMARY KEY (`huduma_id`),
  UNIQUE KEY `jina_la_huduma` (`jina_la_huduma`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `huduma_zetu`
--

INSERT INTO `huduma_zetu` (`huduma_id`, `jina_la_huduma`, `tarehe`) VALUES
(1, 'kufungua account', '2013-09-03 11:58:41'),
(2, 'mkopo wa kawaida', '2013-09-03 11:58:56'),
(3, 'mkopo wa pikipiki', '2013-09-03 11:59:18'),
(4, 'mkopo wa bima ya afya', '2013-09-03 11:59:34');

-- --------------------------------------------------------

--
-- Table structure for table `kata`
--

CREATE TABLE IF NOT EXISTS `kata` (
  `kata_id` int(11) NOT NULL AUTO_INCREMENT,
  `wilaya_id` int(11) NOT NULL,
  `kata` varchar(250) NOT NULL,
  PRIMARY KEY (`kata_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `kata`
--

INSERT INTO `kata` (`kata_id`, `wilaya_id`, `kata`) VALUES
(1, 1, 'segerea'),
(2, 1, 'tabata'),
(3, 1, 'buguruni'),
(4, 1, 'kisukuru'),
(5, 3, 'tandale'),
(6, 3, 'sinza');

-- --------------------------------------------------------

--
-- Table structure for table `maombi_ya_huduma`
--

CREATE TABLE IF NOT EXISTS `maombi_ya_huduma` (
  `ombi_id` int(11) NOT NULL AUTO_INCREMENT,
  `mwanachama_id` int(11) NOT NULL,
  `huduma_id` int(11) NOT NULL,
  `kiwango_cha_ombi` decimal(11,2) NOT NULL,
  `kiwango_cha_kuanzia` decimal(11,2) NOT NULL,
  `malipo_kwa_siku` decimal(11,2) NOT NULL,
  `muda_malipo_id` int(10) NOT NULL,
  `tarehe_ya_ombi` int(11) NOT NULL,
  `mratibu_id` int(11) NOT NULL,
  PRIMARY KEY (`ombi_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `maombi_ya_huduma`
--


-- --------------------------------------------------------

--
-- Table structure for table `mikoa`
--

CREATE TABLE IF NOT EXISTS `mikoa` (
  `mkoa_id` int(11) NOT NULL AUTO_INCREMENT,
  `mkoa` varchar(230) NOT NULL,
  PRIMARY KEY (`mkoa_id`),
  UNIQUE KEY `mkoa` (`mkoa`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `mikoa`
--

INSERT INTO `mikoa` (`mkoa_id`, `mkoa`) VALUES
(1, 'Arusha'),
(2, 'Dar-es-salaam'),
(3, 'Dodoma'),
(4, 'Mwanza'),
(5, 'Pwani'),
(6, 'Dakari'),
(7, 'Daruki');

-- --------------------------------------------------------

--
-- Table structure for table `mratibu`
--

CREATE TABLE IF NOT EXISTS `mratibu` (
  `mratibu_id` int(11) NOT NULL AUTO_INCREMENT,
  `jina_mratibu` varchar(250) NOT NULL,
  `neno_siri` varchar(120) NOT NULL,
  `simu_mratibu` varchar(30) NOT NULL,
  `tarehe` datetime NOT NULL,
  PRIMARY KEY (`mratibu_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `mratibu`
--


-- --------------------------------------------------------

--
-- Table structure for table `muda_malipo`
--

CREATE TABLE IF NOT EXISTS `muda_malipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `muda` int(11) NOT NULL,
  `kipimo` varchar(40) NOT NULL,
  `kwa_maneno` varchar(130) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kwa_maneno` (`kwa_maneno`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `muda_malipo`
--

INSERT INTO `muda_malipo` (`id`, `muda`, `kipimo`, `kwa_maneno`) VALUES
(1, 4, 'mwezi', 'Miezi 4'),
(2, 30, 'siku', 'siku 30'),
(3, 2, 'mwaka', 'miaka 2'),
(4, 3, 'wiki', 'wiki 3');

-- --------------------------------------------------------

--
-- Table structure for table `mwanachama`
--

CREATE TABLE IF NOT EXISTS `mwanachama` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jina` varchar(300) NOT NULL,
  `jinsia` varchar(20) NOT NULL,
  `tarehe_kuzaliwa` date NOT NULL,
  `mkoa` varchar(200) NOT NULL,
  `wilaya` varchar(200) NOT NULL,
  `kata` varchar(200) NOT NULL,
  `simu` varchar(100) NOT NULL,
  `namba_kitambulisho` varchar(200) NOT NULL,
  `aina_kitambulisho` varchar(200) NOT NULL,
  `mratibu_id` varchar(200) NOT NULL,
  `tareheleo` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `mwanachama`
--


-- --------------------------------------------------------

--
-- Table structure for table `mwanachama_biashara`
--

CREATE TABLE IF NOT EXISTS `mwanachama_biashara` (
  `biashara_id` int(11) NOT NULL AUTO_INCREMENT,
  `mwanachama_id` int(11) NOT NULL,
  `kikundi` varchar(200) NOT NULL,
  `biashara` text NOT NULL,
  PRIMARY KEY (`biashara_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `mwanachama_biashara`
--


-- --------------------------------------------------------

--
-- Table structure for table `mwanachama_famili`
--

CREATE TABLE IF NOT EXISTS `mwanachama_famili` (
  `mwanafamili_id` int(11) NOT NULL AUTO_INCREMENT,
  `mwanachama_id` int(11) NOT NULL,
  `aina_ya_mwanafamili` varchar(100) NOT NULL,
  `jina_la_mwanafamili` text NOT NULL,
  `mratibu_id` int(11) NOT NULL,
  PRIMARY KEY (`mwanafamili_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `mwanachama_famili`
--


-- --------------------------------------------------------

--
-- Table structure for table `mwanachama_mdhamini`
--

CREATE TABLE IF NOT EXISTS `mwanachama_mdhamini` (
  `mdhamini_id` int(11) NOT NULL AUTO_INCREMENT,
  `mwanachama_id` int(11) NOT NULL,
  `jina_la_mdhamini` varchar(250) NOT NULL,
  `simu_ya_dhamini` varchar(30) NOT NULL,
  `mratibu_id` int(11) NOT NULL,
  `tarehe` datetime NOT NULL,
  PRIMARY KEY (`mdhamini_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `mwanachama_mdhamini`
--


-- --------------------------------------------------------

--
-- Table structure for table `picha_zetu`
--

CREATE TABLE IF NOT EXISTS `picha_zetu` (
  `img_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(200) NOT NULL,
  `table_id` int(11) NOT NULL,
  `image_path` text NOT NULL,
  `img_path` varchar(300) NOT NULL,
  `user_id` int(11) NOT NULL,
  `img_date` datetime NOT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `picha_zetu`
--


-- --------------------------------------------------------

--
-- Table structure for table `vikundi`
--

CREATE TABLE IF NOT EXISTS `vikundi` (
  `kikundi_id` int(11) NOT NULL AUTO_INCREMENT,
  `jina_kikundi` int(11) NOT NULL,
  `tarehe` datetime NOT NULL,
  PRIMARY KEY (`kikundi_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `vikundi`
--


-- --------------------------------------------------------

--
-- Table structure for table `wilaya`
--

CREATE TABLE IF NOT EXISTS `wilaya` (
  `wilaya_id` int(11) NOT NULL AUTO_INCREMENT,
  `mkoa_id` int(11) NOT NULL,
  `wilaya` varchar(250) NOT NULL,
  PRIMARY KEY (`wilaya_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `wilaya`
--

INSERT INTO `wilaya` (`wilaya_id`, `mkoa_id`, `wilaya`) VALUES
(1, 2, 'Ilala'),
(2, 2, 'Temeke'),
(3, 2, 'Kinondoni'),
(4, 1, 'Terangilo'),
(5, 1, 'Temboni');
