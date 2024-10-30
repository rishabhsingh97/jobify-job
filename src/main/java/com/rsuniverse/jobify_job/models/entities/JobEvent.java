package com.rsuniverse.jobify_job.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "application_events")
public class JobEvent {

    @Id
    private String id;
    private String action;
    private Object payload;
    private long timestamp;
}
