package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Sučelje koje modelira Data Source Object za ovu aplikaciju. Ova strategija je
 * sučelje prema bazi podataka ili bilo kojem drugom sustavu koji se nalazi na
 * sloju za perzistenciju podataka (npr. datotečni sustav). Ovaj DAO nudi
 * sljedeće metode:
 * <ul>
 * <li>{@link #getBlogEntry(Long)}</li>
 * <li>{@link #getUsersEntries(Long)}</li>
 * <li>{@link #getBlogUsers()}</li>
 * <li>{@link #getBlogUser(String)}</li>
 * </ul>
 * <p>
 * Za više informacija o ovom konceptu korisnika se navodi na sljedeću
 * <a href="https://en.wikipedia.org/wiki/Data_access_object">poveznicu</a>
 * </p>
 *
 * @see BlogEntry
 * @see BlogUser
 * 
 * @author Davor Češljaš
 */
public interface DAO {

	/**
	 * Metoda koja dohvaća primjerak razreda {@link BlogEntry} koji ima
	 * identifikator jednak <b>id</b>, sa sloja za perzistenciju. Ukoliko takav
	 * ne postoji metoda vraća <code>null</code>
	 *
	 * @param id
	 *            identifikator objave na blogu
	 * @return primjerak razreda {@link BlogEntry} koji ima identifikator jednak
	 *         <b>id</b> ili <code>null</code> ukoliko takva objava ne postoji
	 * @throws DAOException
	 *             prilikom greške unutar sustava na sloju perzistenciju
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Metoda koja dohvaća {@link List} svih primjeraka razreda
	 * {@link BlogEntry} koji pripadaju korisniku, čiji je identifikator jednak
	 * <b>userId</b>. Metoda podatke dohvaća sa sloja za perzistenciju podataka.
	 *
	 * @param userId
	 *            identifikator korisnika čije se objave traže
	 * @return {@link List} svih primjeraka razreda {@link BlogEntry} koji
	 *         pripadaju korisniku, čiji je identifikator jednak <b>userId</b>.
	 *         Ova lista je prazna ukoliko takav korisnik ne postoji ili
	 *         korisnik nema niti jednu objavu
	 * @throws DAOException
	 *             prilikom greške unutar sustava na sloju perzistenciju
	 */
	public List<BlogEntry> getUsersEntries(Long userId) throws DAOException;

	/**
	 * Metoda koja dohvaća {@link List} svih korisnika bloga sa sloja za
	 * perzistenciju podataka. Ova lista je prazna ukoliko ne postoji niti jedan
	 * korisnik bloga.
	 *
	 * @return {@link List} svih korisnika bloga modeliranih razredom
	 *         {@link BlogUser}
	 * @throws DAOException
	 *             prilikom greške unutar sustava na sloju perzistenciju
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Metoda koja temeljem korisnikovog nadimka <b>nick</b> dohvaća primjerak
	 * razreda {@link BlogUser} sa sloja za perzistenciju podataka. Ukoliko
	 * korisnik sa predanim nadimkom ne postoji metoda vraća <code>null</code>
	 *
	 * @param nick
	 *            nadimak na temelju kojeg se traži primjerak razreda
	 *            {@link BlogUser}
	 * @return primjerak razreda {@link BlogUser} sa nadimkom <b>nick</b> ili
	 *         <code>null</code> ukoliko takav korisnik ne postoji
	 * @throws DAOException
	 *            prilikom greške unutar sustava na sloju perzistenciju
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
}