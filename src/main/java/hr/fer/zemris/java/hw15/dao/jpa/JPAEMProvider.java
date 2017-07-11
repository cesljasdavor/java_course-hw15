package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Razred koji se koristi za pohranu primjeraka razreda {@link EntityManager}
 * unutar primjerka razreda {@link ThreadLocal}. ThreadLocal je zapravo mapa
 * čiji su ključevi identifikator dretve koji radi operaciju nad mapom.
 * 
 * @see ThreadLocal
 * 
 * @author Davor Češljaš
 */
public class JPAEMProvider {

	/**
	 * Statička referenca na primjerak razreda {@link ThreadLocal} unutar kojeg
	 * su spremljeni primjerci razreda {@link LocalData} koji su mapirani na
	 * pojedinu dretvu
	 * 
	 * @see LocalData
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Metoda koja se koristi za dohvat primjerka razreda {@link EntityManager}
	 * za trenutnu dretvu. Metoda će ukoliko primjerak razreda
	 * {@link EntityManager} ne postoji za trenutnu dretvu stvoriti novi
	 * primjerak tog razreda, zapamtiti ga te vratiti stvoreni primjerak
	 * pozivatelju
	 * 
	 * @return primjerak razreda {@link EntityManager} za trenutnu dretvu
	 * 
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Metoda koja se koristi za zatvaranje primjerka razreda
	 * {@link EntityManager} koji se koristio unutar trenutne dretve. Prilikom
	 * zatvaranja sve neprovedene transakcije se provode , nakon ćega se taj
	 * primjerak razreda briše iz internog spremišta
	 *
	 * @throws DAOException
	 *             ukoliko nije moguće obaviti transakciju ili nije moguće
	 *             zatvoriti primjerak razreda {@link EntityManager}
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * Pomoćni razred čiji primjerci služe kao omotači primjerka razreda
	 * {@link EntityManager}
	 * 
	 * @see EntityManager
	 * 
	 * @author Davor Češljaš
	 */
	private static class LocalData {

		/**
		 * Članska varijabla koja predstavlja primjerak razreda
		 * {@link EntityManager} koji primjerak ovog razreda omata
		 */
		EntityManager em;
	}

}