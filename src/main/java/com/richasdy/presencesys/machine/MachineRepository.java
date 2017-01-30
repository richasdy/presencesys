package com.richasdy.presencesys.machine;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

	Page<Machine> findDistinctMachineById(long id, Pageable pageable);

	// Date
	Page<Machine> findDistinctMachineByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Machine> findDistinctMachineByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Machine> findDistinctMachineByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Machine> findDistinctMachineByIpContaining(String ip, Pageable pageable);

	Page<Machine> findDistinctMachineByNoteContaining(String note, Pageable pageable);

}
