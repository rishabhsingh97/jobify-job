package com.rsuniverse.jobify_job.models.dtos;

import java.time.LocalDate;

import com.rsuniverse.jobify_job.models.enums.JobStatus;
import lombok.Data;

@Data
public class JobDTO {
    private String id;  
    private String title;
    private String description;
    private String company;
    private String location;
    private String employmentType;
    private LocalDate postedDate;
    private String postedBy;
    private JobStatus status;
}