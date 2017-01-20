DROP TABLE IF EXISTS `Machine`;

CREATE TABLE `Machine` (
--  `id` bigint(11) UNSIGNED NOT NULL,
  `id` int(11) UNSIGNED NOT NULL,
  `ip` varchar(255) NOT NULL,
  `note` text,
--  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Machine`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ip` (`ip`);

  
ALTER TABLE `Machine`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

