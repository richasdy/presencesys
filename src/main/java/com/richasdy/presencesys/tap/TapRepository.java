package com.richasdy.presencesys.tap;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TapRepository extends JpaRepository<Tap, Long> {

	Page<Tap> findDistinctTapById(long id, Pageable pageable);

	Page<Tap> findDistinctTapByIdSchedule(long idSchedule, Pageable pageable);

	Page<Tap> findDistinctTapByIdUser(long idUser, Pageable pageable);

	Page<Tap> findDistinctTapByIdKelompok(long idKelompok, Pageable pageable);

	// Date

	Page<Tap> findDistinctTapByScheduleTanggal(Date tanggal, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleStartBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleStopBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Tap> findDistinctTapByUserNumberContaining(String userNumber, Pageable pageable);
	
	Page<Tap> findDistinctTapByScheduleTipeContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByNamaContaining(String nama, Pageable pageable);
	
	Page<Tap> findDistinctTapByStatusContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByNoteContaining(String note, Pageable pageable);
	
	


}
