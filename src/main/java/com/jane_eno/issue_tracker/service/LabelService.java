package com.jane_eno.issue_tracker.service;

import com.jane_eno.issue_tracker.domain.label.Color;
import com.jane_eno.issue_tracker.domain.label.Label;
import com.jane_eno.issue_tracker.domain.label.LabelRepository;
import com.jane_eno.issue_tracker.domain.user.User;
import com.jane_eno.issue_tracker.exception.ElementNotFoundException;
import com.jane_eno.issue_tracker.web.dto.response.LabelsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jane_eno.issue_tracker.web.dto.response.LabelDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public List<Label> findLabels(List<Long> assigneeIdList) {
        return labelRepository.findAllById(assigneeIdList);
//        return assigneeIdList.stream().map(this::findByLabelId).collect(Collectors.toList());
    }

    public List<Label> findAllLabels() {
        return labelRepository.findAll();
    }

//    public List<LabelDTO> findAllLabelDTOs() {
//        return labelRepository.findAll().stream().map(l->);
//    }

    private Label findByLabelId(Long labelId) {
        return labelRepository.findById(labelId).orElseThrow(
                () -> new ElementNotFoundException("Cannot find label by given id.")
        );
    }

    public LabelsResponseDTO read() {
        return LabelsResponseDTO.builder()
                .labelsCount(3)
                .milestonesCount(2)
                .labels(new ArrayList<>(Arrays.asList(
                        new LabelDTO(1L, "bug", new Color("#FFFFFF", "#CCFFCC"), "bug fix", true),
                        new LabelDTO(2L, "enhancement", new Color("#FFFFFF", "#99FFFF"), "enhancement", false)
                )))
                .build();
    }

    public void create(LabelDTO label) {
        labelRepository.save(Label.createLabel(label));
    }

    public void update(Long labelId, LabelDTO label) {
    }

    public void delete(Long labelId) {

    }

    public long count() {
        return labelRepository.count();
    }
}