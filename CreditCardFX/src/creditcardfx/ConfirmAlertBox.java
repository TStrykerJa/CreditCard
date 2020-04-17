package creditcardfx;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ConfirmAlertBox {

    public static void callConfirmErrorAlert(boolean firstnameErr, boolean surnameErr, boolean moneyErr) {

        // create a alert 
        Alert a = new Alert(AlertType.NONE);

        // set alert type 
        a.setAlertType(AlertType.ERROR);

        String firstNameError = "\n\nINVALID or WRONG FORMAT First name.\n"
                + "The first name can only contain English alphabet only.\n"
                + "The first name can be both in lowercase and uppercase.";

        String surNameError = "\n\nINVALID or WRONG FORMAT Surname.\n"
                + "The surname can only contain English alphabet only.\n"
                + "The surname can be both in lowercase and uppercase.";

        String moneyError = "\n\nINVALID or WRONG FORMAT Money value.\n"
                + "The money value can only contain numeral only with exception of comma (,) and only one dot (.).\n"
                + "The comma does not need to be in correct position.";

        String mainError = "A problem has been detected "
                + "and this alert has been call up to prevent "
                + "user from trying to create wrong or error"
                + " information on the card.\n\nThe problem seems "
                + "to be caused by these following error.";

        // set content text 
        if (firstnameErr == true) {
            mainError = mainError + firstNameError;
        }
        if (surnameErr == true) {
            mainError = mainError + surNameError;
        }
        if (moneyErr == true) {
            mainError = mainError + moneyError;
        }
        a.setContentText(mainError);

        // show the dialog 
        a.show();
    }

    public static boolean callConfirmCheckAlert(String cardInfo) {

        boolean OK_Click = false;
        // create a alert 
        Alert a = new Alert(AlertType.NONE);

        // set alert type 
        a.setAlertType(AlertType.CONFIRMATION);

        // set content text 
        a.setContentText("The draft information of this current card has been create.\n"
                + "Please check this summary to ensure the correctness of the information on this card.\n\n"
                + "The summary information of this card can be seen below.\n\n"
                + cardInfo + "\n\nIf there are incorrect information and the user want to change information or even cancel this "
                + "operation entirely, press"
                + " CANCEL to return back to registation form." + "\n\nTo proceed with this information, press OK"
                + " to confirm registation of this card with the current information."
                + "\nNow this progression have not yet been save, but if you proceed by press OK then this information will be save by that point\n\n Do you wish to save this card information?\n");

        // show the dialog 
        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            OK_Click = true;
        }

        return OK_Click;
    }

}
