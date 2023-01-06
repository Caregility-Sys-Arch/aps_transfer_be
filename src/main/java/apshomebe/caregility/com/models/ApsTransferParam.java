package apshomebe.caregility.com.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApsTransferParam {
	private String fromIpAddress;
	private String toIpAddress;
	private String fromMachineName;
	private String toMachineName;
	private String sessionId;
}