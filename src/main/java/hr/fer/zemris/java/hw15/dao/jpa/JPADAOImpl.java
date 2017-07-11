package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji implementira sučelje {@link DAO}. Ovaj razred koristi se kao Data
 * Source Object koji za izvor podataka ima bazu podataka, a kojoj se pristupa
 * pomoću <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">Java
 * Persistence API-ja</a>. Drugim riječima svi podaci koje metode ovog razreda
 * (odnosno sučelja {@link DAO}) vraćaju dolaze iz baze podataka.
 * 
 * @see DAO
 * 
 * @author Davor Češljaš
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogEntry> getUsersEntries(Long userId) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, userId);
		return blogUser == null ? null : blogUser.getEntries();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		return (List<BlogUser>) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.all").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.by.nick")
				.setParameter("nick", nick).getResultList();

		return users.isEmpty() ? null : users.get(0);
	}

}