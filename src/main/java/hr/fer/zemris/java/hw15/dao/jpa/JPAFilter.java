package hr.fer.zemris.java.hw15.dao.jpa;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Razred koji implementira sučelje {@link Filter}. Primjerci ovog razreda
 * koriste se kako bi presreli zahtjev korisnika za određeni resurs na
 * postlužitelju te u trenutku kada resurs krene vraćati odgovor zatvore
 * korišteni primjerak razreda {@link EntityManager} preko
 * {@link JPAEMProvider#close()} metode.
 * 
 * @see Filter
 * @see EntityManager
 * @see JPAEMProvider
 * 
 * @author Davor Češljaš
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}
	}

	@Override
	public void destroy() {
	}

}