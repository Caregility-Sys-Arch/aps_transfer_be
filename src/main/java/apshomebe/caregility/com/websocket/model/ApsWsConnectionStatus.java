package apshomebe.caregility.com.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "aps_connection_status")
@Data
public class ApsWsConnectionStatus {
	@Id
	private String id;
	private String current_machine_name;
	private String current_socket_id;
	private String current_ip_address;
	private EConnectionStatus status;
	private String comment;
	private Date created_at;
	private Date updated_at;
}
