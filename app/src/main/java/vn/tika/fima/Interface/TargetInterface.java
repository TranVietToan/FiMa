package vn.tika.fima.Interface;

public interface TargetInterface {
    public void showInputShortTargetData();
    public void reloadTargetFragment();
    public void getDataShortTargetUpdate(int ID, String Title, int Content, int subContent, String TimeStart,String timeEnd);
    public void insertShortTargetDate(String Title, int Content, int subContent, String TimeStart,String timeEnd);
    public void updateShortTargetData(int ID, String Title, int Content, int subContent, String timeStart,String timeEnd);

    public void showInputLongTargetData();
    public void getDataLongTargetUpdate(int ID, String Title, int Content, int subContent, String TimeStart,String timeEnd);
    public void insertLongTargetDate(String Title, int Content, int subContent, String TimeStart,String timeEnd);
    public void updateLongTargetData(int ID, String Title, int Content, int subContent, String timeStart,String timeEnd);
}
