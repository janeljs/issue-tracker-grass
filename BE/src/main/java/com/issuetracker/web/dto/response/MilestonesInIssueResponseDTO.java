package com.issuetracker.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MilestonesInIssueResponseDTO {

    private final MilestoneDTO milestone;
}