package apshomebe.caregility.com.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApsTransferParamRequest {
	private String from_ip_address;
	private String to_ip_address;
	private String from_machine_name;
	private String to_machine_name;
	private String session_id;
}