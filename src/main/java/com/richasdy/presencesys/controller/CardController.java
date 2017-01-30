package com.richasdy.presencesys.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richasdy.presencesys.domain.Card;
import com.richasdy.presencesys.service.CardService;

@Controller
@RequestMapping("card")
public class CardController {

	CardService service;

	@Autowired
	public CardController(CardService service) {
		this.service = service;
	}

	@GetMapping()
	public String index(Model model) {

		Iterable<Card> listEntity = service.findAll();

		model.addAttribute("listEntity", listEntity);
		model.addAttribute("pageName", "Tabel Kartu");
		model.addAttribute("pageNameDesc", "Daftar Kartu Akses System");

		return "card/index";
	}

	@GetMapping("/create")
	public String create(Model model) {

		model.addAttribute("card", new Card());

		model.addAttribute("pageName", "Kartu Baru");
		model.addAttribute("pageNameDesc", "Daftar Isian Kartu");

		return "card/create";
	}

	@PostMapping()
	public String save(@Valid Card entity, BindingResult result) {
		

		if (result.hasErrors()) {
			
			return "card/create";
			
		} else {
			
			if (entity.getActivated() == null || entity.getActivated()) {
				entity.setActivatedAt(new Date());
				
			}

			Card confirm = service.save(entity);

			return "redirect:/card/" + confirm.getId();
		}

	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {

		Card entity = service.findOne(id);

		model.addAttribute("entity", entity);
		model.addAttribute("pageName", "Kartu Detail");
		model.addAttribute("pageNameDesc", "Detail Data Kartu");

		return "card/show";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {

		Card entity = service.findOne(id);

		model.addAttribute("card", entity);
		model.addAttribute("pageName", "Kartu Edit");
		model.addAttribute("pageNameDesc", "Detail Perubahan Data Kartu");

		return "card/edit";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable int id, @Valid Card updatedEntity, BindingResult result) {

		if (result.hasErrors() || id!=updatedEntity.getId()) {

			return "card/edit";

		} else {

			Card currentEntity = service.findOne(id);

			if (updatedEntity.getActivated() == null || updatedEntity.getActivated() == false) {
				updatedEntity.setActivatedAt(null);
				currentEntity.setActivatedAt(null);
			} else {
				updatedEntity.setActivatedAt(new Date());

				updatedEntity.setDeletedAt(null);
				currentEntity.setDeletedAt(null);
			}

			// copy changed field into object
			// get null value field
			final BeanWrapper src = new BeanWrapperImpl(updatedEntity);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
			Set<String> emptyNames = new HashSet<String>();
			for (java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null || srcValue.equals(0))
					emptyNames.add(pd.getName());
			}
			String[] resultNullField = new String[emptyNames.size()];

			// BeanUtils.copyProperties with ignore field
			BeanUtils.copyProperties(updatedEntity, currentEntity, emptyNames.toArray(resultNullField));

			service.update(currentEntity);

			return "redirect:/card/" + currentEntity.getId();

		}

	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable int id) {

		// tidak terpakai
		// accountService.deleteSoft(id);

		// Card currentEntity = service.findOne(id);
		// currentEntity.setActivated(false);
		// currentEntity.setActivatedAt(null);
		// currentEntity.setUpdatedAt(new Date());
		// currentEntity.setDeletedAt(new Date());
		//
		// service.save(currentEntity);
		
		service.delete(id);

		return "redirect:/card";

	}

}
