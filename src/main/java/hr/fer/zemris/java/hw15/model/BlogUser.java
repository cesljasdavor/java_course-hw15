package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Razred koji modelira jednog korisnika ovog bloga. Razred je ujedino preslikan
 * i u tablicu baze podataka <b>blog_users</b> , koristeći
 * <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">Java Persistence
 * API</a>. Primjerci ovog razreda imaju sljedeće atribute:
 * 
 * <ul>
 * <li>id</li>
 * <li>firstName</li>
 * <li>lastName</li>
 * <li>nick</li>
 * <li>email</li>
 * <li>passwordHash</li>
 * <li>entries ({@link List} svih objava koje je ovaj korisnik napravio
 * modeliranih razredom {@link BlogEntry})</li>
 * </ul>
 * 
 * @see BlogEntry
 * 
 * @author Davor Češljaš
 */
@NamedQueries({ @NamedQuery(name = "BlogUser.all", query = "select b from BlogUser as b"),
		@NamedQuery(name = "BlogUser.by.nick", query = "select b from BlogUser as b where b.nick = :nick") })
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/**
	 * Članska varijabla koja predstavlja identifikator korisnika, ujedino i
	 * primarni ključ relacije
	 */
	private Long id;

	/** Članska varijabla koja predstavlja ime korisnike */
	private String firstName;

	/** Članska varijabla koja predstavlja prezime korisnike */
	private String lastName;

	/**
	 * Članska varijabla koja predstavlja korisnikov nadimak, ujedino i
	 * sekundarni ključ relacije
	 */
	private String nick;

	/** Članska varijabla koja predstavlja korisnikovu e-mail adresu */
	private String email;

	/** Članska varijabla koja predstavlja sažetak korisnikove lozinke */
	private String passwordHash;

	/**
	 * Članska varijabla koja predstavlja {@link List} svih objava koje je
	 * korisnik napravio, modliranih razredom {@link BlogEntry}
	 */
	private List<BlogEntry> entries;

	/**
	 * Metoda koja dohvaća identifikator korisnika, ujedino i primarni ključ
	 * relacije
	 *
	 * @return identifikator korisnika
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Metoda koja postavlja novi identifikator korisnika predan kao parametar
	 * <b>id</b>
	 *
	 * @param id
	 *            novi identifikator korisnika
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metoda koja dohvaća ime korisnike.
	 *
	 * @return ime korisnike
	 */
	@Column(length = 100, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda koja postavlja novo ime korisnike predano kao parametar
	 * <b>firstName</b>
	 *
	 * @param firstName
	 *            novo ime korisnike
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Metoda koja dohvaća prezime korisnike
	 *
	 * @return prezime korisnike
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda koja postavlja novo prezime korisnike predano kao parametar
	 * <b>lastName</b>
	 *
	 * @param lastName
	 *            novo prezime korisnika
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Metoda koja dohvaća korisnikov nadimak, ujedino i sekundarni ključ
	 * relacije
	 *
	 * @return korisnikov nadimak
	 */
	@Column(length = 100, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Metoda koja postavlja novi korisnikov nadimak predan kao parametar
	 * <b>nick</b>
	 * 
	 * @param nick
	 *            novi korisnikov nadimak
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Metoda koja dohvaća korisnikovu e-mail adresu
	 *
	 * @return korisnikovu e-mail adresu
	 */
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda koja postavlja novu korisnikovu e-mail adresu predanu kao
	 * parametar <b>email</b>
	 *
	 * @param email
	 *            nova korisnikova e-mail adresa
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda koja dohvaća sažetak korisnikove lozinke
	 *
	 * @return sažetak korisnikove lozinke
	 */
	@Column(length = 250, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Metoda koja postavlja novi sažetak korisnikove lozinke predan kao
	 * parametar <b>passwordHash</b>
	 *
	 * @param passwordHash
	 *            novi sažetak korisnikove lozinke
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Metoda koja dohvaća {@link List} svih objava koje je korisnik napravio,
	 * modliranih razredom {@link BlogEntry}
	 *
	 * @return {@link List} svih objava koje je korisnik napravio, modliranih
	 *         razredom {@link BlogEntry}
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Metoda koja postavlja novu {@link List}u svih objava koje je korisnik
	 * napravio predanu kao parametar <b>entries</b>
	 *
	 * @param entries
	 *            nova {@link List} svih objava koje je korisnik napravio
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
