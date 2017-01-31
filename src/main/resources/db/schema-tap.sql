DROP TABLE IF EXISTS `Tap`;

CREATE TABLE `Tap` (
  `id` bigint(11) UNSIGNED NOT NULL,
  `id_schedule` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_kelompok` int(11) NOT NULL,
  `schedule_tanggal` date NOT NULL,
  `schedule_start` datetime NOT NULL,
  `schedule_stop` datetime NOT NULL,
  `schedule_tipe` varchar(255) NOT NULL,
  `user_number` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL, -- nip / nik / nis 
  `status` varchar(255) NOT NULL,
  `note` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Tap`
  ADD PRIMARY KEY (`id`);

  
ALTER TABLE `Tap`
  MODIFY `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT;

