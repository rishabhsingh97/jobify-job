package com.rsuniverse.jobify_job.repos;

import com.rsuniverse.jobify_job.models.entities.ApplicationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApplicationEventRepo extends MongoRepository<ApplicationEvent, String> {
    List<ApplicationEvent> findAllByApplicationId(String applicationId);
    List<ApplicationEvent> findAllByUserId(String applicationId);
    List<ApplicationEvent> findAllByJobId(String applicationId);
}

