package apshomebe.caregility.com.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
//
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	@Value("${apssynch.server.cors.allow}")
	List<String> APSSYNCH_SERVER_CORS_ALLOW;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin",
				origin != null && APSSYNCH_SERVER_CORS_ALLOW.contains(origin) ? origin : "*");
		// Access-Control-Allow-Methods
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		// Access-Control-Allow-Credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
		// Access-Control-Allow-Headers
		response.setHeader("Access-Control-Allow-Headers",
				"Origin,Authorization, X-Requested-With, WWW-Authenticate,Content-Type, Accept, " + "X-CSRF-TOKEN");


		chain.doFilter(req, res);
	}


	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}