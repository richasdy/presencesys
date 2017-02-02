package com.richasdy.presencesys.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.util.Util;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	ScheduleRepository repository;

	@Autowired
	public ScheduleServiceImpl(ScheduleRepository repository) {
		this.repository = repository;
	}

	@Override
	public Schedule save(Schedule entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Schedule update(Schedule entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Schedule findOne(long id) {
		return repository.findOne(id);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(long id) {
		repository.delete(id);
	}

	@Override
	public Schedule deleteSoft(long id) {
		Schedule entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Page<Schedule> searchBy(String searchTerm, Pageable pageable) {
		Page<Schedule> retVal = null;

		if (searchTerm == null || searchTerm.isEmpty()) {
			retVal = repository.findAll(pageable);

		} else {
			// if search not null or empty

			// explode string
			searchTerm = searchTerm.toLowerCase();
			String[] searchSplit = searchTerm.split(":");

			// java 7 above
			switch (searchSplit[0]) {
			case "id":
				retVal = repository.findDistinctScheduleById(Long.parseLong(searchSplit[1]), pageable);
				break;

			case "idkelompok":
				retVal = repository.findDistinctScheduleByIdKelompok(Long.parseLong(searchSplit[1]), pageable);
				break;

			case "tipe":
				retVal = repository.findDistinctScheduleByTipeContaining(searchSplit[1], pageable);
				break;

			case "note":
				retVal = repository.findDistinctScheduleByNoteContaining(searchSplit[1], pageable);
				break;

			case "tanggal":
				if (Util.isValidDate(searchSplit[1])) {
					Date tanggal = Util.stringToDate(searchSplit[1]);
					// Date end = Util.stringToDate(searchSplit[1]);
					tanggal.setHours(00);
					tanggal.setMinutes(00);
					tanggal.setSeconds(00);
					// end.setHours(23);
					// end.setMinutes(59);
					// end.setSeconds(59);
					// retVal = repository.findDistinctScheduleByTanggal(start,
					// end, pageable);
					retVal = repository.findDistinctScheduleByTanggal(tanggal, pageable);
				}
				break;

			case "start":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctScheduleByCreatedAtBetween(start, end, pageable);
				}
				break;

			case "stop":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctScheduleByCreatedAtBetween(start, end, pageable);
				}
				break;
			case "createdat":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctScheduleByCreatedAtBetween(start, end, pageable);
				}
				break;

			case "updatedat":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctScheduleByUpdatedAtBetween(start, end, pageable);
				}
				break;

			case "deletedat":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctScheduleByDeletedAtBetween(start, end, pageable);
				}
				break;

			}

		}

		return retVal;
	}

	@Override
	public Page<Schedule> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Schedule findScheduleByIdKelompokAndNow(long idKelompok) {

		 return repository.findScheduleByIdKelompokAndNow(idKelompok);

		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// Date dateTime = new Date();
		// String tanggal = format.format(new Date());
		// return repository.findScheduleByIdKelompokAndNow(idKelompok, tanggal, dateTime);
	}

}
