package com.richasdy.presencesys.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
	
	Schedule save(Schedule entity);

	Schedule update(Schedule entity);

	Schedule findOne(long id);

	long count();

	void delete(long id);

	Schedule deleteSoft(long id);

	Page<Schedule> searchBy(String searchTerm, Pageable pageable);

	Page<Schedule> findAll(Pageable pageable);

}
