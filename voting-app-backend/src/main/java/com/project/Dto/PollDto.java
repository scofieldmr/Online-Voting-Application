package com.project.Dto;

import jakarta.persistence.ElementCollection;
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
public class PollDto {

    private long id;
    private String questions;
    private List<OptionVoteDto> options = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public List<OptionVoteDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionVoteDto> options) {
        this.options = options;
    }
}
