package vn.tika.fima.Model;

import java.io.Serializable;

public class Spending implements Serializable {
    private int idSpending;
    private String titleSpending;
    private int contentSpending;
    private String typeSpending;
    private String topicSpending;
    private String noteSpending;
    private String timeSpending;

    public Spending(int idSpending, String titleSpending, int contentSpending, String typeSpending,
                    String topicSpending, String noteSpending, String timeSpending) {
        this.idSpending = idSpending;
        this.titleSpending = titleSpending;
        this.contentSpending = contentSpending;
        this.typeSpending = typeSpending;
        this.topicSpending = topicSpending;
        this.noteSpending = noteSpending;
        this.timeSpending = timeSpending;
    }

    public int getIdSpending() {
        return idSpending;
    }

    public void setIdSpending(int idSpending) {
        this.idSpending = idSpending;
    }

    public String getTitleSpending() {
        return titleSpending;
    }

    public void setTitleSpending(String titleSpending) {
        this.titleSpending = titleSpending;
    }

    public int getContentSpending() {
        return contentSpending;
    }

    public void setContentSpending(int contentSpending) {
        this.contentSpending = contentSpending;
    }

    public String getTypeSpending() {
        return typeSpending;
    }

    public void setTypeSpending(String typeSpending) {
        this.typeSpending = typeSpending;
    }

    public String getTopicSpending() {
        return topicSpending;
    }

    public void setTopicSpending(String topicSpending) {
        this.topicSpending = topicSpending;
    }

    public String getNoteSpending() {
        return noteSpending;
    }

    public void setNoteSpending(String noteSpending) {
        this.noteSpending = noteSpending;
    }

    public String getTimeSpending() {
        return timeSpending;
    }

    public void setTimeSpending(String timeSpending) {
        this.timeSpending = timeSpending;
    }
}
