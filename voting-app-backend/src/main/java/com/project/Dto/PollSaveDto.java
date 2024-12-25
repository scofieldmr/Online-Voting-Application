package com.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PollSaveDto {

    private String questions;
    private List<OptionVoteSaveDto> options = new ArrayList<>();

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public List<OptionVoteSaveDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionVoteSaveDto> options) {
        this.options = options;
    }
}
