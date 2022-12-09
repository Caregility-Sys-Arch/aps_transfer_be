package apshomebe.caregility.com.websocket.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apshomebe.caregility.com.service.ApsTransferEnvironmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	@Value("${apssynch.server.cors.allow}")
	List<String> APSSYNCH_SERVER_CORS_ALLOW;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("Origin");
		logger.debug("Origin:{}" , origin);
		response.setHeader("Access-Control-Allow-Origin",
				origin != null && APSSYNCH_SERVER_CORS_ALLOW.contains(origin) ? origin : "*");
		// Access-Control-Allow-Methods
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		// Access-Control-Allow-Credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		// Access-Control-Allow-Headers
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
		chain.doFilter(req, res);
	}

}