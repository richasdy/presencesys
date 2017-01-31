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
	private long idSchedule;

	@Column(nullable = false)
	private long idUser;

	@Column(nullable = false)
	private long idKelompok;

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
	@NotEmpty // validator
	private String scheduleTipe;

	@Column(nullable = false)
	private String userNumber;

	@Column(nullable = false)
	private String nama;

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

	public Tap(long id, long idSchedule, long idUser, long idKelompok, Date scheduleTanggal, Date scheduleStart,
			Date scheduleStop, String userNumber, String scheduleTipe, String nama, String status, String note,
			Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.id = id;
		this.idSchedule = idSchedule;
		this.idUser = idUser;
		this.idKelompok = idKelompok;
		this.scheduleTanggal = scheduleTanggal;
		this.scheduleStart = scheduleStart;
		this.scheduleStop = scheduleStop;
		this.scheduleTipe = scheduleTipe;
		this.userNumber = userNumber;
		this.nama = nama;
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

	public long getIdSchedule() {
		return idSchedule;
	}

	public void setIdSchedule(long idSchedule) {
		this.idSchedule = idSchedule;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdKelompok() {
		return idKelompok;
	}

	public void setIdKelompok(long idKelompok) {
		this.idKelompok = idKelompok;
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

	public String getScheduleTipe() {
		return scheduleTipe;
	}

	public void setScheduleTipe(String kelompokTipe) {
		this.scheduleTipe = kelompokTipe;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
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
		return this.getClass().getName() + " [ " + id + ", " + idSchedule + ", " + idUser + ", " + idKelompok + ", "
				+ scheduleTanggal + ", " + scheduleStart + ", " + scheduleStop + ", " + userNumber + ", " + scheduleTipe
				+ ", " + nama + ", " + ", " + status + ", " + ", " + note + ", " + createdAt + ", " + updatedAt + ", "
				+ deletedAt + " ]";
	}

}
