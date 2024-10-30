package com.rsuniverse.jobify_job.models.dtos;

import java.time.LocalDate;

import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationDTO {
    private String id; 
    private String jobId;  
    private String userId;  
    private String textResume;  
    private LocalDate appliedDate;
    private ApplicationStatus status;
}
