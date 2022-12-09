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
	private String content;

}