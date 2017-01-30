DROP TABLE IF EXISTS `Schedule`;

CREATE TABLE `Schedule` (
  `id` int(11) UNSIGNED NOT NULL,
  `id_kelompok` int(11) NOT NULL,
  `tipe` varchar(255) NOT NULL,
  `note` text,
  `tanggal` date NOT NULL,
  `start` datetime NOT NULL,
  `stop` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Schedule`
  ADD PRIMARY KEY (`id`);

  
ALTER TABLE `Schedule`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

