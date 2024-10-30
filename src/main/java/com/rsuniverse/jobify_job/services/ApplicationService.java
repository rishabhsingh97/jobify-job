package com.rsuniverse.jobify_job.services;

import com.rsuniverse.jobify_job.models.dtos.ApplicationDTO;
import com.rsuniverse.jobify_job.models.entities.Application;
import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import com.rsuniverse.jobify_job.models.responses.PaginatedRes;
import com.rsuniverse.jobify_job.repos.ApplicationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final ApplicationEventService applicationEventService;
    private final ModelMapper modelMapper;

    public PaginatedRes<ApplicationDTO> getAllApplicationsByUserId(String id, int page, int size) {
        log.info("Fetching all Applications for user id: {} - page: {}, size: {}", id, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Application> jobPage = applicationRepo.findAllByJobId(id, pageable);
        log.info("Total applications fetched for user id {}: {}", id, jobPage.getTotalElements());
        return getApplicationDTOPaginatedRes(jobPage);
    }

    public PaginatedRes<ApplicationDTO> getAllApplicationsByJobId(String id, int page, int size) {
        log.info("Fetching all Applications for job id: {} - page: {}, size: {}", id, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Application> jobPage = applicationRepo.findAllByJobId(id, pageable);
        log.info("Total applications fetched for job id {}: {}", id, jobPage.getTotalElements());
        return getApplicationDTOPaginatedRes(jobPage);
    }

    private PaginatedRes<ApplicationDTO> getApplicationDTOPaginatedRes(Page<Application> jobPage) {
        List<ApplicationDTO> applications = jobPage.getContent().stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .collect(Collectors.toList());

        log.info("Returning {} applications for the requested page", applications.size());

        return PaginatedRes.<ApplicationDTO>builder()
                .items(applications)
                .pageNum(jobPage.getNumber() + 1)
                .pageSize(jobPage.getSize())
                .totalPages(jobPage.getTotalPages())
                .totalCount(jobPage.getTotalElements())
                .build();
    }

    public ApplicationDTO getApplicationById(String id) {
        log.info("Fetching application by id: {}", id);
        Application application = applicationRepo.findById(id).orElseThrow(() -> new RuntimeException(""));
        log.info("Application found: {}", application);
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        log.info("Creating application: {}", applicationDTO);
        Application application = modelMapper.map(applicationDTO, Application.class);
        applicationRepo.save(application);
        log.info("Application created successfully: {}", application);
        applicationEventService.publish("created", applicationDTO);
        log.info("Published event for created application: {}", application.getId());
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public ApplicationDTO updateApplication(String id, ApplicationDTO applicationDTO) {
        log.info("Updating application with id: {}", id);
        Application application = findUserById(id);
        Application updatedApplicationFields = modelMapper.map(applicationDTO, Application.class);
        Application updatedApplication = this.validateAndUpdateFields(application, updatedApplicationFields);
        applicationRepo.save(updatedApplication);
        log.info("Application updated successfully: {}", application.getId());
        applicationEventService.publish("updated", applicationDTO);
        log.info("Published event for updated application: {}", application.getId());
        return modelMapper.map(application, ApplicationDTO.class);
    }

    private Application validateAndUpdateFields(Application application, Application updatedApplicationFields) {

        if (!(application.getUserId().equals(updatedApplicationFields.getUserId()))
                || !(application.getJobId().equals(updatedApplicationFields.getJobId()))) {
                throw new RuntimeException("another user already exists with this email");
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
        modelMapper.map(updatedApplicationFields, application);
        return application;
    }

    public void deleteApplication(String id) {
        log.info("Deleting application with id: {}", id);
        Application application = findUserById(id);
        applicationRepo.deleteById(id);
        applicationEventService.publish("deleted", id);
        log.info("Published event for deleted application: {}", id);
        log.info("Application with id {} deleted successfully", id);
    }

    public String updateApplicationStatus(String id, ApplicationStatus status) {
        log.info("Updating application status for id: {} to status: {}", id, status);
        Application application = findUserById(id);
        application.setStatus(status);
        Application updatedApplication = applicationRepo.save(application);
        applicationEventService.publish("update", updatedApplication);
        log.info("Published event for application status update: {}", id);
        return "Job status updated successfully";
    }

    private Application findUserById(String id) {
        return applicationRepo.findById(id).orElseThrow(()
                -> new RuntimeException("application not found with id: " + id));
    }
}