package apshomebe.caregility.com.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apshomebe.caregility.com.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import apshomebe.caregility.com.service.AuthDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

  
  @Autowired
  JwtAuthUtils authUtils;

//  @Autowired
//  private UserDetailsServiceImpl userDetailsService;
  
  @Autowired
  private AuthDetailsServiceImpl authDetailsServiceImpl;

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      try {
		if (jwt != null  /*&&authUtils.validateJwtToken(jwt)*/) {
		   // String username = jwtUtils.getUserNameFromJwtToken(jwt);
		    String username = authUtils.getUserNameFromJwtToken(jwt);

            String environment=authUtils.getEnvironmentFromJwtToken(jwt);
            username=username+"]"+environment;
		    //UserDetails userDetails =userDetailsService.loadUserByUsername(username);
		    // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		    UserDetails userDetails= customUserDetailsService.loadUserByUsername(username);
            //UserDetails userDetails= customUserDetailsService.loadUserByUsername(/*username*/"nckd");

		     
		    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
		        userDetails.getAuthorities());
		    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		    SecurityContextHolder.getContext().setAuthentication(authentication);
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}