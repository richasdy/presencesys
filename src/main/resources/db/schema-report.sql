DROP TABLE IF EXISTS `Report`;

CREATE TABLE `Report` (
  `id_tap` bigint(11) UNSIGNED NOT NULL,
  `id_schedule` bigint(11) UNSIGNED NOT NULL,
  `id_kelompok` bigint(11) UNSIGNED NOT NULL,
  `id_user` bigint(11) UNSIGNED NOT NULL,
  `id_card` bigint(11) UNSIGNED NOT NULL,
  `id_machine` bigint(11) UNSIGNED NOT NULL,
  `tap_status` varchar(255) NOT NULL,
  `tap_note` text,
  `schedule_tipe` varchar(255) NOT NULL,
  `schedule_note` text,
  `schedule_tanggal` date NOT NULL,
  `schedule_start` datetime NOT NULL,
  `schedule_stop` datetime NOT NULL,
  `kelompok_nama` text NOT NULL,
  `kelompok_note` text,
  `user_number` varchar(255) NOT NULL,
  `user_nama` varchar(255) NOT NULL,
  `user_note` text,
  `card_number` varchar(255) NOT NULL,
  `card_note` text,
  `machine_ip` varchar(255) NOT NULL,
  `machine_note` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;