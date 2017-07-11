package hr.fer.zemris.java.hw15.web.servlets.author;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.web.servlets.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * unutar svoje metoda {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * radi sljedeće:
 * <ol>
 * <li>Provjerava je li korisnik prijavljen na sustav</li>
 * <li>Provjerava je li unutar korisnikove sesije isti nadimak kao i onaj unutar
 * rute</li>
 * <li>(opcionalno) Ukoliko je zadnji dio rute jednka "edit" i ukoliko se radi o
 * pravovaljanom identifikatoru objave, sprema tu objavu modeliranu razredom
 * {@link BlogEntry} unutar atributa zahtjeva</li>
 * </ol>
 * Nakon ovih koraka izradu HTML dokumenta prosljeđuje JSP datoteci
 * "/WEB-INF/pages/author/manage.jsp"
 * 
 * <p>
 * Metoda {@link #doPost(HttpServletRequest, HttpServletResponse)} izvodi
 * sljedeće korake:
 * <ol>
 * <li>Provjerava je li korisnik prijavljen na sustav</li>
 * <li>Stvara novi ili mijenja postojeći primjerak razreda
 * {@link BlogEntry}</li>
 * <li>Ukoliko je sve dobro prošlo vrši redirekciju na servlet
 * {@link BlogEntryShowServlet#doGet(HttpServletRequest, HttpServletResponse)}
 * (indirektno)</li>
 * </ol>
 * </p>
 * 
 * @see HttpServlet
 * @see BlogEntry
 * @see BlogEntryShowServlet
 * 
 * @author Davor Češljaš
 */
public class BlogEntriesManagingServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (ServletUtil.guardRoute(session, false)) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		List<String> parameters = (List<String>) request.getAttribute("router.parameters");
		String nick = parameters.get(0);
		if (!ServletUtil.nicksMatch(session, nick)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (parameters.get(parameters.size() - 1).equals("edit")) {
			Long entryId = ServletUtil.checkAndGetValue(request, "id");
			if (entryId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			request.setAttribute("entryToUpdate", DAOProvider.getDAO().getBlogEntry(entryId));
		}

		request.getRequestDispatcher("/WEB-INF/pages/author/manage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (ServletUtil.guardRoute(request.getSession(), false)) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		BlogEntry entry = manageBlogEntry(request);
		if (entry == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		if (entry.getId() == null) {
			JPAEMProvider.getEntityManager().persist(entry);
		}

		response.sendRedirect(String.format("%s/servleti/author/%s/%s", request.getContextPath(),
				entry.getCreator().getNick(), entry.getId()));
	}

	/**
	 * Pomoćna metoda koja na temelju parametara iz zahtjeva stvara novi ili
	 * mijenja postojeći primjerak razreda {@link BlogEntry}. Po namještanju
	 * svih potrebnih atributa tog primjerka metoda ga vraća kao povratnu
	 * vrijednost.
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a iz kojeg se vade svi potrebni
	 *            parametri za stvaranje novog ili dohvaćanje postojećeg
	 *            primjerka razreda {@link BlogEntry} te ažuriranje njegovih
	 *            vrijednosti
	 * @return ažurirani ili novo stvoreni i inicijalizirani primjerak razreda
	 *         {@link BlogEntry}
	 */
	private BlogEntry manageBlogEntry(HttpServletRequest request) {
		BlogEntry entry = requireBlogEntry(request);
		if (entry == null) {
			return null;
		}

		entry.setTitle(request.getParameter("title"));
		entry.setText(request.getParameter("text"));

		Date now = new Date();
		entry.setLastModifiedAt(now);

		if (entry.getId() == null) {
			entry.setCreatedAt(now);
			entry.setCreator(
					DAOProvider.getDAO().getBlogUser((String) request.getSession().getAttribute("currentUserNick")));
		}

		return entry;
	}

	/**
	 * Pomoćna metoda koja iz predanog parametra <b>request</b> pokušava
	 * dohvatiti postojeći primjerak razreda {@link BlogEntry}. Ukoliko taj ne
	 * postoji metoda stvara novi primjerak tog razreda i šalje ga kao povratnu
	 * vrijednost.
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a iz kojeg se vade svi potrebni
	 *            parametri za dohvat postojećeg primjerka razreda
	 *            {@link BlogEntry}
	 * @return postojeći ili novostvoreni primjerak razreda {@link BlogEntry}
	 */
	@SuppressWarnings("unchecked")
	private BlogEntry requireBlogEntry(HttpServletRequest request) {
		Long entryId = ServletUtil.checkAndGetValue(request, "id");
		List<String> parameters = (List<String>) request.getAttribute("router.parameters");

		if (parameters.get(parameters.size() - 1).equals("edit")) {
			if (entryId == null) {
				return null;
			}

			return DAOProvider.getDAO().getBlogEntry(entryId);
		}

		return new BlogEntry();
	}
}
