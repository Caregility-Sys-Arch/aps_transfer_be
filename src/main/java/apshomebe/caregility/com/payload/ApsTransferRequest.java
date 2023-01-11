package apshomebe.caregility.com.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApsTransferRequest {

	private String process_request_id;
	private String process_request_type;

	private String transactionId;
	private String command;
	private ApsTransferParamRequest params;

	public ApsTransferRequest(String command) {
		super();
		this.command = command;
	}

	@Override
	public String toString() {
		return "ApsTransferRequest{" +
				"process_request_id='" + process_request_id + '\'' +
				", process_request_type='" + process_request_type + '\'' +
				", command='" + command + '\'' +
				", params=" + params +
				'}';
	}
}