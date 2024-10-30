package com.rsuniverse.jobify_job.models.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "application_events")
public class ApplicationEvent {
    @Id
    private String id;
    private String userId;
    private String jobId;
    private String applicationId;
    private String type;
    private Object payload;
    private long timestamp;
}
