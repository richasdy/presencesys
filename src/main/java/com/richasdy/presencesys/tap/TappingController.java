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
import com.richasdy.presencesys.schedule.Schedule;
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
		User user = userService.findByIdCard(card.getId());
		
		if (user == null) {
			// USER HARUS TERASOSIASI DENGAN KARTU
			return "error : kartu belum terasosiasi dengan user";
			
		} else if (user.getIdKelompok()==0) {
			// USER HARUS TERASOSIASI DENGAN KELOMPOK
			return "error : user belum terasosiasi dengan kelompok";
		} 
		
		// CHECK SCHEDULE
		// find schedule where idKelompok = user.idKelompok and now between start and stop
		Schedule schedule = scheduleService.findScheduleByIdKelompokAndNow(user.getIdKelompok());
		if (schedule == null) {
			return "error : tidak ada jadwal tapping sekarang";
		}
		
		// SAVE DATA TO TAP
		Tap tap = new Tap();
		tap.setIdSchedule(schedule.getId());
		tap.setIdUser(user.getId());
		tap.setIdKelompok(user.getIdKelompok());
		tap.setScheduleTanggal(schedule.getTanggal());
		tap.setScheduleStart(schedule.getStart());
		tap.setScheduleStop(schedule.getStop());
		tap.setUserNumber(user.getUserNumber());
		tap.setScheduleTipe(schedule.getTipe());
		tap.setNama(user.getNama());
		
		Date sekarang = new Date();
		Long selisihWaktu = sekarang.getTime()-schedule.getStop().getTime();
		tap.setStatus(selisihWaktu.toString());
		tap = tapService.save(tap);
		
		if (tap != null){
			return "success : presensi berhasil";
		} else {
			return "error : tap gagal disimpan";
		}
		
	}
	
	@GetMapping("/registerme")
	@ResponseBody
	public String regiterMyMachine(HttpServletRequest request) {
		
		Machine machine = machineService.findByIp(request.getRemoteAddr());
		if (machine == null){
			
			Machine machineToRegister = new Machine();
			machineToRegister.setIp(request.getRemoteAddr());
			machineToRegister.setNote("ip : "+request.getRemoteAddr()+" | X-FORWARDED-FOR :"+request.getHeader("X-FORWARDED-FOR"));
			machineToRegister = machineService.save(machineToRegister);
			
			if(machineToRegister!=null){
				return "regiter berhasil, ip machine : "+request.getRemoteAddr() +" | "+ request.getHeader("X-FORWARDED-FOR");
			} else {
				return "error : gagal registrasi mesin";
			}
			
		} else {
			return "mesin telah terregistrasi";
		}
		
		
	}

}
