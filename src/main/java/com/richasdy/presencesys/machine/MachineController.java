package com.richasdy.presencesys.machine;

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

import com.richasdy.presencesys.machine.Machine;
import com.richasdy.presencesys.machine.MachineService;

@Controller
@RequestMapping("machine")
public class MachineController {

	MachineService service;

	@Autowired
	public MachineController(MachineService service) {
		this.service = service;
	}

	@GetMapping()
	public String index(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pagination = new PageRequest(page, size);
		Page<Machine> pageEntity = service.findAll(pagination);

		model.addAttribute("listEntity", pageEntity);
		model.addAttribute("pageName", "Tabel Machine");
		model.addAttribute("pageNameDesc", "Daftar Akun Akses System");

		return "machine/index";
	}


	@GetMapping("/create")
	public String create(Model model) {
		
		// kemungkinan error disini

		model.addAttribute("entity", new Machine());

		model.addAttribute("pageName", "Machine Baru");
		model.addAttribute("pageNameDesc", "Daftar Isian Akun");

		return "machine/create";
	}

	@PostMapping()
	public String save(@Valid Machine entity, BindingResult result) {

		// debug
		// System.out.println("ganteng");
		// System.out.println(result.toString());
		// System.out.println(entity.toString());

		if (result.hasErrors()) {

			// has error
			return "machine/create";

		} else {

			Machine confirm = service.save(entity);

			return "redirect:/machine/" + confirm.getId();
		}

	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {

		Machine entity = service.findOne(id);

		// debug
		// System.out.println("id @show" + id);
		// System.out.println("ganteng" + account);

		model.addAttribute("entity", entity);
		model.addAttribute("pageName", "Machine Detail");
		model.addAttribute("pageNameDesc", "Detail Data Akun");

		return "machine/show";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {

		Machine entity = service.findOne(id);

		// kemungkinan error disini
		model.addAttribute("entity", entity);
		model.addAttribute("pageName", "Machine Edit");
		model.addAttribute("pageNameDesc", "Detail Perubahan Data Akun");

		return "machine/edit";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable int id, @Valid Machine updatedEntity, BindingResult result) {

		if (result.hasErrors() || id != updatedEntity.getId()) {

			// VULNURABLE
			// ada kemungkinan dihack
			// merubah data account dengan id = id
			// tapi updatedMachine.getId() nya berbeda
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

			return "machine/edit";

		} else {

			// debug
			// System.out.println("im here");
			// System.out.println(result);
			// System.out.println(updatedMachine.toString());

			Machine currentEntity = service.findOne(id);
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

			return "redirect:/machine/" + currentEntity.getId();

		}

	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable int id) {

		// tidak terpakai
		// service.deleteSoft(id);

		Machine currentEntity = service.findOne(id);
		currentEntity.setUpdatedAt(new Date());
		currentEntity.setDeletedAt(new Date());

		service.save(currentEntity);

		return "redirect:/machine";

	}

	@GetMapping("/search")
	public String search(@RequestParam String q, Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pagination = new PageRequest(page, size);
		Page<Machine> pageEntity = service.searchBy(q, pagination);

		model.addAttribute("q", q);
		model.addAttribute("listEntity", pageEntity);
		model.addAttribute("pageName", "Tabel Machine Pencarian : " + q);
		model.addAttribute("pageNameDesc", "Daftar Akun Akses System");

		return "machine/index";
	}

}
