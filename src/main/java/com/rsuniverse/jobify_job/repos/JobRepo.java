package com.rsuniverse.jobify_job.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rsuniverse.jobify_job.models.entities.Job;

@Repository
public interface JobRepo extends MongoRepository<Job, String> {
}
