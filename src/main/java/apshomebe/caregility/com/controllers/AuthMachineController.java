package apshomebe.caregility.com.controllers;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apshomebe.caregility.com.exception.MachineNotFoundException;
import apshomebe.caregility.com.exception.TokenRefreshException;
import apshomebe.caregility.com.models.AuthMachine;
import apshomebe.caregility.com.models.AuthMachineAudit;
import apshomebe.caregility.com.models.RefreshToken;
import apshomebe.caregility.com.models.Vin;
import apshomebe.caregility.com.models.VinAudit;
import apshomebe.caregility.com.payload.AuthMachineRequest;
import apshomebe.caregility.com.payload.JwtAuthResponce;
import apshomebe.caregility.com.payload.MachineRegisterRequest;
import apshomebe.caregility.com.payload.MessageResponse;
import apshomebe.caregility.com.payload.TokenRefreshRequest;
import apshomebe.caregility.com.payload.TokenRefreshResponse;
import apshomebe.caregility.com.repository.AuthMachineAuditRepository;
import apshomebe.caregility.com.repository.AuthMachineRepository;
import apshomebe.caregility.com.repository.VinAuditRepository;
import apshomebe.caregility.com.repository.VinRepository;
import apshomebe.caregility.com.security.JwtAuthUtils;
import apshomebe.caregility.com.service.AuthDetailsImpl;
import apshomebe.caregility.com.service.AuthDetailsServiceImpl;
import apshomebe.caregility.com.service.RefreshTokenService;

@RestController
@RequestMapping("/api/authmachine")
public class AuthMachineController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	RefreshTokenService refreshTokenService;
	@Autowired
	AuthDetailsServiceImpl authDetailsServiceImpl;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthMachineAuditRepository auditRepository;

	@Autowired
	AuthMachineRepository authMachineRepository;

	@Autowired
	VinRepository vinRepository;

	@Autowired
	JwtAuthUtils jwtAuthUtils;
	@Autowired
	VinAuditRepository vinAuditRepository;

	@PostMapping("/autnticate")
	//TODO neee to remove this
	public ResponseEntity<?> authnticateMachine(@Valid @RequestBody AuthMachineRequest authMachineRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authMachineRequest.getMachineName(), authMachineRequest.getEnvUri()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		AuthDetailsImpl authDetails = (AuthDetailsImpl) authentication.getPrincipal();

		//String jwt = jwtAuthUtils.generateJwtToken(authDetails);
//		List<String> roles = authDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
		//RefreshToken refreshToken = refreshTokenService.createRefreshToken(authDetails.getId());

		return ResponseEntity.ok(new JwtAuthResponce(/*jwt*/"nc baschaschjxz chjbchjbckxkbjc xzghjcvhcb kashucbzc\sv", authDetails.getUsername(),
				authDetails.getMachineUriToDisplay(), /*refreshToken.getToken()*/"njasbfdasiufbdsfhds"));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerMachine(@Valid @RequestBody MachineRegisterRequest registerRequest)

	{

		if (authMachineRepository.existsByMachineName(registerRequest.getMachineName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:Machine already Exists"));

		}

		AuthMachine machine = new AuthMachine(registerRequest.getMachineName(),
				encoder.encode(registerRequest.getMachineUri()), registerRequest.getMachineUri(),
				registerRequest.getMachineVin(), registerRequest.isCloud(), registerRequest.isPremises());
		AuthMachineAudit audit = new AuthMachineAudit(machine.getMachineName(), machine.getMachineUri(),
				machine.getMachineUriToDisplay(), machine.getMachineVin(), machine.isCloud(), machine.isPremises());
		authMachineRepository.save(machine);
		auditRepository.save(audit);

		return ResponseEntity.ok(new MessageResponse("Machine registered successfully!"));

	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getAuthMachine).map(authMachine -> {
					//String token = jwtAuthUtils.generateTokenFromUsername(authMachine.getMachineName());
					return ResponseEntity.ok(new TokenRefreshResponse(/*token*/"Zbgsv", requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException( "Refresh token is not in database!"));
	}

	@PutMapping("/update")
	public void switchMachine(@RequestBody AuthMachineRequest authMachineRequest) throws MachineNotFoundException {
		AuthMachine authMachineToUpdate = authMachineRepository.findByMachineName(authMachineRequest.getMachineName())
				.orElseThrow(() -> new MachineNotFoundException("Machine Not fond with given name"));

		authMachineToUpdate.setMachineUri(encoder.encode(authMachineRequest.getEnvUri()));
		authMachineToUpdate.setMachineUriToDisplay(authMachineRequest.getEnvUri());

		if (authMachineRequest.getMachineToConnect().equals("cloud")) {
			authMachineToUpdate.setCloud(true);
			authMachineToUpdate.setPremises(false);
		}
		if (authMachineRequest.getMachineToConnect().equals("premise")) {
			authMachineToUpdate.setCloud(false);
			authMachineToUpdate.setPremises(true);

		}

		authMachineRepository.save(authMachineToUpdate);
		AuthMachineAudit audit = new AuthMachineAudit(authMachineToUpdate.getMachineName(),
				authMachineToUpdate.getMachineUri(), authMachineToUpdate.getMachineUriToDisplay(),
				authMachineToUpdate.getMachineVin(), authMachineToUpdate.isCloud(), authMachineToUpdate.isPremises());
		// update the latest Authmachine to the respective vin
		String machineVin = authMachineToUpdate.getMachineVin();
		if (machineVin != null && !"".equals(machineVin)) {
			Vin vinToUpdate = vinRepository.findByVinNumber(machineVin).get();
			if (Objects.nonNull(vinToUpdate)) {
				vinToUpdate.setAuthMachine(authMachineToUpdate);
				vinRepository.save(vinToUpdate);

				VinAudit vinaudit = new VinAudit(vinToUpdate.getVinNumber(), vinToUpdate.getAuthMachine(),
						vinToUpdate.isDeleted());
				vinAuditRepository.save(vinaudit);
			}
		}

		// send to audit collection
		auditRepository.save(audit);

	}

}
