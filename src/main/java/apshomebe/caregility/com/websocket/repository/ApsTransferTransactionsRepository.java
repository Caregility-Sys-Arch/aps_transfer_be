package apshomebe.caregility.com.websocket.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionName;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionStatus;

@Repository
public interface ApsTransferTransactionsRepository extends MongoRepository<ApsTransferTransactions, String> {
	@Query("{process_request_id :?0,transactionName:?1, transactionStatus:?2}")
	List<ApsTransferTransactions> findByProcessRequestIdAndTransactionStatus(String process_request_id,
			EApsTransferTransactionName prepareData, EApsTransferTransactionStatus apsTransferTransactionStatus);

	@Query("{transactionId :?0, transactionName:?1, transactionStatus:?2}")
	ApsTransferTransactions findTransferRequestDataByTransactionId(String transactionId,
			EApsTransferTransactionName apsTransferTransactionName,
			EApsTransferTransactionStatus apsTransferTransactionStatus);

}