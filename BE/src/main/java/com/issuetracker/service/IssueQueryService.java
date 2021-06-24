package com.issuetracker.service;

import com.issuetracker.domain.elasticsearch.IssueDocumentRepository;
import com.issuetracker.domain.issue.Issue;
import com.issuetracker.domain.issue.IssueRepository;
import com.issuetracker.domain.user.User;
import com.issuetracker.exception.IssueNotFoundException;
import com.issuetracker.web.dto.reqeust.FilterRequestDTO;
import com.issuetracker.web.dto.reqeust.SearchRequestDTO;
import com.issuetracker.web.dto.response.*;
import com.issuetracker.web.dto.vo.Count;
import com.issuetracker.web.dto.vo.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.issuetracker.web.dto.vo.Status.CLOSE;
import static com.issuetracker.web.dto.vo.Status.OPEN;

@Service
@RequiredArgsConstructor
public class IssueQueryService {

    private final IssueDocumentRepository issueDocumentRepository;
    private final IssueRepository issueRepository;
    private final LabelService labelService;
    private final MilestoneService milestoneService;
    private final UserService userService;

    public IssuesResponseDTO searchIssues(SearchRequestDTO searchRequestDTO) {
        Count count = Count.builder()
                .label((int) labelService.count())
                .milestone((int) milestoneService.countByIsOpen(true))
                .openedIssue((int) issueDocumentRepository.countIssueDocumentByTitleAndIsOpen(searchRequestDTO.getQuery(), true))
                .closedIssue((int) issueDocumentRepository.countIssueDocumentByTitleAndIsOpen(searchRequestDTO.getQuery(), false))
                .build();
        List<IssueResponseDTO> issues = issueDocumentRepository.findAllByTitleAndIsOpen(searchRequestDTO.getQuery(), Status.statusToBoolean(searchRequestDTO.getStatus())).stream()
                .map(issueDocument -> IssueResponseDTO.of(issueDocument, userService.userDocumentsToAssignees(issueDocument), labelService.labelDocumentsToLabelDTOs(issueDocument)))
                .collect(Collectors.toList());
        return IssuesResponseDTO.of(count, issues);
    }

    public IssuesResponseDTO filterIssues(FilterRequestDTO filterRequest) {
        Count count = Count.builder()
                .label((int) labelService.count())
                .milestone((int) milestoneService.countByIsOpen(true))
                .openedIssue((int) issueRepository.countIssueFilteredByStatusAndSearchRequest(OPEN.getName(), filterRequest))
                .closedIssue((int) issueRepository.countIssueFilteredByStatusAndSearchRequest(CLOSE.getName(), filterRequest))
                .build();
        List<IssueResponseDTO> issues = issueRepository.findAllIssuesFilteredBySearchRequest(filterRequest).stream()
                .map(issue -> IssueResponseDTO.of(issue, userService.usersToAssignees(issue), labelService.labelsToLabelDTOs(issue)))
                .collect(Collectors.toList());
        return IssuesResponseDTO.of(count, issues);
    }

    public IssueFormResponseDTO getIssueForm() {
        return IssueFormResponseDTO.builder()
                .assignees(userService.usersToAssignees())
                .labels(labelService.findAllLabelDTOs())
                .milestones(milestoneService.findAllMilestoneDTOs())
                .build();
    }

    public IssueDetailPageResponseDTO getDetailPage(Long issueId, Long userId) {
        Issue issue = findIssueById(issueId);
        User loginUser = userService.findUserById(userId);
        return IssueDetailPageResponseDTO.of(
                issue,
                UserResponseDTO.of(issue.getAuthor()),
                commentsToCommentDTOs(loginUser, issue),
                userService.getCheckedAssignees(issue),
                labelService.getCheckedLabels(issue),
                milestoneService.findMilestoneInIssue(issue)
        );
    }

    public AssigneesResponseDTO getAssignees(Long issueId) {
        Issue issue = findIssueById(issueId);
        return new AssigneesResponseDTO(userService.usersToAssignees(issue));
    }

    public LabelsInIssueResponseDTO getLabels(Long issueId) {
        Issue issue = findIssueById(issueId);
        return new LabelsInIssueResponseDTO(labelService.labelsToLabelDTOs(issue));
    }

    public MilestonesInIssueResponseDTO getMilestones(Long issueId) {
        Issue issue = findIssueById(issueId);
        return new MilestonesInIssueResponseDTO(milestoneService.milestonesToMilestoneDTOs(issue));
    }

    private Issue findIssueById(Long id) {
        return issueRepository.findById(id).orElseThrow(IssueNotFoundException::new);
    }

    private List<CommentDTO> commentsToCommentDTOs(User user, Issue issue) {
        return issue.getComments().stream()
                .map(comment -> CommentDTO.createCommentDTO(user, issue, comment))
                .collect(Collectors.toList());
    }
}