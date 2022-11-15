package apshomebe.caregility.com.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apshomebe.caregility.com.exception.MachineAlredyMappedException;
import apshomebe.caregility.com.exception.MachineNotFoundException;
import apshomebe.caregility.com.exception.VinNotFoundException;
import apshomebe.caregility.com.models.Vin;
import apshomebe.caregility.com.payload.MessageResponse;
import apshomebe.caregility.com.payload.VinResponce;
import apshomebe.caregility.com.service.VinService;

@RestController
@RequestMapping("/api/v1")
public class VinController {
	private static final Logger logger = LoggerFactory.getLogger(VinController.class);

	@Autowired
	VinService vinService;

	@PostMapping("/vin/create")
	public ResponseEntity<?> createVin() {
		logger.info("inside the Vincontroller's createVin() method");
		Vin vin = vinService.createNewVin();

		logger.info("exiting  from the  Vincontroller's createVin() method");
		return ResponseEntity
				.ok(new VinResponce(vin.getId(), vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted()));
	}

	@PutMapping("/vin/{vinNumber}/map/machine/{machineName}")
	public ResponseEntity<?> mapVin(@PathVariable String vinNumber, @PathVariable String machineName)
			throws VinNotFoundException, MachineNotFoundException, MachineAlredyMappedException {
		logger.info("inside the Vincontroller's mapVin() method");
		Vin mapVindetails = vinService.mapVindetails(vinNumber, machineName);
		logger.info("exiting  from the  Vincontroller's mapVin() method");
		return ResponseEntity.ok(mapVindetails);

	}

	@GetMapping("/vin/{machineName}")
	public ResponseEntity<VinResponce> getVin(@PathVariable String machineName) throws MachineNotFoundException {
		return ResponseEntity.ok(vinService.getVinAgainstMachineName(machineName));
	}

	@DeleteMapping("/vin/{vinNumber}")
	public void deleteVin(@PathVariable String vinNumber) throws VinNotFoundException {
		vinService.deleteVin(vinNumber);
	}

	@GetMapping("/vin/{vinNumber}/details")
	public ResponseEntity<List<VinResponce>> getVinDetails(@PathVariable String vinNumber) {
		List<VinResponce> vinDetails = vinService.getVinDetails(vinNumber);
		return ResponseEntity.ok(vinDetails);
	}

	@GetMapping("/vin/details/export")
	public ResponseEntity<Map<String, Object>> exportVinDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		Pageable paging = PageRequest.of(page, size);
		Map<String, Object> vinExportDetails = vinService.getVinExportDetails(paging);

		return ResponseEntity.ok(vinExportDetails);

	}

	@PutMapping("/vin/{vinNumber}/refresh")
	public ResponseEntity<?> clearMapping(@PathVariable String vinNumber) throws VinNotFoundException {
		vinService.clearMapping(vinNumber);
		return ResponseEntity.ok(new MessageResponse("mappings cleard for given vin"));
	}

}
