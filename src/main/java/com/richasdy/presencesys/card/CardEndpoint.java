package com.richasdy.presencesys.card;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apiv1/card")
public class CardEndpoint {

	private CardService service;

	@Autowired
	public CardEndpoint(CardService service) {
		this.service = service;
	}

	@GetMapping()
	public ResponseEntity<Iterable<Card>> index() {

		Iterable<Card> iterableEntity = service.findAll();

		if (iterableEntity.iterator().hasNext()) {
			return new ResponseEntity<Iterable<Card>>(iterableEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<Card>>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Card> show(@PathVariable int id) {

		Card entity = service.findOne(id);

		if (entity == null) {
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Card>(entity, HttpStatus.OK);
		}

	}

	@PostMapping()
	public ResponseEntity<Card> save(@Valid @RequestBody Card entity) {

		Card entityValid = service.save(entity);
		return new ResponseEntity<Card>(entityValid, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Card> update(@PathVariable int id, @Valid @RequestBody Card newEntity) {

		Card currentEntity = service.findOne(id);

		if (currentEntity == null) {

			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);

		} else {

			// copy changed field into object
			// get null value field
			final BeanWrapper src = new BeanWrapperImpl(newEntity);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
			Set<String> emptyNames = new HashSet<String>();
			for (java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null || srcValue.equals(0))
					emptyNames.add(pd.getName());
			}
			String[] result = new String[emptyNames.size()];

			// BeanUtils.copyProperties with ignore field
			BeanUtils.copyProperties(newEntity, currentEntity, emptyNames.toArray(result));

			// update data
			Card entity = service.update(currentEntity);

			return new ResponseEntity<Card>(entity, HttpStatus.OK);

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Card> delete(@PathVariable int id) {


		Card entity = service.findOne(id);

		if (entity == null) {
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		} else {
			service.delete(id);
			return new ResponseEntity<Card>(HttpStatus.GONE);
		}

	}

}