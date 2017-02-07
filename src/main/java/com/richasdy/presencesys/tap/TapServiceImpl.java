package com.richasdy.presencesys.tap;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.util.Util;

@Service
public class TapServiceImpl implements TapService{
	
	TapRepository repository;

	@Autowired
	public TapServiceImpl(TapRepository repository) {
		this.repository = repository;
	}

	@Override
	public Tap save(Tap entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Tap update(Tap entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Tap findOne(long id) {
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
	public Tap deleteSoft(long id) {
		Tap entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Page<Tap> searchBy(String searchTerm, Pageable pageable) {
		Page<Tap> retVal = null;

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
				retVal = repository.findDistinctTapById(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "idkelompok":
				retVal = repository.findDistinctTapByIdKelompok(Long.parseLong(searchSplit[1]), pageable);
				break;

			case "iduser":
				retVal = repository.findDistinctTapByIdUser(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "idschedule":
				retVal = repository.findDistinctTapByIdSchedule(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "idcard":
				retVal = repository.findDistinctTapByIdCard(Long.parseLong(searchSplit[1]), pageable);
				break;
			
			case "idmachine":
				retVal = repository.findDistinctTapByIdMachine(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "namakelompok":
				retVal = repository.findDistinctTapByKelompokNamaContaining(searchSplit[1], pageable);
				break;
				
			case "usernumber":
				retVal = repository.findDistinctTapByUserNumberContaining(searchSplit[1], pageable);
				break;
				
			case "usernama":
				retVal = repository.findDistinctTapByUserNamaContaining(searchSplit[1], pageable);
				break;
			
			case "scheduletipe":
				retVal = repository.findDistinctTapByScheduleTipeContaining(searchSplit[1], pageable);
				break;
				
			case "schedulenote":
				retVal = repository.findDistinctTapByScheduleNoteContaining(searchSplit[1], pageable);
				break;
				
			case "scheduletanggal":
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
					retVal = repository.findDistinctTapByScheduleTanggal(tanggal, pageable);
				}
				break;
				
			case "schedulestart":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctTapByScheduleStartBetween(start, end, pageable);
				}
				break;
				
			case "schedulestop":
				if (Util.isValidDate(searchSplit[1])) {
					Date start = Util.stringToDate(searchSplit[1]);
					Date end = Util.stringToDate(searchSplit[1]);
					start.setHours(00);
					start.setMinutes(00);
					start.setSeconds(00);
					end.setHours(23);
					end.setMinutes(59);
					end.setSeconds(59);
					retVal = repository.findDistinctTapByScheduleStopBetween(start, end, pageable);
				}
				break;
				
			case "cardnumber":
				retVal = repository.findDistinctTapByCardNumberContaining(searchSplit[1], pageable);
				break;
				
			case "machineip":
				retVal = repository.findDistinctTapByMachineIpContaining(searchSplit[1], pageable);
				break;

			case "status":
				retVal = repository.findDistinctTapByStatusContaining(searchSplit[1], pageable);
				break;

			case "note":
				retVal = repository.findDistinctTapByNoteContaining(searchSplit[1], pageable);
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
					retVal = repository.findDistinctTapByCreatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctTapByUpdatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctTapByDeletedAtBetween(start, end, pageable);
				}
				break;

			}

		}

		return retVal;
	}

	@Override
	public Page<Tap> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
