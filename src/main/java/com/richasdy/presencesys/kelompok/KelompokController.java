package com.richasdy.presencesys.kelompok;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.richasdy.presencesys.kelompok.Kelompok;
import com.richasdy.presencesys.kelompok.KelompokService;

@Controller
@RequestMapping("kelompok")
public class KelompokController {

	KelompokService service;

	@Autowired
	public KelompokController(KelompokService service) {
		this.service = service;
	}

	@GetMapping()
	public String index(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pagination = new PageRequest(page, size);
		Page<Kelompok> pageEntity = service.findAll(pagination);

		model.addAttribute("listEntity", pageEntity);
		model.addAttribute("pageName", "Tabel Kelompok");
		model.addAttribute("pageNameDesc", "Daftar Kelompok System");

		return "kelompok/index";
	}

	@GetMapping("/create")
	public String create(Model model) {

		// ubah kelompok jadi entity biar universal

		model.addAttribute("kelompok", new Kelompok());

		model.addAttribute("pageName", "Kelompok Baru");
		model.addAttribute("pageNameDesc", "Daftar Isian Kelompok");

		return "kelompok/create";
	}

	@PostMapping()
	public String save(@Valid Kelompok entity, BindingResult result) {

		// debug
		// System.out.println("ganteng");
		// System.out.println(result.toString());
		// System.out.println(entity.toString());

		if (result.hasErrors()) {

			// has error
			return "kelompok/create";

		} else {

			Kelompok confirm = service.save(entity);

			return "redirect:/kelompok/" + confirm.getId();
		}

	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {

		Kelompok entity = service.findOne(id);

		// debug
		// System.out.println("id @show" + id);
		// System.out.println("ganteng" + account);

		model.addAttribute("entity", entity);
		model.addAttribute("pageName", "Kelompok Detail");
		model.addAttribute("pageNameDesc", "Detail Data Kelompok");

		return "kelompok/show";
	}

	@GetMapping("/{id}/edit")
	// @ResponseBody
	public String edit(Model model, @PathVariable int id) {

		Kelompok entity = service.findOne(id);

		// kemungkinan error disini
		model.addAttribute("kelompok", entity);
		model.addAttribute("pageName", "Kelompok Edit");
		model.addAttribute("pageNameDesc", "Detail Perubahan Data Kelompok");

		return "kelompok/edit";
		// return new Kelompok();
		// return service.findOne(id);
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable int id, @Valid Kelompok updatedEntity, BindingResult result) {

		if (result.hasErrors() || id != updatedEntity.getId()) {

			// VULNURABLE
			// ada kemungkinan dihack
			// merubah data account dengan id = id
			// tapi updatedKelompok.getId() nya berbeda
			// pakai postman

			// SOLUSI
			// masukkan id sebagai hidden input
			// check PathVariable dan hidden input id harus sama, baru proses
			// update

			// DONE

			// VULNURABLE
			// manual edit id target post dan manual edit id hidden input id

			// SOLUSI
			// id ambil dari session

			return "kelompok/edit";

		} else {

			// debug
			// System.out.println("im here");
			// System.out.println(result);
			// System.out.println(updatedKelompok.toString());

			Kelompok currentEntity = service.findOne(id);
			currentEntity.setUpdatedAt(new Date());

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

			return "redirect:/kelompok/" + currentEntity.getId();

		}

	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable int id) {

		// tidak terpakai
		// service.deleteSoft(id);

		// Kelompok currentEntity = service.findOne(id);
		// currentEntity.setUpdatedAt(new Date());
		// currentEntity.setDeletedAt(new Date());
		//
		// service.save(currentEntity);
		
		service.delete(id);

		return "redirect:/kelompok";

	}

	@GetMapping("/search")
	public String search(@RequestParam String q, Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pagination = new PageRequest(page, size);
		Page<Kelompok> pageEntity = service.searchBy(q, pagination);

		model.addAttribute("q", q);
		model.addAttribute("listEntity", pageEntity);
		model.addAttribute("pageName", "Tabel Kelompok Pencarian : " + q);
		model.addAttribute("pageNameDesc", "Daftar Kelompok System");

		return "kelompok/index";
	}

}
