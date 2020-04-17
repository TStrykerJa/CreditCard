package creditcardfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tree
 */
public class CSVReaderInJava {

    static List<CreditCard> readCardsFromCSV(String fileName) {
        List<CreditCard> cards = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split("#");

                CreditCard card = createCard(attributes);

                // adding book into ArrayList
                cards.add(card);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return cards;
    }

    private static CreditCard createCard(String[] metadata) {
        String serial = metadata[0];
        String ccv = metadata[1];
        String name = metadata[2];
        String surname = metadata[3];
        LocalDate issueDate = LocalDate.parse(metadata[4]);
        LocalDate expiryDate = LocalDate.parse(metadata[5]);
        String type = metadata[6];
        String tier = metadata[7];
        String moneyBoundary = metadata[8];
        String state = metadata[9];

        // create and return book of this metadata
        return new CreditCard(serial, ccv, name,surname,issueDate,expiryDate,type,tier,moneyBoundary,state);
    }
    
}
