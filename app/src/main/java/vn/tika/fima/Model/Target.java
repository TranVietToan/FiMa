package vn.tika.fima.Model;

import java.io.Serializable;

public class Target implements Serializable {
    private int idTarget;
    private String titleTarget;
    private int contentTarget;
    private int subContentTarget = 0;
    private String timeStart_Target;
    private String timeEnd_Target;
    private String typeTarget;

    public Target(int idTarget, String titleTarget, int contentTarget, int subContentTarget, String timeStart_Target, String timeEnd_Target) {
        this.idTarget = idTarget;
        this.titleTarget = titleTarget;
        this.contentTarget = contentTarget;
        this.subContentTarget = subContentTarget;
        this.timeStart_Target = timeStart_Target;
        this.timeEnd_Target = timeEnd_Target;
    }

    public Target(int idTarget, String titleTarget, int contentTarget, int subContentTarget, String timeStart_Target, String timeEnd_Target, String typeTarget) {
        this.idTarget = idTarget;
        this.titleTarget = titleTarget;
        this.contentTarget = contentTarget;
        this.subContentTarget = subContentTarget;
        this.timeStart_Target = timeStart_Target;
        this.timeEnd_Target = timeEnd_Target;
        this.typeTarget = typeTarget;
    }

    public int getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(int idTarget) {
        this.idTarget = idTarget;
    }

    public String getTitleTarget() {
        return titleTarget;
    }

    public void setTitleTarget(String titleTarget) {
        this.titleTarget = titleTarget;
    }

    public int getContentTarget() {
        return contentTarget;
    }

    public void setContentTarget(int contentTarget) {
        this.contentTarget = contentTarget;
    }

    public int getSubContentTarget() {
        return subContentTarget;
    }

    public void setSubContentTarget(int subContentTarget) {
        this.subContentTarget = subContentTarget;
    }

    public String getTimeStart_Target() {
        return timeStart_Target;
    }

    public void setTimeStart_Target(String timeStart_Target) {
        this.timeStart_Target = timeStart_Target;
    }

    public String getTimeEnd_Target() {
        return timeEnd_Target;
    }

    public void setTimeEnd_Target(String timeEnd_Target) {
        this.timeEnd_Target = timeEnd_Target;
    }

    public String getTypeTarget() {
        return typeTarget;
    }

    public void setTypeTarget(String typeTarget) {
        this.typeTarget = typeTarget;
    }
}
