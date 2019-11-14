package com.company;

public class AutomatedTellerMachine {

    public static Denomination currentDenomination;

    public static void main(String[] args) {
        Denomination fiveBills = new Denomination(5, 100);
        Denomination tenBills = new Denomination(10, 100);
        Denomination twentyBills = new Denomination(20, 100);
        Denomination fiftyBills = new Denomination(50, 100);

        currentDenomination = fiftyBills;
        fiftyBills.next = twentyBills;
        twentyBills.next = tenBills;
        tenBills.next = fiveBills;

        withdraw(215);
    }

    public static int totalAmount() {
        int amount = 0;
        Denomination current = currentDenomination;

        while(currentDenomination != null) {
            amount += currentDenomination.amount();
            currentDenomination = currentDenomination.next;
        }

        currentDenomination = current;

        return amount;
    }

    public static void withdraw(int amount) {
        int fifties = 0;
        int twenties = 0;
        int tens = 0;
        int fives = 0;

        int total = 0;

        Denomination current = currentDenomination;

        while(currentDenomination != null || total >= amount) {

            int withdraw = currentDenomination.withdraw(amount - total);

            if (currentDenomination.billValue == 50) {
                total += (withdraw * 50);
                fifties += withdraw;
            } else if (currentDenomination.billValue == 20) {
                total += (withdraw * 20);
                twenties += withdraw;
            } else if (currentDenomination.billValue == 10) {
                total += (withdraw * 10);
                tens += withdraw;
            } else if (currentDenomination.billValue == 5) {
                total += (withdraw * 5);
                fives += withdraw;
            }

            if (currentDenomination.next == null) {
                break;
            }

            currentDenomination = currentDenomination.next;
        }

        currentDenomination = current;

        System.out.println(fifties + " * €50 Bills");
        System.out.println(twenties + " * €20 Bills");
        System.out.println(tens + " * €10 Bills");
        System.out.println(fives + " * €5 Bills");
    }
}
