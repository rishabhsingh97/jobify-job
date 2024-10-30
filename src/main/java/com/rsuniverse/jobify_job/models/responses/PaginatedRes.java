package com.rsuniverse.jobify_job.models.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedRes<T> {
    private List<T> items;
    private int pageNum;
    private int pageSize;
    private int totalPages;
    private long totalCount;

}
