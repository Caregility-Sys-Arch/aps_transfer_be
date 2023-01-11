package apshomebe.caregility.com.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientMessage {
	private String processRequestId;
	private String transactionId;
	private ClientCommand command;
	private String apsFromIpAddress;
	private String apsToIpAddress;
	private String content;

	public String getProcessRequestId() {
		return processRequestId;
	}

	public void setProcessRequestId(String processRequestId) {
		this.processRequestId = processRequestId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public ClientCommand getCommand() {
		return command;
	}

	public void setCommand(ClientCommand command) {
		this.command = command;
	}

	public String getApsFromIpAddress() {
		return apsFromIpAddress;
	}

	public void setApsFromIpAddress(String apsFromIpAddress) {
		this.apsFromIpAddress = apsFromIpAddress;
	}

	public String getApsToIpAddress() {
		return apsToIpAddress;
	}

	public void setApsToIpAddress(String apsToIpAddress) {
		this.apsToIpAddress = apsToIpAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}