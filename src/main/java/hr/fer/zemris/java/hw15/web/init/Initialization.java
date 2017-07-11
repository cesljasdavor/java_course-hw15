package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * Razred koji implementira sučelje {@link ServletContextListener}. Primjerci
 * ovog razreda prilikom pokretanja poslužitelja stvaraju novi primjerak razreda
 * {@link EntityManagerFactory} te ga nakon inicijalizacije postavljaju razredu
 * {@link JPAEMFProvider}, preko kojeg se inicijalizirani primjerak tog razreda
 * može dohvatitit. Također isti primjerak razreda spremaju i unutar primjerka
 * razreda koji implementira sučelje {@link ServletContext}, a koji je poslužitelj
 * stvori, kako bi se on mogao dohvatiti bilo gdje na poslužitelju.
 * <p>
 * U trenutku kada se poslužitelj zatvara, unutar metode
 * {@link #contextDestroyed(ServletContextEvent)} već spomenuti primjerak
 * razreda {@link EntityManagerFactory} se zatvara i otpuštaju se svi njegovi
 * resursi
 * </p>
 * 
 * @see ServletContextListener
 * @see ServletContext
 * @see EntityManagerFactory
 * 
 * @author Davor Češljaš
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}