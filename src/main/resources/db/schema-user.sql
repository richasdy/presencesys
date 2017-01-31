DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` int(11) UNSIGNED NOT NULL,
  `id_card` int(11) NOT NULL,
  `user_number` varchar(255) NOT NULL, -- nip / nik / nis 
  `nama` varchar(255) NOT NULL,
  `note` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `User`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_card` (`id_card`);

  
ALTER TABLE `User`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

