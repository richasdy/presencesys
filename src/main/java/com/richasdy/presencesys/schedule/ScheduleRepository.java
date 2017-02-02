package com.richasdy.presencesys.schedule;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	Page<Schedule> findDistinctScheduleById(long id, Pageable pageable);

	Page<Schedule> findDistinctScheduleByIdKelompok(long idCard, Pageable pageable);

	// Date
	Page<Schedule> findDistinctScheduleByTanggalBetween(Date start, Date end, Pageable pageable);

	Page<Schedule> findDistinctScheduleByTanggal(Date tanggal, Pageable pageable);

	Page<Schedule> findDistinctScheduleByStartBetween(Date start, Date end, Pageable pageable);

	Page<Schedule> findDistinctScheduleByStopBetween(Date start, Date end, Pageable pageable);

	Page<Schedule> findDistinctScheduleByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Schedule> findDistinctScheduleByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Schedule> findDistinctScheduleByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Schedule> findDistinctScheduleByTipeContaining(String nama, Pageable pageable);

	Page<Schedule> findDistinctScheduleByNoteContaining(String note, Pageable pageable);
	
//	@Query("SELECT s FROM Schedule s WHERE s.idKelompok = :idKelompok AND CURRENT_DATE = s.tanggal AND (CURRENT_TIME BETWEEN s.start AND s.stop )")
	@Query("SELECT s FROM Schedule s WHERE s.idKelompok = :idKelompok AND CURRENT_DATE = s.tanggal AND (CURRENT_TIME BETWEEN s.start AND s.stop )")
	Schedule findScheduleByIdKelompokAndNow(@Param("idKelompok")long idKelompok);

}
