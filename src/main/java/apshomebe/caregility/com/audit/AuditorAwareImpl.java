package apshomebe.caregility.com.audit;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
/*
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
*/
public class AuditorAwareImpl implements AuditorAware<String> {

	private static final Logger logger = LoggerFactory.getLogger(AuditorAwareImpl.class);

	@Override
	public Optional<String> getCurrentAuditor() {
/*
		return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated).map(Authentication::getName);

 */
		return Optional.of("SSSS");
	}


}
