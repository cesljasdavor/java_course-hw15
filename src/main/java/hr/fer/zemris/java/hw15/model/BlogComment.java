package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred modelira komentar na objavu oblikovanu razredom {@link BlogEntry}.
 * Razred je ujedino preslikan i u tablicu baze podataka <b>blog_comments</b> ,
 * koristeći <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">Java
 * Persistence API</a>. Primjerci ovog razreda imaju sljedeće atribute:
 * <ul>
 * <li>id</li>
 * <li>blogEntry (strani ključ na primjerak razreda {@link BlogEntry})</li>
 * <li>userEMail</li>
 * <li>message</li>
 * <li>postedOn</li>
 * </ul>
 * Jedna objava može sadržavati više komentara.
 * 
 * @see BlogEntry
 * 
 * @author Davor Češljaš
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Članska varijabla koja predstavlja identifikator komentara, ujedino i
	 * primarni ključ relacije
	 */
	private Long id;

	/**
	 * Članska varijabla koja predstavlja primjerak razreda {@link BlogEntry}
	 * kojem ovaj komentar pripada
	 */
	private BlogEntry blogEntry;

	/**
	 * Članska varijabla koja predstavlja e-mail adresu korisnika koje je
	 * postavio komentar
	 */
	private String usersEMail;

	/** Članska varijabla koja predstavlja sadržaj ovog komentara */
	private String message;

	/**
	 * Članska varijabla koja predstavlja datum i vrijeme kada je ovaj komentar
	 * stvoren
	 */
	private Date postedOn;

	/**
	 * Metoda koja dohvaća identifikator komentara, ujedino i primarni ključ
	 * relacije
	 *
	 * @return identifikator komentara, ujedino i primarni ključ relacije
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Metoda koja postavlja novi identifikator komentara predan kao parametar
	 * <b>id</b>
	 *
	 * @param id
	 *            novi identifikator komentara
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metoda koja dohvaća primjerak razreda {@link BlogEntry} kojem ovaj
	 * komentar pripada
	 *
	 * @return primjerak razreda {@link BlogEntry} kojem ovaj komentar pripada
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Metoda koja postavlja novi primjerak razreda {@link BlogEntry} kojem ovaj
	 * komentar pripada predan kao parametar <b>blogEntry</b>
	 *
	 * @param blogEntry
	 *            novi primjerak razreda {@link BlogEntry} kojem ovaj komentar
	 *            pripada
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Metoda koja dohvaća e-mail adresu korisnika koje je postavio komentar
	 *
	 * @return e-mail adresu korisnika koje je postavio komentar
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Metoda koja postavlja novu e-mail adresu korisnika koje je postavio
	 * komentar, predanu kao <b>usersEMail</b>
	 *
	 * @param usersEMail
	 *            e-mail adresu korisnika koje je postavio komentar
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Metoda koja dohvaća sadržaj ovog komentara
	 *
	 * @return sadržaj ovog komentara
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Metoda koja postavlja novi sadržaj ovog komentara predan kao parametar
	 * <b>message</b>
	 *
	 * @param message
	 *            novi sadržaj ovog komentara
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Metoda koja dohvaća datum i vrijeme kada je ovaj komentar stvoren
	 *
	 * @return datum i vrijeme kada je ovaj komentar stvoren
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Metoda koja postavlja novi datum i vrijeme kada je ovaj komentar stvoren
	 * predan kao parametar <b>postedOn</b>
	 *
	 * @param postedOn
	 *            datum i vrijeme kada je ovaj komentar stvoren
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}