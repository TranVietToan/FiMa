package vn.tika.fima.Model;

import java.io.Serializable;

public class Income implements Serializable {
    private int idIncome;
    private String titleIncome;
    private int contentIncome;
    private String typeIncome;
    private String noteIncome;
    private String timeIncome;

    public Income(int idIncome, String titleIncome, int contentIncome,
                  String typeIncome, String noteIncome, String timeIncome) {
        this.idIncome = idIncome;
        this.titleIncome = titleIncome;
        this.contentIncome = contentIncome;
        this.typeIncome = typeIncome;
        this.noteIncome = noteIncome;
        this.timeIncome = timeIncome;
    }

    public int getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(int idIncome) {
        this.idIncome = idIncome;
    }

    public String getTitleIncome() {
        return titleIncome;
    }

    public void setTitleIncome(String titleIncome) {
        this.titleIncome = titleIncome;
    }

    public int getContentIncome() {
        return contentIncome;
    }

    public void setContentIncome(int contentIncome) {
        this.contentIncome = contentIncome;
    }

    public String getTypeIncome() {
        return typeIncome;
    }

    public void setTypeIncome(String typeIncome) {
        this.typeIncome = typeIncome;
    }

    public String getNoteIncome() {
        return noteIncome;
    }

    public void setNoteIncome(String noteIncome) {
        this.noteIncome = noteIncome;
    }

    public String getTimeIncome() {
        return timeIncome;
    }

    public void setTimeIncome(String timeIncome) {
        this.timeIncome = timeIncome;
    }
}
