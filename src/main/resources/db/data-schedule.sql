INSERT INTO `Schedule` (`id_kelompok`, `tipe`, `note`,  `tanggal`, `start`, `stop`, `created_at`, `updated_at`, `deleted_at`) VALUES
('1','datang', 'test note', NOW(), DATE_SUB(NOW(), INTERVAL 1 HOUR), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW(), NULL, NULL),
('2','pulang', 'test note2', NOW(), DATE_SUB(NOW(), INTERVAL 1 HOUR), DATE_ADD(NOW(), INTERVAL 1 HOUR), NOW(), NULL, NULL);