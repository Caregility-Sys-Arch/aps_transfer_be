package apshomebe.caregility.com.websocket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;
import apshomebe.caregility.com.websocket.repository.ApsTransferTransactionsRepository;

@Service
public class ApsTransferTransactionsServiceImpl implements ApsTransferTransactionsService {
	@Autowired
	ApsTransferTransactionsRepository apsTransferTransactionsRepository;

	@Override
	public List<ApsTransferTransactions> listAllApsTransferTransactions() {
		return apsTransferTransactionsRepository.findAll();
	}

}
