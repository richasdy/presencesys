DROP TABLE IF EXISTS `Kelompok`;

CREATE TABLE `Kelompok` (
  `id` int(11) UNSIGNED NOT NULL,
  `nama` text NOT NULL,
  `note` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Kelompok`
  ADD PRIMARY KEY (`id`);

  
ALTER TABLE `Kelompok`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

