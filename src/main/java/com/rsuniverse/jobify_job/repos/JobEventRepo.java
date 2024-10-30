package com.rsuniverse.jobify_job.repos;

import com.rsuniverse.jobify_job.models.entities.JobEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobEventRepo extends MongoRepository<JobEvent, String> {
}

