package com.richasdy.presencesys.group;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.util.Util;

@Service
public class GroupServiceImpl implements GroupService{
	
	GroupRepository repository;

	@Autowired
	public GroupServiceImpl(GroupRepository repository) {
		this.repository = repository;
	}

	@Override
	public Group save(Group entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Group update(Group entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Group findOne(long id) {
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
	public Group deleteSoft(long id) {
		Group entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Page<Group> searchBy(String searchTerm, Pageable pageable) {
		Page<Group> retVal = null;

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
				retVal = repository.findDistinctMachineById(Integer.parseInt(searchSplit[1]), pageable);
				break;

			case "name":
				retVal = repository.findDistinctMachineByNameContaining(searchSplit[1], pageable);
				break;

			case "note":
				retVal = repository.findDistinctMachineByNoteContaining(searchSplit[1], pageable);
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
					retVal = repository.findDistinctMachineByCreatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctMachineByUpdatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctMachineByDeletedAtBetween(start, end, pageable);
				}
				break;

			}

		}

		return retVal;
	}

	@Override
	public Page<Group> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
