package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.servlets.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * učitavaju sve korisnike ovog bloga pozivom metode {@link DAO#getBlogUsers()},
 * te ih spremaju u atribute zahtjeva. Nakon toga rad prepuštaju JSP datoteci
 * "/WEB-INF/pages/main.jsp"
 * <p>
 * Unutar metode {@link #doPost(HttpServletRequest, HttpServletResponse)}
 * obavlja se prijava korisnika na blog. Ukoliko je prijava uspješna, spremaju
 * se korisnikov identifikator, nadimak, ime i prezime u atribute korisnikove
 * sessije. Potom se radi redirekcija, kako se ne bi nanovo registriralo
 * korisnika na svako osvježavanje stranice
 * </p>
 * 
 * @see HttpServlet
 * @see DAO
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "main", urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ovu rutu ne štitim jer je ona početna
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();

		request.setAttribute("blogUsers", users);

		request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String providedNick = request.getParameter("nick");
		System.out.println("postam");
		BlogUser user = DAOProvider.getDAO().getBlogUser(providedNick);
		if (user == null) {
			setErrorMessage(request, response,
					String.format("Korisnik sa nadimkom '%s' ne postoji u sustavu", request.getParameter("nick")));
		}

		if (!user.getPasswordHash().equals(ServletUtil.calculatePasswordHash(request))) {
			request.setAttribute("providedNick", providedNick);
			setErrorMessage(request, response, "Unijeli ste pogršnu lozinku, molimo Vas pokušajte ponovo");
			return;
		}

		HttpSession session = request.getSession();
		// promjena imena zbog EL-expressiona
		session.setAttribute("currentUserId", user.getId());
		session.setAttribute("currentUserFn", user.getFirstName());
		session.setAttribute("currentUserLn", user.getLastName());
		session.setAttribute("currentUserNick", user.getNick());

		response.sendRedirect(request.getContextPath());
	}

	/**
	 * Pomoćna metoda koja se poziva u slučaju greške prilikom prijave. Metoda
	 * kao atribut zahtjeva postavlja predanu poruku <b>message</b> te ponovo
	 * poziva metodu {@link #doGet(HttpServletRequest, HttpServletResponse)} sa
	 * predanim parametrima <b>request</b> i <b>response</b>.
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a koji se prosljeđuje metodi
	 *            {@link #doGet(HttpServletRequest, HttpServletResponse)}
	 * @param response
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletResponse}, a koji se prosljeđuje metodi
	 *            {@link #doGet(HttpServletRequest, HttpServletResponse)}
	 * @param message
	 *            Poruka o pogrešci koja se ispisuje korisniku
	 * @throws ServletException
	 *             ukoliko metoda
	 *             {@link #doGet(HttpServletRequest, HttpServletResponse)} baci
	 *             ovu iznimku
	 * @throws IOException
	 *             ukoliko metoda
	 *             {@link #doGet(HttpServletRequest, HttpServletResponse)} baci
	 *             ovu iznimku
	 */
	private void setErrorMessage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("loginErrorMessage", message);

		this.doGet(request, response);
	}

}
