package com.richasdy.presencesys.group;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Page<Group> findDistinctMachineById(long id, Pageable pageable);

	// Date
	Page<Group> findDistinctMachineByCreatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Group> findDistinctMachineByUpdatedAtBetween(Date start, Date end, Pageable pageable);

	Page<Group> findDistinctMachineByDeletedAtBetween(Date start, Date end, Pageable pageable);

	// String Containing
	Page<Group> findDistinctMachineByNameContaining(String name, Pageable pageable);

	Page<Group> findDistinctMachineByNoteContaining(String note, Pageable pageable);

}
