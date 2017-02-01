package com.richasdy.presencesys.tap;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.assertj.core.util.Lists;
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

import com.richasdy.presencesys.machine.Machine;
import com.richasdy.presencesys.machine.MachineService;
import com.richasdy.presencesys.tap.Tap;
import com.richasdy.presencesys.tap.TapService;

@Controller
@RequestMapping("tapping")
public class TappingController {

	TapService tapService;
	MachineService machineService;

	@Autowired
	public TappingController(TapService tapService, MachineService machineService) {
		this.tapService = tapService;
		this.machineService = machineService;
	}

	@GetMapping()
	@ResponseBody
	public String index(@RequestParam(defaultValue = "0") String cardNumber, HttpServletRequest request) {

		String retVal = null;

		// System.out.println("@controller : "+request.getRemoteAddr());
		// System.out.println("@controller :
		// "+request.getHeader("X-FORWARDED-FOR"));

		// CHECK REGISTERED MACHINE
//		String searchTermMachine = "ip:" + request.getRemoteAddr();
//		Page<Machine> pageMachine = machineService.searchBy(searchTermMachine, new PageRequest(0, 1));
//		List listMachine = Lists.newArrayList(pageMachine);
//
//		if (listMachine.size() == 0) {
//			return "error : perangkat tidak terdaftar";
//		}

		
		// CHECK EMPTY CARDNUMBER
		if (cardNumber.equals("0")) {
			return "error : cardNumber tidak boleh kosong";
		}

		
		// CHECK REGISTERED CARD
//		String searchTermCard = "cardnumber:" + cardNumber;
//		Page<Machine> pageCard = machineService.searchBy(searchTermCard, new PageRequest(0, 1));
//		List listCard = Lists.newArrayList(pageCard);
//
//		if (listCard.size() == 0) {
//			return "error : kartu tidak terdaftar";
//		}

		return cardNumber;
	}

}
