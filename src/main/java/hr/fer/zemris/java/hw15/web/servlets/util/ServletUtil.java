package hr.fer.zemris.java.hw15.web.servlets.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Razred koji se koristi kao pomoćna biblioteka za sve servlete unutar ove
 * aplikacije. Ovom razredu ne mogu se stvarati primjerci. Razred nudi sljedeće
 * metode:
 * <ul>
 * <li>{@link #calculatePasswordHash(HttpServletRequest)}</li>
 * <li>{@link #guardRoute(HttpSession, boolean)}</li>
 * <li>{@link #loggedIn(HttpSession)}</li>
 * <li>{@link #nicksMatch(HttpSession, String)}</li>
 * <li>{@link #checkAndGetValue(HttpServletRequest, String)}</li>
 * </ul>
 * 
 * @see HttpServletRequest
 * @see HttpSession
 * 
 * @author Davor Češljaš
 */
public class ServletUtil {
	/**
	 * Konstanta koja predstavlja naziv algoritma koji se koristi za izračun
	 * zaštitne sume datoteka (i dekripciju i enkripciju).
	 */
	private static final String DIGEST_ALGORITHM = "SHA-1";

	/**
	 * Konstanta koja predstavlja pretpostavljeni skup znakova kojim se enkodira
	 * lozinka
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Privatni konstruktor koji služi tome da se primjerci ovog razreda ne mogu
	 * stvarati izvan samog razreda.
	 */
	private ServletUtil() {
	}

	/**
	 * Metoda koja se koristi za računanje sažetka lozinke. Kao algoritam
	 * računanja sažetka koristi se {@value #DIGEST_ALGORITHM}
	 *
	 * @param request
	 *            primjerak razreda koji implementira sučelje
	 *            {@link HttpServletRequest}, a unutar čijih parametara se
	 *            nalazi lozinka
	 * @return primjerak razreda {@link String} koji predstavlja sažetak lozinke
	 *         ili <code>null</code> ukoliko sažetak nije moguće napraviti
	 */
	public static String calculatePasswordHash(HttpServletRequest request) {
		try {
			String encoding = request.getCharacterEncoding();
			return passwordDigest(request.getParameter("password"), encoding);
		} catch (NoSuchAlgorithmException e) {
			// server se ovdje mora srušiti jer ne može enkodirati lozinke!
			System.exit(-1);
		} catch (RuntimeException e) {
		}

		return null;
	}

	/**
	 * Pomoćna metoda koja vrši sam izračun zaštitne sume od parametra
	 * <b>password</b> algoritmom {@value #DIGEST_ALGORITHM}. Parametar
	 * <b>encoding</b> interpretira se kao skup znakova kojim je enkodiran
	 * <b>password</b>. Ukoliko je on <code>null</code> pretpostavlja se da je
	 * <b>password</b> enkodiran skupom znakova (kodnom stranicom)
	 * {@value #DEFAULT_ENCODING}.
	 * 
	 *
	 * @param password
	 *            lozinka kojoj se računa sažetak (zaštitna suma)
	 * @param encoding
	 *            primjerak razreda {@link String} koji predstavlja ime kodne
	 *            stranice koja se koristi za enkodiranje lozinke
	 * @return sažetak predane lozinke
	 * @throws NoSuchAlgorithmException
	 *             ukoliko algoritam računanja sažetka
	 *             {@value #DIGEST_ALGORITHM} ne postoji
	 */
	private static String passwordDigest(String password, String encoding) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);

		byte[] passwordBytes = password.getBytes(Charset.forName(encoding == null ? DEFAULT_ENCODING : encoding));

		return bytetohex(md.digest(passwordBytes));
	}

	/**
	 * Pomoćna metoda koja predano polje okteta pretvara u primjerak razreda
	 * {@link String} koji predstavlja heksadekadsku reprezentaciju predanog
	 * polja okteta. Metoda se oslanja na
	 * {@link String#format(String, Object...)} metodu
	 *
	 * @param bytearray
	 *            polje okteta koje se pretvara u heksadekadsku reprezentaciju
	 * @return heksadekadska reprezentacija predanog polja okteta
	 *         <b>bytearray</b>
	 */
	private static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();

		for (byte b : bytearray) {
			sb.append(String.format("%02X", b));
		}

		return sb.toString().toLowerCase();
	}

	/**
	 * Metoda koja se koristi za tzv. čuvanje ruta. Metoda jednostavo ispituje
	 * je li neki korisnik prijavljen pozivom metode
	 * {@link #loggedIn(HttpSession)} te je li odgovor te metode jednak predanom
	 * parametru <b>loggedIn</b>.
	 * <p>
	 * Jednostavnije rečeno, čuvamo li rutu ako je korisnik prijavljen ili ne
	 * </p>
	 *
	 * @param session
	 *            primjerak sučelja {@link HttpSession} kojeg se ispituje
	 *            postoji li prijavljeni korisnik
	 * @param isLoggedIn
	 *            zastavica koja ukazuje na očekivani odgovor metode
	 *            {@link #loggedIn(HttpSession)}
	 * @return <code>true</code> ako se odgovor metode
	 *         {@link #loggedIn(HttpSession)} i parametar <b>loggedIn</b>
	 *         podudaraju, <code>false</code> inače
	 */
	public static boolean guardRoute(HttpSession session, boolean isLoggedIn) {
		return !(loggedIn(session) ^ isLoggedIn);
	}

	/**
	 * Metoda koja iz predanog primjerka sučelja {@link HttpSession} pokušava
	 * ustvrditi je li korisnik prijavljen ili ne
	 *
	 * @param session
	 *            primjerak sučelja {@link HttpSession} kojeg se ispituje
	 *            postoji li prijavljeni korisnik
	 * @return <code>true</code> ako je korisnik prijavljen, <code>false</code>
	 *         inače
	 */
	public static boolean loggedIn(HttpSession session) {
		return session.getAttribute("currentUserId") != null;
	}

	/**
	 * Metoda koja ustvrđuje je li nadimak spremljen unutar parametara primjerka
	 * sučelja {@link HttpSession} jednak onom predanom kao parametar
	 * <b>nick</b>
	 *
	 * @param session
	 *            primjerka sučelja {@link HttpSession} unutar čijih bi
	 *            parametara treba biti spremljen nadimak korisnika sessije (sjednice)
	 * @param nick
	 *            nadimak s kojim metoda uspoređuje onaj spremljen unutar
	 *            parametara sessije
	 * @return <code>true</code> ukoliko se nadimci podudaraju,
	 *         <code>false</code> inače
	 */
	public static boolean nicksMatch(HttpSession session, String nick) {
		String usersNick = (String) session.getAttribute("currentUserNick");
		if (usersNick == null || nick == null) {
			return false;
		}

		return usersNick.equals(nick);
	}

	/**
	 * Metoda koja iz predanog primjerka razreda koji implementira sučelje
	 * {@link HttpServletRequest} dohvaća vrijednost parametra pod nazivom
	 * <b>name</b>. Ukoliko ta vrijednost ne postoji ili se ne može parsirati u
	 * cijeli broj metoda vraća <code>null</code>.
	 *
	 * @param request
	 *            klijentov zahtjev modeliran sučeljem
	 *            {@link HttpServletRequest}
	 * @param name
	 *            ključ pod kojim je vrijednost parametra unutar klijentovog
	 *            zahtjeva modeliranog sa {@link HttpServletRequest}
	 * @return vrijednost mapiranu pod ključem <b>name</b> unutar parametara
	 *         koje je korisnik predao ili <code>null</code> u slučaju neke od
	 *         gore opisanih pogrešaka
	 */
	public static Long checkAndGetValue(HttpServletRequest request, String name) {
		String value = request.getParameter(name);

		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException ignorable) {
			}
		}

		return null;
	}
}
