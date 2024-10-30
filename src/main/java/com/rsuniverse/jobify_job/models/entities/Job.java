package com.rsuniverse.jobify_job.models.entities;

import java.time.LocalDate;

import com.rsuniverse.jobify_job.models.enums.JobStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "jobs")
public class Job {
    @Id
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