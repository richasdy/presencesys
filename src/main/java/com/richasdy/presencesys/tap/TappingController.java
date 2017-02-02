package com.richasdy.presencesys.tap;

import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.richasdy.presencesys.card.Card;
import com.richasdy.presencesys.card.CardService;
import com.richasdy.presencesys.kelompok.Kelompok;
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
	private MachineService machineService;

	@Autowired
	private CardService cardService;

	@Autowired
	private UserService userService;

	@Autowired
	private KelompokService kelompokService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TapService tapService;

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

		} else if (user.getIdKelompok() == 0) {
			// USER HARUS TERASOSIASI DENGAN KELOMPOK
			return "error : user belum terasosiasi dengan kelompok";
		}

		// CHECK SCHEDULE
		// find schedule where idKelompok = user.idKelompok and now between
		// start and stop
		Schedule schedule = scheduleService.findScheduleByIdKelompokAndNow(user.getIdKelompok());
		if (schedule == null) {
			// return "error : tidak ada jadwal tapping sekarang";
			return "error : tidak ada jadwal tapping sekarang" + (new Date());
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
		Long selisihWaktu = sekarang.getTime() - schedule.getStop().getTime();
		tap.setStatus(selisihWaktu.toString());
		tap = tapService.save(tap);

		if (tap != null) {
			return "success : presensi berhasil";
		} else {
			return "error : tap gagal disimpan";
		}

	}

	// CODE FOR SIMULATION

	@GetMapping("/registerme")
	@ResponseBody
	public String regiterMyMachine(HttpServletRequest request) {

		Machine machine = machineService.findByIp(request.getRemoteAddr());
		if (machine == null) {

			Machine machineToRegister = new Machine();
			machineToRegister.setIp(request.getRemoteAddr());
			machineToRegister.setNote(
					"ip : " + request.getRemoteAddr() + " | X-FORWARDED-FOR :" + request.getHeader("X-FORWARDED-FOR"));
			machineToRegister = machineService.save(machineToRegister);

			if (machineToRegister != null) {
				return "regiter berhasil, ip machine : " + request.getRemoteAddr() + " | "
						+ request.getHeader("X-FORWARDED-FOR");
			} else {
				return "error : gagal registrasi mesin";
			}

		} else {
			return "mesin telah terregistrasi";
		}

	}

	private Machine machine;
	private Card card;
	private Kelompok kelompok;
	private User user;
	private Schedule schedule;

	@GetMapping("/cobaregistrasi")
	@ResponseBody
	public String cobaPresensi(HttpServletRequest request) {

		String retVal = "registrasi selesai, ";

		// REGISTER MACHINE
		machine = machineService.findByIp(request.getRemoteAddr());
		if (machine == null) {

			Machine machineToRegister = new Machine();
			machineToRegister.setIp(request.getRemoteAddr());
			machineToRegister.setNote(
					"ip : " + request.getRemoteAddr() + " | X-FORWARDED-FOR :" + request.getHeader("X-FORWARDED-FOR"));
			machineToRegister = machineService.save(machineToRegister);

		} else {
			retVal = retVal + "machine has registered, ";
		}

		// REGISTER CARD
		card = new Card();
		card.setCardNumber("cobaCardNumber");
		card.setNote("cobaCardNote");
		card.setActivated(true);
		card.setActivatedAt(new Date());
		card = cardService.save(card);

		if (card == null) {
			retVal = retVal + "error register card, ";
		}

		// REGISTER KELOMPOK
		kelompok = new Kelompok();
		kelompok.setNama("cobaNamaKelompok");
		kelompok.setNote("cobaNoteKelompok");
		kelompok = kelompokService.save(kelompok);

		if (kelompok == null) {
			retVal = retVal + "error register kelompok, ";
		}

		// REGISTER USER
		user = new User();
		user.setUserNumber("cobaUserNumber");
		user.setIdCard(card.getId());
		user.setIdKelompok(kelompok.getId());
		user.setNama("cobaNamaUser");
		user.setNote("cobaNoteUser");
		user = userService.save(user);

		if (user == null) {
			retVal = retVal + "error register user";
		}

		return retVal;

	}

	@GetMapping("/cobajadwal")
	@ResponseBody
	public String cobaPresensiJadwal(HttpServletRequest request) {

		String retVal = "register schedule berhasil, silahkan coba untuk 2 jam kedepan";

		// REGISTER SCHEDULE
		schedule = new Schedule();
		schedule.setIdKelompok(kelompok.getId());
		schedule.setNote("cobaNoteSchedule");
		schedule.setTipe("cobaTipeSchedule");
		schedule.setTanggal(new Date());
		schedule.setStart(new Date());
		schedule.setStop(new Date(System.currentTimeMillis() + 2 * 3600 * 1000));
		schedule = scheduleService.save(schedule);

		if (schedule == null) {
			retVal = "error register schedule";
		}

		return retVal + ", " + TimeZone.getDefault() + ", " + (new Date());

	}

	@GetMapping("/cobatap")
	// @ResponseBody
	public String cobaPresensiTap(HttpServletRequest request) {

		return "redirect:/tapping?cardNumber=" + card.getCardNumber();

	}

}
