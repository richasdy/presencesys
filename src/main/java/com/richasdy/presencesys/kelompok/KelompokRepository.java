package com.richasdy.presencesys.kelompok;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelompokRepository extends JpaRepository<Kelompok, Long> {

	Page<Kelompok> findDistinctGroupById(long id, Pageable pageable);

	// Date
	Page<Kelompok> findDistinctGroupByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Kelompok> findDistinctGroupByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Kelompok> findDistinctGroupByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Kelompok> findDistinctGroupByNamaContaining(String name, Pageable pageable);

	Page<Kelompok> findDistinctGroupByNoteContaining(String note, Pageable pageable);

}
