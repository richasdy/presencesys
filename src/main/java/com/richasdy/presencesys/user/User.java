package com.richasdy.presencesys.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(unique = true, nullable = false)
	// number cant use @notEmpty Validator
	// @NotEmpty // validator
	private long idCard;
	
	@Column(unique = true, nullable = false)
	// number cant use @notEmpty Validator
	// @NotEmpty // validator
	private long idKelompok;
	
	@Column(nullable = false)
	private String userNumber;

	@Column(nullable = false)
	@NotEmpty // validator
	private String nama;

	private String note;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedAt;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(long id, long idCard, long idKelompok, String userNumber, String nama, String note, Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.id = id;
		this.idCard = idCard;
		this.idKelompok = idKelompok;
		this.userNumber = userNumber;
		this.nama = nama;
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

	public long getIdCard() {
		return idCard;
	}

	public void setIdCard(long idCard) {
		this.idCard = idCard;
	}
	
	public long getIdKelompok() {
		return idKelompok;
	}

	public void setIdKelompok(long idKelompok) {
		this.idKelompok = idKelompok;
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
		return this.getClass().getName() + " [ " + id + ", " + idCard + ", " + userNumber + ", " +nama + ", " + note + ", " + createdAt
				+ ", " + updatedAt + ", " + deletedAt + " ]";
	}

}
