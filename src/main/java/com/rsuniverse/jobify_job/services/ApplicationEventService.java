package com.rsuniverse.jobify_job.services;

import com.rsuniverse.jobify_job.models.entities.Application;
import com.rsuniverse.jobify_job.models.entities.ApplicationEvent;
import com.rsuniverse.jobify_job.repos.ApplicationEventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationEventService {

    private final ApplicationEventRepo eventRepository;

    public void publish(String action, Object payload) {
        ApplicationEvent eventEntity = new ApplicationEvent();
        eventEntity.setPayload(payload);
        eventEntity.setTimestamp(System.currentTimeMillis());
        eventRepository.save(eventEntity);
    }

    public List<ApplicationEvent> getEventsForApplication(String applicationId) {
        return eventRepository.findAllByApplicationId(applicationId);
    }

    public List<ApplicationEvent> getEventsForApplicationsByUserId(String userId) {
        return eventRepository.findAllByUserId(userId);
    }

    public List<ApplicationEvent> getEventsForApplicationsByJobId(String jobId) {
        return eventRepository.findAllByJobId(jobId);
    }
}
