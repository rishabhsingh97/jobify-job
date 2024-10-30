package com.rsuniverse.jobify_job.controllers;

import com.rsuniverse.jobify_job.models.enums.ApplicationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsuniverse.jobify_job.models.dtos.ApplicationDTO;
import com.rsuniverse.jobify_job.models.responses.BaseRes;
import com.rsuniverse.jobify_job.models.responses.PaginatedRes;
import com.rsuniverse.jobify_job.services.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/users/{id}")
    public ResponseEntity<BaseRes<PaginatedRes<ApplicationDTO>>> getAllApplicationsByUserId(@RequestParam(defaultValue = "1") int page,
                                                                                        @RequestParam(defaultValue = "10") int size,
                                                                                        @PathVariable String id) {
        log.info("Incoming request to get all applications by id: {}", id);
            PaginatedRes<ApplicationDTO> paginatedRes = applicationService.getAllApplicationsByUserId(id, page, size);
            return BaseRes.success(paginatedRes);
    }

//    @GetMapping("/jobs/{id}")
//    public ResponseEntity<BaseRes<PaginatedRes<ApplicationDTO>>> getAllApplicationsByJobId(@RequestParam(defaultValue = "1") int page,
//                                                                                           @RequestParam(defaultValue = "10") int size,
//                                                                                           @PathVariable String id) {
//        log.info("Incoming request to get all applications by job id: {}", id);
//            PaginatedRes<ApplicationDTO> paginatedRes = applicationService.getAllApplicationsByJobId(id, page, size);
//            return BaseRes.success(paginatedRes);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRes<ApplicationDTO>> getApplicationByApplicationId(@PathVariable String id) {
        log.info("Incoming request to get job with id: {}", id);
            ApplicationDTO jobDTO = applicationService.getApplicationById(id);
            return BaseRes.success(jobDTO);
    }

    @PostMapping("")
    public ResponseEntity<BaseRes<ApplicationDTO>> createApplication(@RequestBody @Valid ApplicationDTO jobDTO) {
        log.info("Incoming request to create job: {}", jobDTO);
            ApplicationDTO createdApplication = applicationService.createApplication(jobDTO);
            return BaseRes.success(createdApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseRes<ApplicationDTO>> updateApplication(@PathVariable String id, @RequestBody @Valid ApplicationDTO jobDTO) {
        log.info("Incoming request to update job with id: {}, job: {}", id, jobDTO);
            ApplicationDTO updatedApplication = applicationService.updateApplication(id, jobDTO);
            return BaseRes.success(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseRes<Void>> deleteApplication(@PathVariable String id) {
        log.info("Incoming request to delete job with id: {}", id);
        applicationService.deleteApplication(id);
        return BaseRes.success(null);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BaseRes<String>> updateApplicationStatus(@PathVariable String id, @RequestParam ApplicationStatus status) {
        String result = applicationService.updateApplicationStatus(id, status);
        return BaseRes.success(result);
    }
}
