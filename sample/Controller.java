package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Controller {

    @FXML
    TextField txtPrice, txtCost, txtShipping, txtHandling, txtVat, txtFees;
    @FXML
    Button btnCalculate;
    @FXML
    Pane resultDisplay;
    @FXML
    Label lblIncorrectInput, lblMargin, lblProfit, lblVAT, lblFees;

    private double price;
    private double cost;
    private double shipping;
    private double handling;
    private double vat;
    private double fees;


    public void calculate(){

        if(validateUserInput()) {
            // correct user input, calculate margin
            getUserInput();
            calculateMargin();

            if(lblIncorrectInput.isVisible()){
                lblIncorrectInput.setVisible(false);
            }
            resultDisplay.setVisible(true);
        }else {
            // incorrect user input - required fields missing or non-numeric value entered
            lblIncorrectInput.setText("Please fill in all required (*) fields and ensure you enter numbers only.");

            if(resultDisplay.isVisible()){
                resultDisplay.setVisible(false);
            }
            lblIncorrectInput.setVisible(true);
        }
    }



    private void getUserInput(){
        // required user input
        price = Double.parseDouble(txtPrice.getText());
        cost = Double.parseDouble(txtCost.getText());
        vat = Double.parseDouble(txtVat.getText());

        // optional user input
        shipping = txtShipping.getText().isEmpty() ? 0 : Double.parseDouble(txtShipping.getText());
        handling = txtHandling.getText().isEmpty() ? 0 : Double.parseDouble(txtHandling.getText());
        fees = txtFees.getText().isEmpty() ? 0 : Double.parseDouble(txtFees.getText());
    }


    private boolean validateUserInput(){
        ArrayList<TextField> userInput = new ArrayList<>();

        if(txtPrice.getText().isEmpty() || txtCost.getText().isEmpty() || txtVat.getText().isEmpty()) {
            return false;
        }

        userInput.add(txtPrice);
        userInput.add(txtCost);
        userInput.add(txtShipping);
        userInput.add(txtHandling);
        userInput.add(txtVat);
        userInput.add(txtFees);

        for (int i = 0; i < userInput.size(); i++){
            if (!isNumeric(userInput.get(i).getText()) && !userInput.get(i).getText().isEmpty()){
                return false;
            }
        }

        return true;
    }


    private boolean isNumeric(String value){
        try{
            double d = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }


    private void calculateMargin(){
        double[] result = MarginCalculation.calculateMargin(price, cost, shipping, handling, vat, fees);
        double margin = result[0];
        double profit = result[1];
        double vatToPay = result[2];
        double feesToPay = result[3];


        if (margin < 0){
            lblMargin.setTextFill(Paint.valueOf("#FF0000"));
        } else{
            lblMargin.setTextFill(Paint.valueOf("#006400"));
        }

        lblMargin.setText(String.format("%.2f", margin) + "%");
        lblProfit.setText(String.format("£ %.2f", profit));
        lblVAT.setText(String.format("£ %.2f", vatToPay));
        lblFees.setText(String.format("£ %.2f", feesToPay));
    }

}
