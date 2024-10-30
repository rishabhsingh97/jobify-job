package com.rsuniverse.jobify_job.controllers;

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

import com.rsuniverse.jobify_job.models.dtos.JobDTO;
import com.rsuniverse.jobify_job.models.responses.BaseRes;
import com.rsuniverse.jobify_job.models.responses.PaginatedRes;
import com.rsuniverse.jobify_job.services.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    
    private final JobService jobService;

    @GetMapping("")
    public ResponseEntity<BaseRes<PaginatedRes<JobDTO>>> getAllJobs(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Incoming request to get all jobs");
        try {
            PaginatedRes<JobDTO> paginatedRes = jobService.getAllJobs(page, size);
            return BaseRes.<PaginatedRes<JobDTO>>success(paginatedRes);
        } catch (Exception e) {
            log.error("Error occurred while fetching jobs: {}", e.getMessage());
            return BaseRes.error("Error fetching jobs: " + e.getMessage(), 100, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRes<JobDTO>> getJobById(@PathVariable String id) {
        log.info("Incoming request to get job with id: {}", id);
        try {
            JobDTO jobDTO = jobService.getJobById(id);
            return BaseRes.success(jobDTO);
        } catch (Exception e) {
            log.error("Error occurred while fetching job with id {}: {}", id, e.getMessage());
            return BaseRes.error(e.getMessage(), 101, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<BaseRes<JobDTO>> createJob(@RequestBody @Valid JobDTO jobDTO) {
        log.info("Incoming request to create job: {}", jobDTO);
        try {
            JobDTO createdJob = jobService.createJob(jobDTO);
            return BaseRes.success(createdJob);
        } catch (Exception e) {
            log.error("Error occurred while creating job: {}", e.getMessage());
            return BaseRes.error("Error creating job: " + e.getMessage(), 102, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseRes<JobDTO>> updateJob(@PathVariable String id, @RequestBody @Valid JobDTO jobDTO) {
        log.info("Incoming request to update job with id: {}, job: {}", id, jobDTO);
        try {
            JobDTO updatedJob = jobService.updateJob(id, jobDTO);
            return BaseRes.success(updatedJob);
        } catch (Exception e) {
            log.error("Error occurred while updating job with id {}: {}", id, e.getMessage());
            return BaseRes.error("Error updating job: " + e.getMessage(), 103, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseRes<Void>> deleteJob(@PathVariable String id) {
        log.info("Incoming request to delete job with id: {}", id);
        try {
            jobService.deleteJob(id);
            return BaseRes.success(null);
        } catch (Exception e) {
            log.error("Error occurred while deleting job with id {}: {}", id, e.getMessage());
            return BaseRes.error("Error deleting job: " + e.getMessage(), 104, HttpStatus.NOT_FOUND);
        }
    }
}
