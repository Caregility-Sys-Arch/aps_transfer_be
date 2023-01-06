package apshomebe.caregility.com.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "aps_transfer_details_copy")
public class ApsTransferCopy {
    @Id
    private  String id;
    private String processRequestId;
    private String processRequestType;
    private String command;
    private ApsTransferParam params;
    private String status = "Initiated";
    private Date createdAt;
    private Date completedAt;
}
