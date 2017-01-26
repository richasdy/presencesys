DROP TABLE IF EXISTS `Group`;

CREATE TABLE `Group` (
  `id` int(11) UNSIGNED NOT NULL,
  `name` text NOT NULL,
  `note` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Group`
  ADD PRIMARY KEY (`id`);

  
ALTER TABLE `Group`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

