package apshomebe.caregility.com.service;

import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
*/
import apshomebe.caregility.com.models.AuthMachine;
import apshomebe.caregility.com.repository.AuthMachineRepository;
/*
@Service
public class AuthDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	AuthMachineRepository authMachineRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	/ *
		AuthMachine authMachine=authMachineRepository
				.findByMachineName(username)
				.orElseThrow(()-> new UsernameNotFoundException("Machine Not Found with MachineName: " + username));
		return AuthDetailsImpl.build(authMachine);

	 * /
		return null;
	}

}

 */
