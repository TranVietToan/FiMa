package vn.tika.fima.Interface;

public interface SpendingInterface {
    public void showInputSpendingDataFragment();
    public void reloadSpendingFragment();
    public void getDataSpendingUpdate(int ID, String Title, int Content, String Type, String Topic, String Note, String Time);
    public void insertSpendingDate( String Title, int Content, String Type, String Topic,String Note, String Time);
    public void updateSpendingData(int ID, String Title, int Content, String Type, String Topic, String Note,String Time);
}
