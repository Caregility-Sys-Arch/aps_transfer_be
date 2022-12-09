package apshomebe.caregility.com.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApsBulkTransferRequest {

	private String process_request_id;
	private String process_request_type;
	private String command;
	private List<ApsTransferParamRequest> transfer_list;

	public ApsBulkTransferRequest(String command) {
		super();
		this.command = command;
	}

	@Override
	public String toString() {
		return "ApsTransferRequest{" + "process_request_id='" + process_request_id + '\'' + ", process_request_type='"
				+ process_request_type + '\'' + ", command='" + command + '\'' + ", transfer_list=" + transfer_list + '}';
	}
}