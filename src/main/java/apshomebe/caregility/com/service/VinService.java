package apshomebe.caregility.com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apshomebe.caregility.com.exception.MachineAlredyMappedException;
import apshomebe.caregility.com.exception.MachineNotFoundException;
import apshomebe.caregility.com.exception.VinNotFoundException;
import apshomebe.caregility.com.models.AuthMachine;
import apshomebe.caregility.com.models.AuthMachineAudit;
import apshomebe.caregility.com.models.Vin;
import apshomebe.caregility.com.models.VinAudit;
import apshomebe.caregility.com.payload.VinResponce;
import apshomebe.caregility.com.repository.AuthMachineAuditRepository;
import apshomebe.caregility.com.repository.AuthMachineRepository;
import apshomebe.caregility.com.repository.VinAuditRepository;
import apshomebe.caregility.com.repository.VinRepository;

@Service
public class VinService {
	private static final Logger logger = LoggerFactory.getLogger(VinService.class);

	@Autowired
	VinRepository vinRepository;
	@Autowired
	AuthMachineRepository authMachineRepository;

	@Autowired
	VinAuditRepository vinAuditRepository;

	@Autowired
	AuthMachineAuditRepository machineAuditRepository;

	/**
	 * Generate The Unique VIN Number And Store That Into The Vin Collection
	 * 
	 * @return returns the unique vin
	 */

	@Transactional
	public Vin createNewVin() {
		logger.info("inside the VinService's createNewVin() method");
		String vinNumber = getUniqueVin();

		// store Vin object into the database
		Vin vin = new Vin();
		vin.setVinNumber(vinNumber);
		vin.setDeleted(false);

		VinAudit audit = new VinAudit(vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted());
		vinAuditRepository.save(audit);
		logger.info("exiting from  the VinService's createNewVin() method");
		return vinRepository.save(vin);

	}

	/**
	 * Map The Machine Name To The Given Vin Number
	 * 
	 * @param vinNumber
	 * @param machineName
	 * @return returns the mapped vin object
	 * @throws VinNotFoundException
	 * @throws MachineNotFoundException
	 * @throws MachineAlredyMappedException
	 */

	@Transactional
	public Vin mapVindetails(String vinNumber, String machineName)
			throws VinNotFoundException, MachineNotFoundException, MachineAlredyMappedException {

		logger.info("inside the VinService's mapVindetails() method");
		Vin vin = vinRepository.findByVinNumber(vinNumber)
				.orElseThrow(() -> new VinNotFoundException("Given Vin Number Does not exists in the db" + vinNumber));
		AuthMachine machine = authMachineRepository.findByMachineName(machineName)
				.orElseThrow(() -> new MachineNotFoundException("Machine not found with given name"));
		if (vinRepository.existsByAuthMachine_MachineName(machineName)) {
			throw new MachineAlredyMappedException("this machine is alredy mapped to some Vin");
		}
		// dump the record before update to the audit table

		vin.setAuthMachine(machine);
		machine.setMachineVin(vinNumber);
		authMachineRepository.save(machine);
		// audit the Machine
		AuthMachineAudit audit = new AuthMachineAudit(machine.getMachineName(), machine.getMachineUri(),
				machine.getMachineUriToDisplay(), machine.getMachineVin(), machine.isCloud(), machine.isPremises());

		VinAudit vinAudit = new VinAudit(vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted());

		vinAuditRepository.save(vinAudit);
		machineAuditRepository.save(audit);

		logger.info("exiting from  the VinService's mapVindetails() method");
		return vinRepository.save(vin);

	}

	/**
	 * this method will returns the Vin against machine name
	 * 
	 * @param machineName
	 * @return
	 * @throws MachineNotFoundException
	 */
	@Transactional
	public VinResponce getVinAgainstMachineName(String machineName) throws MachineNotFoundException {
		logger.info("inside the VinService's getVinAgainstMachineName() method");
		Vin vin = vinRepository.findByIsDeletedAndAuthMachine_MachineName(false, machineName)
				.orElseThrow(() -> new MachineNotFoundException("Machine not found with given name"));
		VinResponce responce = new VinResponce(vin.getId(), vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted());
		logger.info("exiting from  the VinService's getVinAgainstMachineName() method");
		return responce;

	}

	/**
	 * this method will do the logical deletion of the vin
	 * 
	 * @param vinNumber VinNumber TO be Deleted
	 * @throws VinNotFoundException If the Vin does not exists in the Database
	 */
	@Transactional
	public void deleteVin(String vinNumber) throws VinNotFoundException {
		logger.info("inside the VinService's deleteVin() method");

		Vin vin = vinRepository.findByVinNumber(vinNumber)
				.orElseThrow(() -> new VinNotFoundException("Given Vin Number Does not exists in the db" + vinNumber));

		if (Objects.nonNull(vin.getAuthMachine())) {
			AuthMachine machine = authMachineRepository.findByMachineName(vin.getAuthMachine().getMachineName()).get();
			if (Objects.nonNull(machine)) {
				machine.setMachineVin("");
				authMachineRepository.save(machine);

				// audit the Authmachine
				AuthMachineAudit audit = new AuthMachineAudit(machine.getMachineName(), machine.getMachineUri(),
						machine.getMachineUriToDisplay(), machine.getMachineVin(), machine.isCloud(),
						machine.isPremises());
				machineAuditRepository.save(audit);
			}
		}

		vin.setDeleted(true);

		vinRepository.save(vin);
		// dump the record before update to the audit table

		VinAudit audit = new VinAudit(vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted());
		vinAuditRepository.save(audit);
		logger.info("exiting from  the VinService's deleteVin() method");

	}

	/**
	 * This Method is used to get the machine name mapping for the specific VIN
	 * number cloud and on premise
	 * 
	 * @param vinNumber
	 * @return
	 */
	public List<VinResponce> getVinDetails(String vinNumber) {
		logger.info("inside the VinService's getVinDetails() method");
		List<VinResponce> findAllByVinNumber = vinAuditRepository.findAllByVinNumberAndAuthMachineIsNotNull(vinNumber);
		logger.info("exiting from  the VinService's getVinDetails() method");
		return findAllByVinNumber;
	}

	/**
	 * Export the all the VIN details along with machine name for integration
	 * purpose
	 * 
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> getVinExportDetails(Pageable pageable) {
		logger.info("inside the VinService's getVinExportDetails() method");
		List<VinResponce> vinDetails = new ArrayList<>();
		Page<VinResponce> responcePage = vinRepository.findAllByIsDeletedAndAuthMachineIsNotNull(false, pageable);
		vinDetails = responcePage.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("vinDetails", vinDetails);
		response.put("currentPage", responcePage.getNumber());
		response.put("totalItems", responcePage.getTotalElements());
		response.put("totalPages", responcePage.getTotalPages());
		logger.info("exiting from  the VinService's getVinExportDetails() method");
		return response;

	}

	@Transactional
	public void clearMapping(String vinNumber) throws VinNotFoundException {
		logger.info("inside the VinService's clearMapping() method");

		Vin vin = vinRepository.findByVinNumber(vinNumber)
				.orElseThrow(() -> new VinNotFoundException("Given Vin Number Does not exists in the db" + vinNumber));

		if (Objects.nonNull(vin.getAuthMachine())) {
			AuthMachine machine = authMachineRepository.findByMachineName(vin.getAuthMachine().getMachineName()).get();
			if (Objects.nonNull(machine)) {
				machine.setMachineVin("");
				authMachineRepository.save(machine);

				// audit the Authmachine
				AuthMachineAudit audit = new AuthMachineAudit(machine.getMachineName(), machine.getMachineUri(),
						machine.getMachineUriToDisplay(), machine.getMachineVin(), machine.isCloud(),
						machine.isPremises());
				machineAuditRepository.save(audit);
			}
		}
		vin.setAuthMachine(null);
		vinRepository.save(vin);
		// dump the record before update to the audit table

		VinAudit audit = new VinAudit(vin.getVinNumber(), vin.getAuthMachine(), vin.isDeleted());
		vinAuditRepository.save(audit);
		logger.info("exiting from  the VinService's clearMapping() method");

	}

	/**
	 * This Method Will Generate Unique Vin Number
	 * 
	 * @return
	 */
	private synchronized String getUniqueVin() {
		logger.info("inside the VinService's getUniqueVin() method");
		String vinNumber = RandomStringUtils.randomAlphanumeric(24);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSS");
		LocalDateTime now = LocalDateTime.now();
		vinNumber += dtf.format(now);
		logger.info("exiting from  the VinService's getUniqueVin() method");
		return vinNumber;
	}

}
