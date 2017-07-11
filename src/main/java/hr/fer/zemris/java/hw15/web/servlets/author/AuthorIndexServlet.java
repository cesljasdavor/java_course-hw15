package hr.fer.zemris.java.hw15.web.servlets.author;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * dohvaćaju sve objave modelirane razredom {@link BlogEntry}, koje je objavio
 * korisnik, a čiji se nadimak nalazi unutar rute.
 * <p>
 * Ukoliko korisnik s tim nadimkom ne postoji metoda će poslati poruku o
 * pogrešci statusa {@link HttpServletResponse#SC_NOT_FOUND}
 * </p>
 * <p>
 * Ukoliko je sve u redu metoda će spremiti primjerak razreda {@link BlogUser}
 * unutar atributa zahtjeva i stvaranja HTML dokumenta proslijediti JSP datoteci
 * "/WEB-INF/pages/author/index.jsp".
 * </p>
 * 
 * @see HttpServlet
 * @see BlogEntry
 * @see BlogUser
 * 
 * @author Davor Češljaš
 * 
 */
public class AuthorIndexServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> pathParams = (List<String>) request.getAttribute("router.parameters");

		BlogUser user = DAOProvider.getDAO().getBlogUser(pathParams.get(0));
		if (user == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		request.setAttribute("blogUser", user);
		request.getRequestDispatcher("/WEB-INF/pages/author/index.jsp").forward(request, response);
	}
}
