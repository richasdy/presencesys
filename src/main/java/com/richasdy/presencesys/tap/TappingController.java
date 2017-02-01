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

import com.richasdy.presencesys.card.Card;
import com.richasdy.presencesys.card.CardService;
import com.richasdy.presencesys.kelompok.KelompokService;
import com.richasdy.presencesys.machine.Machine;
import com.richasdy.presencesys.machine.MachineService;
import com.richasdy.presencesys.schedule.ScheduleService;
import com.richasdy.presencesys.tap.Tap;
import com.richasdy.presencesys.tap.TapService;
import com.richasdy.presencesys.user.User;
import com.richasdy.presencesys.user.UserService;

@Controller
@RequestMapping("tapping")
public class TappingController {

	
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	CardService cardService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	KelompokService kelompokService;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	TapService tapService;

	@GetMapping()
	@ResponseBody
	public String index(@RequestParam(defaultValue = "0") String cardNumber, HttpServletRequest request) {

		String retVal = null;

		// System.out.println("@controller : "+request.getRemoteAddr());
		// System.out.println("@controller :
		// "+request.getHeader("X-FORWARDED-FOR"));

		// CHECK REGISTERED MACHINE
		Machine machine = machineService.findByIp(request.getRemoteAddr());
		// System.out.println(machine.toString());
		if (machine == null) {
			return "error : perangkat tidak terdaftar";
		}

		// CHECK EMPTY CARDNUMBER
		if (cardNumber.equals("0")) {
			return "error : cardNumber tidak boleh kosong";
		}

		// CHECK REGISTERED CARD
		Card card = cardService.findByCardNumber(cardNumber);
		// System.out.println(machine.toString());
		if (card == null) {
			return "error : kartu tidak terdaftar";
		}

		// CHECK ACTIVE CARD
		if (card.getActivated() == false) {
			return "error : kartu belum aktif";
		}

		// CHECK USER
		// USER TERASOSIASI DENGAN KARTU
		User user = userService.findByIdCard(card.getId());
		if (user == null) {
			return "error : kartu belum terasosiasi dengan user";
		}
		
		// CHECK KELOMPOK
		
		// CHECK SCHEDULE
		
		// SAVE DATA TO TAP
		
		return cardNumber;
	}

}
