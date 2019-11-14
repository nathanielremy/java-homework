package com.company;

public class Denomination {
    final int billValue;
    int numberOfBills;
    public Denomination next = null;

    Denomination(int _billValue, int _numberOfBills) {
        this.billValue = _billValue;
        this.numberOfBills = _numberOfBills;
    }

    public int amount() {
        return billValue * numberOfBills;
    }

    public int withdraw(int amount) {
        if (numberOfBills == 0) {
            return 0;
        } else if (billValue > amount) {
            return 0;
        }
        int billsToWithdraw = (amount / billValue);

        if(billsToWithdraw > numberOfBills) {
            int billsToReturn = numberOfBills;
            numberOfBills = 0;

            return billsToReturn;
        }

        numberOfBills -= billsToWithdraw;

        return billsToWithdraw;
    }
}
