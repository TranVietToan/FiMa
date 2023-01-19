package vn.tika.fima.Interface;

public interface IncomeInterface {
    public void showInputIncomeDataFragment();
    public void reloadIncomeFragment();
    public void getDataIncomeUpdate(int ID, String Title, int Content, String Type, String Note, String Time);
    public void insertIncomeDate( String Title, int Content, String Type, String Note, String Time);
    public void updateIncomeData(int ID, String Title, int Content, String Type,  String Note,String Time);
}
