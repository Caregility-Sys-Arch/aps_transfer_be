package apshomebe.caregility.com.models;

import java.util.Date;
import java.util.List;

import apshomebe.caregility.com.payload.ApsTransferParamRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Document(collection = "aps_bulk_transfer_details")
@Data
public class ApsBulkTransfer {
	@Id
	private String id;
	private String process_request_id;
	private String process_request_type;
	private String transactionId;
	private String command;
	private List<ApsTransferParamRequest> transfer_list;

	private Date createdAt;
	private Date completedAt;
	private String status;

}
