package com.dyamicforms.model;

import java.util.List;

public class MCheckboxAnswer {
    private int id;
    private List<String> answer;

    public MCheckboxAnswer(int id, List<String> answer) {
        this.id = id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }
}