package com.richasdy.presencesys.tap;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TapRepository extends JpaRepository<Tap, Long> {

	Page<Tap> findDistinctTapById(long id, Pageable pageable);

	Page<Tap> findDistinctTapByIdKelompok(long idSchedule, Pageable pageable);

	Page<Tap> findDistinctTapByIdUser(long idSchedule, Pageable pageable);

	Page<Tap> findDistinctTapByIdSchedule(long idSchedule, Pageable pageable);

	Page<Tap> findDistinctTapByIdCard(long idSchedule, Pageable pageable);

	Page<Tap> findDistinctTapByIdMachine(long idSchedule, Pageable pageable);

	// Date

	Page<Tap> findDistinctTapByScheduleTanggal(Date tanggal, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleStartBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleStopBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Tap> findDistinctTapByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Tap> findDistinctTapByKelompokNamaContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByUserNumberContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByUserNamaContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleTipeContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByScheduleNoteContaining(String note, Pageable pageable);

	Page<Tap> findDistinctTapByCardNumberContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByMachineIpContaining(String nama, Pageable pageable);

	Page<Tap> findDistinctTapByStatusContaining(String userNumber, Pageable pageable);

	Page<Tap> findDistinctTapByNoteContaining(String note, Pageable pageable);

}
