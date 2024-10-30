package com.rsuniverse.jobify_job.services;

import com.rsuniverse.jobify_job.models.entities.Application;
import com.rsuniverse.jobify_job.models.entities.ApplicationEvent;
import com.rsuniverse.jobify_job.models.entities.Job;
import com.rsuniverse.jobify_job.models.entities.JobEvent;
import com.rsuniverse.jobify_job.repos.ApplicationEventRepo;
import com.rsuniverse.jobify_job.repos.JobEventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobEventService {

    private final JobEventRepo jobEventRepo;

    public void publish(Job job, String action, Object payload) {
        JobEvent eventEntity = new JobEvent();
        eventEntity.setAction(action);
        eventEntity.setPayload(payload);
        eventEntity.setTimestamp(System.currentTimeMillis());
        jobEventRepo.save(eventEntity);
    }
}
