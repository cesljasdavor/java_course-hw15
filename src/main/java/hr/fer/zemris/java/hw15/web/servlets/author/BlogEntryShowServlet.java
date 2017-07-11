package hr.fer.zemris.java.hw15.web.servlets.author;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * dohvaća primjerak razreda {@link BlogEntry} čiji identifikator odgovara onom
 * unutar rute. Ukoliko takva objava ne postoji šalje poruku o pogrešci, a
 * ukoliko postoji sprema pronađeni objekt u atribute zahtjeva te generiranje
 * HTML dokumenta delegira JSP datoteci "/WEB-INF/pages/author/show.jsp".
 * <p>
 * Metoda {@link #doPost(HttpServletRequest, HttpServletResponse)} pak služi za
 * stvaranje novog komentara na objavu te ponovnu redirekciju na ovaj servlet,
 * kako se komentar ne bi na svako osvježavanje stranice ponovno stvarao.
 * </p>
 * 
 * @see HttpServlet
 * @see BlogEntry
 * 
 * @author Davor Češljaš
 */
public class BlogEntryShowServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BlogEntry entry = requireBlogEntry(request);
		if (entry == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		request.setAttribute("toDisplay", entry);

		request.getRequestDispatcher("/WEB-INF/pages/author/show.jsp").forward(request, response);
	}

	/**
	 * Pomoćna metoda koja se koristi za dohvat primjerka razreda
	 * {@link BlogEntry} iz parametara zahtjeva modeliranog primjerkom razreda
	 * koji implementira sučelje {@link HttpServletRequest}.
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a iz kojeg se vade potrebni
	 *            parametri za dohvat primjerka razreda {@link BlogEntry}
	 * @return primjerak razreda {@link BlogEntry} ili <code>null</code> ukoliko
	 *         vađenje parametara nije uspjelo ili objava sa tim identifikatorom
	 *         ne postoji na sloju za perzistenicju podataka.
	 */
	@SuppressWarnings("unchecked")
	private BlogEntry requireBlogEntry(HttpServletRequest request) {
		List<String> parameters = (List<String>) request.getAttribute("router.parameters");

		Long entryId = Long.parseLong(parameters.get(1));
		String nick = parameters.get(0);

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		if (entry == null || !entry.getCreator().getNick().equals(nick)) {
			return null;
		}

		return entry;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BlogEntry entry = requireBlogEntry(request);
		if (entry == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		postComment(request, entry);
		// nazad na isti get, ali pri tome stvori novi request i response
		response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo());
	}

	/**
	 * Pomoćna metoda koja vrši spremanje novog komentara na sloj za
	 * perzistenciju podataka. Parametri za stvaranj komentara dobivaju se iz
	 * parametara zahtjeva
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a iz kojeg se vade parametri
	 *            potrebni za stvaranje komentara
	 * @param entry
	 *            primjerak razreda {@link BlogEntry} kojemu se dodaje komentar
	 */
	private void postComment(HttpServletRequest request, BlogEntry entry) {
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(entry);

		String email = "Anonimni";
		Object potentialUserNick = request.getSession().getAttribute("currentUserNick");
		if (potentialUserNick != null) {
			email = DAOProvider.getDAO().getBlogUser((String) potentialUserNick).getEmail();
		}
		comment.setUsersEMail(email);

		comment.setMessage(request.getParameter("message"));
		comment.setPostedOn(new Date());

		JPAEMProvider.getEntityManager().persist(comment);
	}

}
