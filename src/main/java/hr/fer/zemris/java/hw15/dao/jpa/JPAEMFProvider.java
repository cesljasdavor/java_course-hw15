package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Razred koji predstavlja biblioteku, a koja sadrži jedan primjerak razreda
 * {@link EntityManagerFactory}. Ovaj objekt koristi se za stvaranje primjeraka
 * razreda {@link EntityManager}, koji se koriste za dohvat i spremanje podataka
 * u bazu podataka ,koje ova aplikacija koristi. Razred nudi dvije metode:
 * <ul>
 * <li>{@link #getEmf()}</li>
 * <li>{@link #setEmf(EntityManagerFactory)}</li>
 * </ul>
 * 
 * @see EntityManagerFactory
 * @see EntityManager
 * 
 * @author Davor Češljaš
 */
public class JPAEMFProvider {

	/**
	 * Statička referenca na primjerak razreda {@link EntityManagerFactory} koji
	 * se koristi za stvaranje primjeraka razreda {@link EntityManager} unutar
	 * ove aplikacije
	 */
	public static EntityManagerFactory emf;

	/**
	 * Statička metoda koja dohvaća primjerak razreda
	 * {@link EntityManagerFactory} koji je spremljen unutar statičke varijable
	 * {@link #emf}
	 *
	 * @return primjerak razreda {@link EntityManagerFactory} koji je spremljen
	 *         unutar statičke varijable {@link #emf}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Statička metoda koja ažurira statičku referencu {@link #emf} na primjerak
	 * razreda {@link EntityManagerFactory} <b>emf</b> koji je poslan kao
	 * parametar. Ukoliko je taj parametar <code>null</code>, dosadašnji
	 * primjerak razreda se briše iz memorije.
	 *
	 * @param emf
	 *            novi primjerak razreda {@link EntityManagerFactory} koji se
	 *            postavlja ili <code>null</code> ukoliko se trenutni primjerak
	 *            tog razreda želi obrisati
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}