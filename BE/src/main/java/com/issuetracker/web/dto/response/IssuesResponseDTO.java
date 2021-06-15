package com.issuetracker.web.dto.response;

import com.issuetracker.web.dto.vo.Count;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class IssuesResponseDTO {

    private final Count count;
    private final List<IssueResponseDTO> issues;

    public static IssuesResponseDTO of(Count count, List<IssueResponseDTO> issues) {
        return IssuesResponseDTO.builder()
                .count(count)
                .issues(issues)
                .build();
    }

//    public static IssuesResponseDTO blankIssuesResponseDTO(int ) {
//        return IssuesResponseDTO.builder()
//                .count(Count.builder()
//                        .label(0)
//                        .milestone(0)
//                        .openedIssue(0)
//                        .closedIssue(0)
//                        .build())
//                .issues(new ArrayList<>())
//                .build();
//    }
}
