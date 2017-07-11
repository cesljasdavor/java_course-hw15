package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.servlets.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)},
 * a ukoliko korisnik već nije prijavljen na sustav, posao iscrtavanja forme za
 * registraciju prepuštaju JSP datoteci "/WEB-INF/pages/register.jsp".
 * <p>
 * Unutar metoda {@link #doPost(HttpServletRequest, HttpServletResponse)}, ovaj
 * servlet obavlja registraciju korisnika te ukoliko je registracija uspješna
 * prijavlju korisnika na sustav tako što poziva
 * {@link MainServlet#doPost(HttpServletRequest, HttpServletResponse)}
 * (implicitno). U suprotnom poziva metodu
 * {@link #doGet(HttpServletRequest, HttpServletResponse)} sa porukom o pogrešci
 * spremljenom unutar atributa zahtjeva
 * </p>
 * 
 * @see HttpServlet
 * @see MainServlet
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "register", urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (ServletUtil.guardRoute(request.getSession(), true)) {
			response.sendRedirect(request.getContextPath());
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BlogUser newUser = createUser(request);

		if (newUser == null) {
			request.setAttribute("registerErrorMessage",
					String.format(
							"Nažalost nadimak '%s' već postoji unutar ovog bloga, molimo Vas da unesete drugi nadimak",
							request.getParameter("nick")));
			this.doGet(request, response);
			return;
		}

		JPAEMProvider.getEntityManager().persist(newUser);
		// automatska registracija korisnika(time je očito da je sve u redu
		// prošlo)
		request.getRequestDispatcher("/servleti/main").forward(request, response);
	}

	/**
	 * Pomoćna metoda koja stvara novi primjerak razreda {@link BlogUser} pomoću
	 * parametara koji su spremljeni unutar zahtjeva modeliranog sučeljem
	 * {@link HttpServletRequest} <b>request</b>
	 *
	 * @param request
	 *            zahtjev modeliran sučeljem {@link HttpServletRequest} unutar
	 *            kojeg se nalaze parametri potrebni za stvaranje novog
	 *            korisnika
	 * @return novi korisnik modeliran razredom {@link BlogUser}
	 */
	private BlogUser createUser(HttpServletRequest request) {
		// zbog dobro napravljene forme niti jedan element neće biti null
		// ili post nikada neće biti pokrenut
		String nick = request.getParameter("nick");
		if (nickExists(nick)) {
			return null;
		}

		BlogUser newUser = new BlogUser();

		newUser.setNick(nick);
		newUser.setFirstName(request.getParameter("firstName"));
		newUser.setLastName(request.getParameter("lastName"));
		newUser.setEmail(request.getParameter("email"));

		String passwordHash = ServletUtil.calculatePasswordHash(request);
		if (passwordHash == null) {
			return null;
		}
		newUser.setPasswordHash(passwordHash);

		return newUser;
	}

	/**
	 * Pomoćna metoda koja ispituje postoji li predani nadimak <b>nick</b>
	 * unutar podataka na sloju za perzistenciju
	 *
	 * @param nick
	 *            nadimak koji se ispituje
	 * @return <code>true</code> ukoliko nadimak već postoji unutar podataka na
	 *         sloju za perzistenciju, <code>false</code> inače
	 */
	private boolean nickExists(String nick) {
		return DAOProvider.getDAO().getBlogUser(nick) != null;
	}
}
