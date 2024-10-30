package com.rsuniverse.jobify_job.repos;

import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rsuniverse.jobify_job.models.entities.Application;


@Repository
public interface ApplicationRepo extends MongoRepository<Application, String>{
     Page<Application> findAllById(String jobId, Pageable pageable);
     Page<Application> findAllByJobId(String jobId, Pageable pageable);
}
