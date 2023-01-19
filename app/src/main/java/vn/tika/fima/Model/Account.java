package vn.tika.fima.Model;

public class Account {
    private int Cash;
    private int moneyInCard;

    public Account(int cash, int moneyInCard) {
        Cash = cash;
        this.moneyInCard = moneyInCard;
    }

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }

    public int getMoneyInCard() {
        return moneyInCard;
    }

    public void setMoneyInCard(int moneyInCard) {
        this.moneyInCard = moneyInCard;
    }
}
