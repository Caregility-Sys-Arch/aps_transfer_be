package apshomebe.caregility.com.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import apshomebe.caregility.com.payload.ApsTransferRequest;
import lombok.Data;

@Document(collection = "aps_transactions")
@Data
public class ApsTransferTransactions {
	@Id
	private String id;
	private String process_request_id;
	private String transactionId;// This is Id of Bulk Transfer Request DB Id
//	 private String apsMachineName;
	 private String socketId;
	 //Todo delete if not needed
	 private String apsFromMachineName;
	 private String apsFromIpAddress;
	private String apsToMachineName;
	private String apsToIpAddress;
	//Todo delete if not needed



	private EApsTransferTransactionName transactionName;
	private EApsTransferTransactionStatus transactionStatus;
	ApsTransferRequest apsTransferRequest;
	private String comment;
	private Date createdAt;
	private Date updatedAt;



}
