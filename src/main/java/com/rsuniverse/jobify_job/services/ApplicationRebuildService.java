package com.rsuniverse.jobify_job.services;

import com.rsuniverse.jobify_job.models.entities.Application;
import com.rsuniverse.jobify_job.models.entities.ApplicationEvent;
import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationRebuildService {

    private final ApplicationEventService eventService;

    public Application rebuildApplicationState(String applicationId) {
        List<ApplicationEvent> events = eventService.getEventsForApplication(applicationId);
        Application application = new Application();

        application.setId(applicationId);
        application.setStatus(ApplicationStatus.PENDING);

        for (ApplicationEvent event : events) {
            applyEvent(application, event);
        }

        return application;
    }

    private void applyEvent(Application application, ApplicationEvent event) {
        switch (event.getType()) {
            case "add":
                Application newApplication = (Application) event.getPayload();
                application.setId(newApplication.getId());
                application.setJobId(newApplication.getJobId());
                application.setUserId(newApplication.getUserId());
                application.setTextResume(newApplication.getTextResume());
                application.setAppliedDate(newApplication.getAppliedDate());
                application.setStatus(newApplication.getStatus());
                break;

            case "delete":
                application.setStatus(ApplicationStatus.DELETED);
                break;

            case "update":
                Application updatedApplication = (Application) event.getPayload();
                if (updatedApplication.getJobId() != null) {
                    application.setJobId(updatedApplication.getJobId());
                }
                if (updatedApplication.getUserId() != null) {
                    application.setUserId(updatedApplication.getUserId());
                }
                if (updatedApplication.getTextResume() != null) {
                    application.setTextResume(updatedApplication.getTextResume());
                }
                if (updatedApplication.getAppliedDate() != null) {
                    application.setAppliedDate(updatedApplication.getAppliedDate());
                }
                if (updatedApplication.getStatus() != null) {
                    application.setStatus(updatedApplication.getStatus());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown event type: " + event.getType());
        }
    }

}
