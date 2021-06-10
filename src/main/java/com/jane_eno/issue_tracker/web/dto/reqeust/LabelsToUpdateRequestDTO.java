package com.jane_eno.issue_tracker.web.dto.reqeust;

import com.jane_eno.issue_tracker.web.dto.response.LabelDTO;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@ToString
@NoArgsConstructor
public class LabelsToUpdateRequestDTO {

    private List<LabelDTO> labels;
}