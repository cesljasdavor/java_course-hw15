package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.web.servlets.util.Router;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda i
 * unutar metoda {@link #doGet(HttpServletRequest, HttpServletResponse)} i
 * unutar metode {@link #doPost(HttpServletRequest, HttpServletResponse)}
 * pronalaze jedan od servleta iz paketa
 * <b>hr.fer.zemris.java.hw15.web.servlets.author</b>, a koji odgovara zahtjevu
 * korisnika. Kada pronađu servlet prosljeđuju mu posao posluživanja korisnika.
 * <p>
 * Na primjerke ovog razreda se može gledati kao na ulaznu točku usmjeritelja(engl. router)
 * </p>
 * 
 * @see HttpServlet
 * @see Router
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "authorRouter", urlPatterns = { "/servleti/author/*" })
public class AuthorRouterServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * Pomoćna metoda koja sa predanim parametrima <b>request</b> i
	 * <b>response</b> poziva metodu
	 * {@link Router#reroute(HttpServletRequest, HttpServletResponse)}.
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a koji se prosljeđuje metodi
	 *            {@link Router#reroute(HttpServletRequest, HttpServletResponse)}
	 * @param response
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletResponse}, a koji se prosljeđuje metodi
	 *            {@link Router#reroute(HttpServletRequest, HttpServletResponse)}
	 * @throws ServletException
	 *             ukoliko metoda
	 *             {@link Router#reroute(HttpServletRequest, HttpServletResponse)}
	 *             baci ovu iznimku
	 * @throws IOException
	 *             ukoliko metoda
	 *             {@link Router#reroute(HttpServletRequest, HttpServletResponse)}
	 *             baci ovu iznimku
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Router.reroute(request, response);
	}
}
