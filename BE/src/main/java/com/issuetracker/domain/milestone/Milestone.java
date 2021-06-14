package com.issuetracker.domain.milestone;

import com.issuetracker.domain.BaseTimeEntity;
import com.issuetracker.domain.issue.Issue;
import com.issuetracker.web.dto.response.MilestoneDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;

    @OneToMany(mappedBy = "milestone")
    private List<Issue> issues;

    public static Milestone create(MilestoneDTO milestoneDTO) {
        return Milestone.builder()
                .id(milestoneDTO.getId())
                .title(milestoneDTO.getTitle())
                .description(milestoneDTO.getDescription())
                .dueDate(milestoneDTO.getDueDate())
                .build();
    }

    public Milestone update(MilestoneDTO milestoneDTO) {
        this.title = milestoneDTO.getTitle();
        this.dueDate = milestoneDTO.getDueDate();
        this.description = milestoneDTO.getDescription();
        return this;
    }

    public Long countOpenedIssues() {
        return issues.stream()
                .filter(Issue::isOpen)
                .count();
    }

    public Long countClosedIssues() {
        return issues.stream()
                .filter(Issue::isClosed)
                .count();
    }
}