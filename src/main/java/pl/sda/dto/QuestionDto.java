package pl.sda.dto;

import java.util.List;

public class QuestionDto {
    private long id;
    private String body;
    private List<String> options;

    public QuestionDto(long id, String body, List<String> options) {
        this.id = id;
        this.body = body;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
