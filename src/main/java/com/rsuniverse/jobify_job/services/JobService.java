package com.rsuniverse.jobify_job.services;

import com.rsuniverse.jobify_job.models.dtos.JobDTO;
import com.rsuniverse.jobify_job.models.entities.Job;
import com.rsuniverse.jobify_job.models.responses.PaginatedRes;
import com.rsuniverse.jobify_job.repos.JobRepo;
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
public class JobService {

    private final JobRepo jobRepo;
    private final ModelMapper modelMapper;
    private final JobEventService jobEventService;

    public PaginatedRes<JobDTO> getAllJobs(int page, int size) {
        log.info("Fetching all jobs - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Job> jobPage = jobRepo.findAll(pageable);

        List<JobDTO> jobs = jobPage.getContent().stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());

        log.info("Total jobs fetched: {}, Total pages: {}", jobPage.getTotalElements(), jobPage.getTotalPages());

        return PaginatedRes.<JobDTO>builder()
                .items(jobs)
                .pageNum(jobPage.getNumber() + 1)
                .pageSize(jobPage.getSize())
                .totalPages(jobPage.getTotalPages())
                .totalCount(jobPage.getTotalElements())
                .build();
    }

    public JobDTO getJobById(String id) {
        log.info("Fetching job by id: {}", id);
        Job job = jobRepo.findById(id).orElseThrow(() -> {
            log.error("Job not found with id: {}", id);
            return new RuntimeException("Job not found with id: " + id);
        });
        log.info("Job found: {}", job);
        return modelMapper.map(job, JobDTO.class);
    }

    public JobDTO createJob(JobDTO jobDTO) {
        log.info("Creating job: {}", jobDTO);
        Job job = modelMapper.map(jobDTO, Job.class);
        jobRepo.save(job);
        log.info("Job created successfully: {}", job);
        jobEventService.publish(job, "created", jobDTO);
        log.info("Published job creation event for job id: {}", job.getId());

        return modelMapper.map(job, JobDTO.class);
    }

    public JobDTO updateJob(String id, JobDTO jobDTO) {
        log.info("Updating job with id: {}", id);
        Job job = findJobById(id);
        modelMapper.map(jobDTO, job);
        jobRepo.save(job);
        log.info("Job updated successfully: {}", job);
        jobEventService.publish(job, "updated", jobDTO);
        log.info("Published job update event for job id: {}", job.getId());
        return modelMapper.map(job, JobDTO.class);
    }

    public void deleteJob(String id) {
        log.info("Deleting job with id: {}", id);
        Job job = findJobById(id);
        if (job != null) {
            jobRepo.deleteById(id);
            log.info("Job with id {} deleted successfully", id);
            jobEventService.publish(job, "deleted", id);
            log.info("Published job deletion event for job id: {}", id);
        } else {
            log.warn("Job with id {} not found", id);
        }
    }

    private Job findJobById(String id) {
        return jobRepo.findById(id).orElseThrow(()
                -> new RuntimeException("job not found with id: " + id));
    }
}
