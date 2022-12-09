package apshomebe.caregility.com.websocket.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "aps_connection_status_history")
@Data
@EqualsAndHashCode(callSuper = false)
public class ApsWsConnectionStatusHistory extends ApsWsConnectionStatus {

}
