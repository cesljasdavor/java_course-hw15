package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.web.servlets.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * odjavljuju korisnika sa sustava pozivom {@link HttpSession#invalidate()}
 * metode. Dakako uvjet da se korisnik odjavi je da je predhodno bio prijavljen
 * na sustav.
 * 
 * @see {@link HttpServlet}
 * @see HttpSession
 * 
 * @author Davor Češljaš
 * 
 */
@WebServlet(name = "logout", urlPatterns = { "/servleti/logout" })
public class LogoutServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!ServletUtil.guardRoute(request.getSession(), false)) {
			request.getSession().invalidate();
		}

		response.sendRedirect(request.getContextPath());
	}
}
