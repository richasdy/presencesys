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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.service.AccountService;

@Controller
@RequestMapping("account")
public class AccountController {

	AccountService service;

	@Autowired
	public AccountController(AccountService service) {
		this.service = service;
	}

	@GetMapping()
	public String index(Model model) {

		Iterable<Account> listEntity = service.findAll();

		model.addAttribute("listEntity", listEntity);
		model.addAttribute("pageName", "Tabel Account");
		model.addAttribute("pageNameDesc", "Daftar Akun Akses System");

		return "account/index";
	}

	@GetMapping("/create")
	public String create(Model model) {

		model.addAttribute("account", new Account());

		model.addAttribute("pageName", "Account Baru");
		model.addAttribute("pageNameDesc", "Daftar Isian Akun");

		return "account/create";
	}

	@PostMapping()
	public String save(@Valid Account entity, BindingResult result) {

		// debug
		// System.out.println("ganteng");
		// System.out.println(result.toString());
		// System.out.println(entity.toString());

		if (result.hasErrors()) {

			// has error
			return "account/create";

		} else {

			// check activated untuk assign activatedAt
			// entity.getActivated() == true/false --> saat real
			// entity.getActivated() == null --> saat testing,
			// karena nilainya null, bukan hanya true/false
			if (entity.getActivated() == null || entity.getActivated()) {
				entity.setActivatedAt(new Date());

			}

			Account confirm = service.save(entity);

			return "redirect:/account/" + confirm.getId();
		}

	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable int id) {

		Account entity = service.findOne(id);

		// debug
		// System.out.println("id @show" + id);
		// System.out.println("ganteng" + account);

		model.addAttribute("entity", entity);
		model.addAttribute("pageName", "Account Detail");
		model.addAttribute("pageNameDesc", "Detail Data Akun");

		return "account/show";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {

		Account entity = service.findOne(id);

		model.addAttribute("account", entity);
		model.addAttribute("pageName", "Account Edit");
		model.addAttribute("pageNameDesc", "Detail Perubahan Data Akun");

		return "account/edit";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable int id, @Valid Account updatedEntity, BindingResult result) {

		if (result.hasErrors() || id != updatedEntity.getId()) {

			// VULNURABLE
			// ada kemungkinan dihack
			// merubah data account dengan id = id
			// tapi updatedAccount.getId() nya berbeda
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

			return "account/edit";

		} else {

			// debug
			// System.out.println("im here");
			// System.out.println(result);
			// System.out.println(updatedAccount.toString());

			Account currentEntity = service.findOne(id);

			// debug
			// System.out.println(currentAccount.toString());

			// check perubahan
			// check activated untuk assign activatedAt
			// account.getActivated() == true/false --> saat real
			// account.getActivated() == null --> saat testing,
			// karena nilainya null, bukan hanya true/false
			if (updatedEntity.getActivated() == null || updatedEntity.getActivated() == false) {
				updatedEntity.setActivatedAt(null);
				currentEntity.setActivatedAt(null);
			} else {
				updatedEntity.setActivatedAt(new Date());

				// memastikan saat diaktifkan, deletedAt di kembalikan null
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

			return "redirect:/account/" + currentEntity.getId();

		}

	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable int id) {

		// tidak terpakai
		// accountService.deleteSoft(id);

		Account currentEntity = service.findOne(id);
		currentEntity.setActivated(false);
		currentEntity.setActivatedAt(null);
		currentEntity.setUpdatedAt(new Date());
		currentEntity.setDeletedAt(new Date());

		service.save(currentEntity);

		return "redirect:/account";

	}

	@GetMapping("/search")
	public String search(@RequestParam String q, Model model) {

		// Iterable<Account> listEntity = service.search(q);
		Iterable<Account> listEntity = service.searchBy(q);

		model.addAttribute("q", q);
		model.addAttribute("listEntity", listEntity);
		model.addAttribute("pageName", "Tabel Account Pencarian : " + q);
		model.addAttribute("pageNameDesc", "Daftar Akun Akses System");

		return "account/index";
	}

}
