package creditcardfx;

import com.sun.prism.paint.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import static javafx.scene.input.KeyCode.Z;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tree
 */
public class AddCard extends Application implements EventHandler<ActionEvent> {

    @Override
    public void start(Stage stage) {

        Text firstNameLabel = new Text("Name :  ");

        TextField nameText = new TextField();
        nameText.setPromptText("Your Client First Name Here.");

        Text surnameLabel = new Text("Surname :  ");

        TextField surnameText = new TextField();
        surnameText.setPromptText("Your Client Surname Here.");

        Text dobLabel = new Text("Date of Card Creation :  ");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        Text StartCardLable = new Text("Start Card Status :  ");

        ToggleGroup choiceCard = new ToggleGroup();
        RadioButton normalRadio = new RadioButton("Normal");
        normalRadio.setToggleGroup(choiceCard);
        RadioButton forfeitRadio = new RadioButton("Forfeit");
        forfeitRadio.setToggleGroup(choiceCard);
        normalRadio.setSelected(true);

        Text cardType = new Text("Card Type :  ");

        ToggleButton CardType = new ToggleButton();
        ToggleButton credit = new ToggleButton("Credit");
        ToggleButton debit = new ToggleButton("Debit");
        ToggleGroup cardSelection = new ToggleGroup();
        credit.setToggleGroup(cardSelection);
        debit.setToggleGroup(cardSelection);
        credit.setSelected(true);

        Text ccvGenaration = new Text("CCV Generation :  ");

        CheckBox lowerAlphabetCheckBox = new CheckBox("Lowercase Alphabet (a-z)    ");
        lowerAlphabetCheckBox.setIndeterminate(false);
        lowerAlphabetCheckBox.setSelected(true);

        CheckBox capitalizeAlphabetCheckBox = new CheckBox("Capitalize Alphabet (A-Z)   ");
        lowerAlphabetCheckBox.setIndeterminate(false);
        capitalizeAlphabetCheckBox.setSelected(true);

        Text moneyBoundLabel = new Text("Money Boundary :  ");

        ObservableList<String> suggestMoneyBound = FXCollections.observableArrayList(
                "10,000,000", "5,000,000", "2,000,000", "1,000,000", "500,000", "200,000", "100,000", "70,000", "50,000", "40,000", "30,000", "25,000", "20,000", "18,000", "15,000", "12,000", "10,000", "7,500", "5,000", "2,500", "1,000", "Manually Insert");
        ChoiceBox moneyBound = new ChoiceBox();
        moneyBound.setItems(suggestMoneyBound);
        moneyBound.setValue(suggestMoneyBound.get(11));

        TextField moneyBoundText = new TextField();
        moneyBoundText.setPromptText("Insert Money Amount.");
        moneyBoundText.setText(suggestMoneyBound.get(11));
        moneyBoundText.setEditable(false);

        moneyBound.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue ov, Number t, Number t1) {
                if (t1.intValue() == 21) {
                    moneyBoundText.setEditable(true);
                    moneyBoundText.setPromptText("Insert Money Amount.");
                } else {
                    moneyBoundText.setPromptText("Insert Money Amount.");
                    moneyBoundText.setPromptText(suggestMoneyBound.get(t1.intValue()));
                    moneyBoundText.setText(suggestMoneyBound.get(t1.intValue()));
                    moneyBoundText.setEditable(false);

                }
            }

        });

        Text tierLabel = new Text("Card Tier :  ");

        ChoiceBox tierChoiceBox = new ChoiceBox();
        ObservableList<String> tierList = FXCollections.observableArrayList(
                "Normal", "Silver", "Gold", "Platinum");
        tierChoiceBox.setItems(tierList);
        tierChoiceBox.setValue(tierList.get(0));

        Button buttonConfirm = new Button("Confirm");
        buttonConfirm.setMinSize(200, 50);
        buttonConfirm.setOnAction((ActionEvent event) -> {
            System.out.println("Confirm Click");

            boolean confirmError = false;
            boolean firstNameError = false;
            boolean SurnameError = false;
            boolean moneyError = false;

            if (nameText.getText()
                    == null || Utils.isAlpha(nameText.getText()) == false || nameText.getText() == "" || nameText.getText().length() < 1) {
                System.out.println("FirstName Error");
                firstNameError = true;
                confirmError = true;
            }

            if (surnameText.getText()
                    == null || Utils.isAlpha(surnameText.getText()) == false || surnameText.getText() == "" || surnameText.getText().length() < 1) {
                System.out.println("Surname Error");
                SurnameError = true;
                confirmError = true;
            }

            if (moneyBoundText.getText()
                    == null || moneyBoundText.getText() == ""
                    || moneyBoundText.getText().contains(" ") == true
                    || moneyBoundText.getText().matches(".*[a-zA-Z]+.*")
                    || moneyBoundText.getText().length() < 1 || (moneyBoundText.getText().length() - moneyBoundText.getText().replace(".", "").length()) > 1) {
                System.out.println("Money Value Error");
                moneyError = true;
                confirmError = true;
            }

            if (confirmError
                    == true) {
                ConfirmAlertBox.callConfirmErrorAlert(firstNameError, SurnameError, moneyError);
            } else if (confirmError
                    == false) {

                String insertNameString = Utils.capitalizedFirstChar(nameText.getText());
                String insertSurnameString = Utils.capitalizedFirstChar(surnameText.getText());
                LocalDate insertDate = datePicker.getValue();

                String insertCardStatus = "Normal";
                if (normalRadio.isSelected()) {
                    insertCardStatus = "Normal";
                } else if (forfeitRadio.isSelected()) {
                    insertCardStatus = "Forfeit";
                }

                String insertCardType = "Credit";
                if (credit.isSelected()) {
                    insertCardType = "Credit";
                } else if (debit.isSelected()) {
                    insertCardType = "Debit";
                }

                boolean insertLower = false;
                if (lowerAlphabetCheckBox.isSelected()) {
                    insertLower = true;
                }

                boolean insertCap = false;
                if (capitalizeAlphabetCheckBox.isSelected()) {
                    insertCap = true;
                }

                //String insertMoneyBoundString = Utils.removeAllNonNumeric(moneyBoundText.getText());
                String insertMoneyBoundString = Utils.moneyFormatGetRidOfComma(moneyBoundText.getText());
                insertMoneyBoundString = Utils.moneyFormatCommaAndDecimal(insertMoneyBoundString);

                String insertCardTier = "Normal";
                if (tierChoiceBox.getValue() == "Normal") {
                    insertCardTier = "Normal";
                } else if (tierChoiceBox.getValue() == "Silver") {
                    insertCardTier = "Silver";
                } else if (tierChoiceBox.getValue() == "Gold") {
                    insertCardTier = "Gold";
                } else if (tierChoiceBox.getValue() == "Platinum") {
                    insertCardTier = "Platinum";
                }

                String summaryInfo = "First Name : " + insertNameString
                        + "\nSurname : " + insertSurnameString + "\nDate Create : "
                        + insertDate.format(DateTimeFormatter.ISO_DATE) + "\nCard Status : "
                        + insertCardStatus + "\nCard Type : " + insertCardType + "\nCCV Option Lowercase (a-z) : "
                        + insertLower + "\nCCV Option Capitalize (A-Z) : " + insertCap + "\nMoney Boundery Value : "
                        + insertMoneyBoundString + "\nCard Tier : " + insertCardTier;

                if (ConfirmAlertBox.callConfirmCheckAlert(summaryInfo)) {
                    try {
                        CreditCard.confirmCreateCard(insertCardStatus, insertCardType, insertLower, insertCap, insertMoneyBoundString, insertCardTier, insertDate, insertNameString, insertSurnameString);
                    } catch (IOException ex) {
                        Logger.getLogger(AddCard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();

        //Setting size for the pane 
        gridPane.setMinSize(700, 600);
        gridPane.setMaxSize(700, 600);


        //Setting the padding    
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(10);
        gridPane.setHgap(8);

        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);

        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameText, 1, 1);

        gridPane.add(dobLabel, 0, 2);
        gridPane.add(datePicker, 1, 2);

        gridPane.add(StartCardLable, 0, 3);
        gridPane.add(normalRadio, 1, 3);
        gridPane.add(forfeitRadio, 2, 3);
        gridPane.add(cardType, 0, 4);
        gridPane.add(credit, 1, 4);
        gridPane.add(debit, 2, 4);

        gridPane.add(ccvGenaration, 0, 5);
        gridPane.add(lowerAlphabetCheckBox, 1, 5);
        gridPane.add(capitalizeAlphabetCheckBox, 2, 5);

        gridPane.add(moneyBoundLabel, 0, 6);
        gridPane.add(moneyBound, 1, 6);

        gridPane.add(moneyBoundText, 2, 6);

        gridPane.add(tierLabel, 0, 7);
        gridPane.add(tierChoiceBox, 1, 7);

        gridPane.add(buttonConfirm, 1, 9);
        //Styling nodes   
        // buttonConfirm.setStyle(
        //          "-fx-font-size:40; -fx-textfill: white;");
        /*
        firstNameLabel.setStyle("-fx-font-size:40; -fx-textfill: white;");
        surnameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        dobLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        StartCardLable.setStyle("-fx-font: normal bold 15px 'serif' ");
        cardType.setStyle("-fx-font: normal bold 15px 'serif' ");
        ccvGenaration.setStyle("-fx-font: normal bold 15px 'serif' ");
        moneyBoundLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        tierLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
         */
        //Setting the back ground color 
        //  gridPane.setStyle("-fx-background-color: BEIGE;");

        //Creating a scene object 
        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(getClass().getResource("/creditcardfx/styleAddCard.css").toExternalForm());
        buttonConfirm.setStyle(
                "-fx-font-size:40;");
        firstNameLabel.setStyle("-fx-font-size:23 ; -fx-fill: white;-fx-font-family: \"RSU\";");
        surnameLabel.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        dobLabel.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        StartCardLable.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        cardType.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        ccvGenaration.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        moneyBoundLabel.setStyle("-fx-font-size:23; -fx-fill: #F8F8F8;-fx-font-family: \"RSU\";");
        tierLabel.setStyle("-fx-font-size:23;-fx-fill: #F8F8F8;-fx-font-family: \"RSU\"; ");
        lowerAlphabetCheckBox.setStyle("-fx-font-size:18;-fx-text-fill: #F8F8F8;-fx-font-family: \"RSU\"; ");
        capitalizeAlphabetCheckBox.setStyle("-fx-font-size:18;-fx-text-fill: #F8F8F8;-fx-font-family: \"RSU\"; ");
        normalRadio.setStyle("-fx-font-size:18;-fx-text-fill: #F8F8F8;-fx-font-family: \"RSU\"; ");
        forfeitRadio.setStyle("-fx-font-size:18;-fx-text-fill: #F8F8F8;-fx-font-family: \"RSU\"; ");
        
        //Setting title to the Stage 
        stage.setTitle("Registration Form");

        //Adding scene to the stage 
        stage.setScene(scene);

        //Displaying the contents of the stage 
        stage.show();

    }

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
