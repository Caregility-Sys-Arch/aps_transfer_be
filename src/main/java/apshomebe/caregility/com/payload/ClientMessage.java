package apshomebe.caregility.com.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientMessage {
	private ClientCommand command;
	private String content;

}