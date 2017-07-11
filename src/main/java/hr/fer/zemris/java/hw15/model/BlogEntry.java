package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred koji modelira jednu objavu korisnika modeliranog razredom
 * {@link BlogUser}. Razred je ujedino preslikan i u tablicu baze podataka
 * <b>blog_entries</b> , koristeći
 * <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">Java Persistence
 * API</a>. Primjerci ovog razreda imaju sljedeće atribute:
 * <ul>
 * <li>id</li>
 * <li>comments (lista svih komentara na ovu objavu oblikovanih razredom
 * {@link BlogComment})</li>
 * <li>createdAt</li>
 * <li>lastModifiedAt</li>
 * <li>title</li>
 * <li>text</li>
 * <li>creator (strani ključ na primjerak razreda {@link BlogUser})</li>
 * </ul>
 * 
 * Jedan korisnik može imati više objava
 * 
 * @see BlogComment
 * @see BlogUser
 * 
 * @author Davor Češljaš
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Članska varijabla koja predstavlja identifikator objave, ujedino i
	 * primarni ključ relacije
	 */
	private Long id;

	/**
	 * Članska varijabla koja predstavlja {@link List} svih komentara na ovu
	 * objavu modeliranih razredom {@link BlogComment}
	 */
	private List<BlogComment> comments = new ArrayList<>();

	/** Članska varijabla koja predstavlja datum i vrijeme stvaranja objave */
	private Date createdAt;

	/** Članska varijabla koja predstavlja datum i vrijeme posljednje izmjene */
	private Date lastModifiedAt;

	/** Članska varijabla koja predstavlja naslov objave */
	private String title;

	/** Članska varijabla koja predstavlja sadržaj objave */
	private String text;

	/**
	 * Članska varijabla koja predstavlja primjerak razreda {@link BlogUser}
	 * kojem ova objava pripada
	 */
	private BlogUser creator;

	/**
	 * Metoda koja dohvaća identifikator objave, ujedino i primarni ključ
	 * relacije
	 *
	 * @return identifikator objave
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Metoda koja postavlja novi identifikator objave predan kao parametar
	 * <b>id</b>
	 *
	 * @param id
	 *            novi identifikator objave
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metoda koja dohvaća {@link List} svih komentara na ovu objavu modeliranih
	 * razredom {@link BlogComment}
	 *
	 * @return {@link List} svih komentara na ovu objavu modeliranih razredom
	 *         {@link BlogComment}
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn DESC")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Metoda koja postavlja novu {@link List}u svih komentara na ovu objavu
	 * modeliranih razredom {@link BlogComment} predanu kao parametar
	 * <b>comments</b>
	 *
	 * @param comments
	 *            nova {@link List}a svih komentara na ovu objavu modeliranih
	 *            razredom {@link BlogComment}
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Metoda koja dohvaća datum i vrijeme stvaranja objave
	 *
	 * @return datum i vrijeme stvaranja objave
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Metoda koja postavlja novi datum i vrijeme stvaranja objave predan kao
	 * parametar <b>createdAt</b>
	 *
	 * @param createdAt
	 *            novi datum i vrijeme stvaranja objave
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Metoda koja dohvaća datum i vrijeme posljednje izmjene
	 *
	 * @return datum i vrijeme posljednje izmjene
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Metoda koja postavlja novi datum i vrijeme posljednje izmjene predan kao
	 * parametar <b>lastModifiedAt</b>
	 *
	 * @param lastModifiedAt
	 *            novi datum i vrijeme posljednje izmjene
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Metoda koja dohvaća naslov objave
	 *
	 * @return naslov objave
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Metoda koja postavlja novi naslov objave predan kao parametar
	 * <b>title</b>
	 *
	 * @param title
	 *            novi naslov objave
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Metoda koja dohvaća sadržaj objave
	 *
	 * @return sadržaj objave
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Metoda koja postavlja novi sadržaj objave predan kao parametar
	 * <b>text</b>
	 *
	 * @param text
	 *            novi sadržaj objave
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Metoda koja dohvaća primjerak razreda {@link BlogUser} kojem ova objava
	 * pripada
	 *
	 * @return primjerak razreda {@link BlogUser} kojem ova objava pripada
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Metoda koja postavlja novi primjerak razreda {@link BlogUser} kojem ova
	 * objava pripada predan kao parametar <b>creator</b>
	 *
	 * @param creator
	 *            novi primjerak razreda {@link BlogUser} kojem ova objava
	 *            pripada
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}