package com.rsuniverse.jobify_job.models.entities;

import java.time.LocalDate;

import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import com.rsuniverse.jobify_job.models.enums.JobStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "applications")
public class Application {
    
    @Id
    private String id; 
    private String jobId;  
    private String userId;  
    private String textResume;  
    private LocalDate appliedDate;
    private ApplicationStatus status;
}
