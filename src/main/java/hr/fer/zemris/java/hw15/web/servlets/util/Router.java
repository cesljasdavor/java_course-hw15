package hr.fer.zemris.java.hw15.web.servlets.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.web.servlets.author.AuthorIndexServlet;
import hr.fer.zemris.java.hw15.web.servlets.author.BlogEntriesManagingServlet;
import hr.fer.zemris.java.hw15.web.servlets.author.BlogEntryShowServlet;

/**
 * Razred koji se koristi kao usmjeritelj (engl. Router). Razredu se ne mogu
 * stvarati primjerci već sadrži samo statičke metode i konstante kako bi mogao
 * vršiti usmjeravanje. Razred sadrži jednu metodu
 * {@link #reroute(HttpServletRequest, HttpServletResponse)}, preko koje se vrši
 * pronalaženje servleta koji je mapiran na pojedinu rutu te izvršavanje metode
 * {@link HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}.
 * 
 * 
 * @see HttpServlet
 * 
 * @author Davor Češljaš
 */
public class Router {

	/**
	 * Konstanta koja predstavlja ključ unutar {@link #SERVLETS} kojem odgovara
	 * servlet {@link AuthorIndexServlet}
	 */
	private static final String INDEX = "index";

	/**
	 * Konstanta koja predstavlja ključ unutar {@link #SERVLETS} kojem odgovara
	 * servlet {@link BlogEntriesManagingServlet}
	 */
	private static final String MANAGE = "manage";

	/**
	 * Konstanta koja predstavlja ključ unutar {@link #SERVLETS} kojem odgovara
	 * servlet {@link BlogEntryShowServlet}
	 */
	private static final String SHOW = "show";

	/**
	 * Konstanta koja predstavlja {@link Map} u kojoj su ključevi ruta mapirani
	 * na pripradajuće primjerke razreda {@link HttpServlet}
	 */
	private static final Map<String, HttpServlet> SERVLETS;

	static {
		Map<String, HttpServlet> map = new HashMap<>();
		map.put(INDEX, new AuthorIndexServlet());
		map.put(MANAGE, new BlogEntriesManagingServlet());
		map.put(SHOW, new BlogEntryShowServlet());

		SERVLETS = Collections.unmodifiableMap(map);
	}

	/**
	 * Privatni konstruktor koji služi tome da se primjerci ovog razreda ne mogu
	 * stvarati izvan samog razreda.
	 */
	private Router() {
	}

	/**
	 * Metoda koja vrši pronalazak servleta unutar interne mape na temelju rute
	 * na koju je zahtjev poslan. Ukoliko na tu rutu nije mapiran niti jedan
	 * servlet metoda će korisniku ispisati grešku. Ukoliko je servlet zadužen
	 * za posluživanje poslane rute pronađen metoda će nad njim pozvati metodu
	 * {@link HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a koji predstavlja korisnikov
	 *            zahtjev
	 * @param response
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletResponse}, a koji predstavlja odgovor na
	 *            korisnikov zahtjev
	 * @throws IOException
	 *             ukoliko servleti kojem se delegira posao baci ovu iznimku
	 * @throws ServletException
	 *             ukoliko servleti kojem se delegira posao baci ovu iznimku
	 */
	public static void reroute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<String> parameters = extractParameters(request);
		if (parameters.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		request.setAttribute("router.parameters", parameters);

		HttpServlet servlet = findServlet(parameters);
		if (servlet == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		servlet.service(request, response);
	}

	/**
	 * Pomoćna metoda koja se koristi za vađenje parametara rute iz predanog
	 * primjerka razreda koji implementira sučelje {@link HttpServletRequest}
	 * <b>request</b>
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a iz kojeg se vade parametri rute
	 * @return {@link List} svih parametara rute koji su modelirani razredom
	 *         {@link String}
	 */
	private static List<String> extractParameters(HttpServletRequest request) {
		String extraPath = request.getPathInfo();

		List<String> parameters = new ArrayList<>();
		for (String param : extraPath.split("/")) {
			param = param.trim();

			if (!param.isEmpty()) {
				parameters.add(param);
			}
		}

		return parameters;
	}

	/**
	 * Pomoćna metoda koja se koristi za pronalazak servleta koji odgovara ruti
	 * čiji su parametri predani kao {@link List} primjeraka razreda
	 * {@link String} <b>parameters</b>
	 *
	 * @param parameters
	 *            {@link List} primjeraka razreda {@link String} koja
	 *            predstavlja parametre rute
	 * @return primjerak razreda {@link HttpServlet} koji odgovara parametrima
	 *         rute ili <code>null</code> ukoliko ruta ne odgovara niti jednom
	 *         primjerku razreda {@link HttpServlet} unutar mape
	 *         {@link #SERVLETS}
	 */
	private static HttpServlet findServlet(List<String> parameters) {
		switch (parameters.size()) {
		case 1:
			return SERVLETS.get(INDEX);
		case 2:
			return findByLastRoutePart(parameters.get(1));
		default:
			return null;
		}
	}

	/**
	 * Pomoćna metoda koja ispituje zadnji parametar poslane rute kako bi
	 * ustvrdio koji je primjerak razreda {@link HttpServlet} potrebno vratiti
	 * pozivatelju
	 *
	 * @param lastInRoute
	 *            posljednji parametar rute
	 * @return primjerak razreda {@link HttpServlet} koji odgovara posljednjem
	 *         parametru rute ili <code>null</code> ukoliko ruta ne odgovara
	 *         niti jednom primjerku razreda {@link HttpServlet} unutar mape
	 *         {@link #SERVLETS}
	 */
	private static HttpServlet findByLastRoutePart(String lastInRoute) {
		try {
			Long.parseLong(lastInRoute);
			return SERVLETS.get(SHOW);
		} catch (NumberFormatException nfe) {
			if (lastInRoute.equals("new") || lastInRoute.equals("edit")) {
				return SERVLETS.get(MANAGE);
			}
		}

		return null;
	}
}
