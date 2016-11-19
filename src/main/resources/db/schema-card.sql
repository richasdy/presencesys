DROP TABLE IF EXISTS `Card`;

CREATE TABLE `Card` (
  `id` int(11) UNSIGNED NOT NULL,
  `card_number` varchar(255) NOT NULL,
  `status` tinyint(1) DEFAULT '0',
  `note` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Card`
  ADD PRIMARY KEY (`id`);

  
ALTER TABLE `Card`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

