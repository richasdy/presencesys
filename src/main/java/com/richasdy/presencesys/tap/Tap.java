package com.richasdy.presencesys.tap;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Tap {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(nullable = false)
	private long idKelompok;

	@Column(nullable = false)
	private long idUser;

	@Column(nullable = false)
	private long idSchedule;

	@Column(nullable = false)
	private long idCard;

	@Column(nullable = false)
	private long idMachine;

	@Column(nullable = false)
	private String kelompokNama;

	@Column(nullable = false)
	private String userNumber;

	@Column(nullable = false)
	private String userNama;

	@Column(nullable = false)
	@NotEmpty // validator
	private String scheduleTipe;

	@Column(nullable = false)
	private String scheduleNote;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date scheduleTanggal;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date scheduleStart;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date scheduleStop;

	@Column(nullable = false)
	private String cardNumber;

	@Column(nullable = false)
	private String machineIp;

	@Column(nullable = false)
	@NotEmpty // validator
	private String status;

	private String note;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public Tap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tap(long id, long idKelompok, long idUser, long idSchedule, long idCard, long idMachine, String kelompokNama,
			String userNumber, String userNama, String scheduleTipe, String scheduleNote, Date scheduleTanggal,
			Date scheduleStart, Date scheduleStop, String cardNumber, String machineIp, String status, String note,
			Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.id = id;
		this.idKelompok = idKelompok;
		this.idUser = idUser;
		this.idSchedule = idSchedule;
		this.idCard = idCard;
		this.idMachine = idMachine;
		this.kelompokNama = kelompokNama;
		this.userNumber = userNumber;
		this.userNama = userNama;
		this.scheduleTipe = scheduleTipe;
		this.scheduleNote = scheduleNote;
		this.scheduleTanggal = scheduleTanggal;
		this.scheduleStart = scheduleStart;
		this.scheduleStop = scheduleStop;
		this.cardNumber = cardNumber;
		this.machineIp = machineIp;
		this.status = status;
		this.note = note;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdKelompok() {
		return idKelompok;
	}

	public void setIdKelompok(long idKelompok) {
		this.idKelompok = idKelompok;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdSchedule() {
		return idSchedule;
	}

	public void setIdSchedule(long idSchedule) {
		this.idSchedule = idSchedule;
	}

	public long getIdCard() {
		return idCard;
	}

	public void setIdCard(long idCard) {
		this.idCard = idCard;
	}

	public long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(long idMachine) {
		this.idMachine = idMachine;
	}

	public String getKelompokNama() {
		return kelompokNama;
	}

	public void setKelompokNama(String kelompokNama) {
		this.kelompokNama = kelompokNama;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserNama() {
		return userNama;
	}

	public void setUserNama(String userNama) {
		this.userNama = userNama;
	}

	public String getScheduleTipe() {
		return scheduleTipe;
	}

	public void setScheduleTipe(String scheduleTipe) {
		this.scheduleTipe = scheduleTipe;
	}

	public String getScheduleNote() {
		return scheduleNote;
	}

	public void setScheduleNote(String scheduleNote) {
		this.scheduleNote = scheduleNote;
	}

	public Date getScheduleTanggal() {
		return scheduleTanggal;
	}

	public void setScheduleTanggal(Date scheduleTanggal) {
		this.scheduleTanggal = scheduleTanggal;
	}

	public Date getScheduleStart() {
		return scheduleStart;
	}

	public void setScheduleStart(Date scheduleStart) {
		this.scheduleStart = scheduleStart;
	}

	public Date getScheduleStop() {
		return scheduleStop;
	}

	public void setScheduleStop(Date scheduleStop) {
		this.scheduleStop = scheduleStop;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public String toString() {

		return this.getClass().getName() + " [ " + id + ", " + idKelompok + ", " + idUser + ", " + idSchedule + ", "
				+ idCard + ", " + idMachine + ", " + kelompokNama + ", " + userNumber + ", " + userNama + ", "
				+ scheduleTipe + ", " + scheduleNote + ", " + scheduleTanggal + ", " + scheduleStart + ", "
				+ scheduleStop + ", " + idUser + ", " + idKelompok + ", " + scheduleTanggal + ", " + scheduleStart
				+ ", " + scheduleStop + ", " + cardNumber + ", " + machineIp + ", " + status + ", " + ", " + note
				+ createdAt + ", " + updatedAt + ", " + deletedAt + " ]";
	}

}
