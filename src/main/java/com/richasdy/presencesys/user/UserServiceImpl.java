package com.richasdy.presencesys.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.util.Util;

@Service
public class UserServiceImpl implements UserService{
	
	UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User save(User entity) {
		entity.setCreatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public User update(User entity) {
		entity.setUpdatedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public User findOne(long id) {
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
	public User deleteSoft(long id) {
		User entity = repository.findOne(id);
		entity.setDeletedAt(new Date());
		return repository.save(entity);
	}

	@Override
	public Page<User> searchBy(String searchTerm, Pageable pageable) {
		Page<User> retVal = null;

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
				retVal = repository.findDistinctUserById(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "idcard":
				retVal = repository.findDistinctUserByIdCard(Long.parseLong(searchSplit[1]), pageable);
				break;
				
			case "usernumber":
				retVal = repository.findDistinctUserByUserNumberContaining(searchSplit[1], pageable);
				break;

			case "nama":
				retVal = repository.findDistinctUserByNamaContaining(searchSplit[1], pageable);
				break;

			case "note":
				retVal = repository.findDistinctUserByNoteContaining(searchSplit[1], pageable);
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
					retVal = repository.findDistinctUserByCreatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctUserByUpdatedAtBetween(start, end, pageable);
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
					retVal = repository.findDistinctUserByDeletedAtBetween(start, end, pageable);
				}
				break;

			}

		}

		return retVal;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
