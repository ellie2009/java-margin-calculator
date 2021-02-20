package sample;

public class MarginCalculation {

    public static double[] calculateMargin(double price, double cost, double shipping, double handling, double vat, double fees){

        double feesToPay = (price * fees) / 100;

        double vatToPay;
        if(vat == 0){
            vatToPay = 0;
        }else {
            vatToPay = (price / (1 + (vat / 100)) - price) * -1;
        }

        double profit = price - cost - shipping - handling - vatToPay - feesToPay;

        double margin = (profit / (price - vatToPay)) * 100;

        return new double[]{margin, profit, vatToPay, feesToPay};
    }
}
