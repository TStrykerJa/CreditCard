package creditcardfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;

public class CreditCard {

    private String serial;
    private String ccv;
    private String name;
    private String surname;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String type;
    private String tier;
    private String moneyBoundary;
    private String state;

    public CreditCard() {

    }

    public CreditCard(String serial, String name, String surname, LocalDate issueDate, LocalDate expiryDate, String type, String tier, String moneyBoundary, String state) {
        this.serial = serial;
        this.ccv = RandomString.getAlphaNumericString(3);
        this.name = name;
        this.surname = surname;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.type = type;
        this.tier = tier;
        this.moneyBoundary = moneyBoundary;
        this.state = state;
    }

    public CreditCard(String serial, String ccv, String name, String surname, LocalDate issueDate, LocalDate expiryDate, String type, String tier, String moneyBoundary, String state) {
        this.serial = serial;
        this.ccv = ccv;
        this.name = name;
        this.surname = surname;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.type = type;
        this.tier = tier;
        this.moneyBoundary = moneyBoundary;
        this.state = state;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial() throws FileNotFoundException, IOException {

        System.out.println("Randomize New Card Number.");

        String randomStringNum = RandomStringNumber.getAlphaNumericString(16);
        boolean checkRandomPass = false;

        String path = Utils.fileLocation;

        File file = new File(path);

        if (!file.exists()) {
            file.createNewFile();

        } else {

            try (BufferedReader brCheck = new BufferedReader(new FileReader(file))) {

                String line;

                do {
                    brCheck.mark(1);
                    System.out.println("Start Checking.");
                    while ((line = brCheck.readLine()) != null) {

                        String[] arr = line.split("#");
                        System.out.println(arr[0]);
                        if (randomStringNum.equals(arr[0])) {
                            randomStringNum = RandomStringNumber.getAlphaNumericString(16);
                            checkRandomPass = false;
                            System.out.println("Error : Detect Same Card Number , Try To Random New Card Number.");

                            brCheck.reset();
                            break;
                        } else {
                            checkRandomPass = true;
                        }
                    }

                } while (checkRandomPass == false);

            }

        }
        System.out.println("Done Checking.");
        this.serial = randomStringNum;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv() {
        this.ccv = RandomString.getAlphaNumericString(3);
    }

    public void setCcvLowerCaseOnly() {
        this.ccv = RandomStringNotCapitalize.getAlphaNumericString(3);
    }

    public void setCcvUpperCaseOnly() {
        this.ccv = RandomStringCapitalize.getAlphaNumericString(3);
    }

    public void setCcvNumberOnly() {
        this.ccv = RandomStringNumber.getAlphaNumericString(3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getMoneyBoundary() {
        return moneyBoundary;
    }

    public void setMoneyBoundary(String moneyBoundary) {
        this.moneyBoundary = moneyBoundary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static void readAndPrintAll() {
        List<CreditCard> cards = CSVReaderInJava.readCardsFromCSV(Utils.fileLocation);
        // let's print all the person read from CSV file
        for (CreditCard c : cards) {
            System.out.println(c);
        }
    }

    public static CreditCard readSpecific(int row) {
        List<CreditCard> cards = CSVReaderInJava.readCardsFromCSV(Utils.fileLocation);
        int index = row - 1;
        return cards.get(index);
    }

    public static List<CreditCard> searchSpecificFor(String search) {
        List<CreditCard> cards = CSVReaderInJava.readCardsFromCSV(Utils.fileLocation);
        List<CreditCard> foundCard = new ArrayList<CreditCard>();

        for (CreditCard c : cards) {
            if (c.getName() != null && c.getName().contains(search)) {
                foundCard.add(c);
            } else if (c.getSurname() != null && c.getSurname().contains(search)) {
                foundCard.add(c);
            } else if (c.getSerial() != null && c.getSerial().contains(search)) {
                foundCard.add(c);
            } else if (c.getExpiryDate() != null && c.getExpiryDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(search)) {
                foundCard.add(c);
            } else if (c.getIssueDate() != null && c.getIssueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(search)) {
                foundCard.add(c);
            } else if (c.getState() != null && c.getState().contains(search)) {
                foundCard.add(c);
            } else if (c.getTier() != null && c.getTier().contains(search)) {
                foundCard.add(c);
            } else if (c.getType() != null && c.getType().contains(search)) {
                foundCard.add(c);
            }
        }
        return foundCard;
    }

    public void addCardButtonClick() throws IOException {

        String bufferName = null;
        String bufferSurname = null;
        String bufferType = null;
        String bufferTier = null;
        String bufferMoneyBoundary = null;

    }

    public static void confirmCreateCard(String cardStatusString, String cardTypeString, boolean lowercaseAlp, boolean capitalizeAlp, String moneyBoundString, String cardTierString, LocalDate cardCreateDateFinal, String FName, String SName) throws IOException {

        String path = Utils.fileLocation;

        File file = new File(path);
        FileWriter writer;

        LocalDate start = cardCreateDateFinal; // DATE CARD CREATED
        LocalDate expiry = cardCreateDateFinal.plusYears(6); // DATE CARD EXPIRE

        CreditCard bufferCard = new CreditCard();
        bufferCard.setSerial();
        if (lowercaseAlp == true && capitalizeAlp == true) {
            bufferCard.setCcv();
        } else if (capitalizeAlp == true && lowercaseAlp == false) {
            bufferCard.setCcvUpperCaseOnly();
        } else if (capitalizeAlp == false && lowercaseAlp == true) {
            bufferCard.setCcvLowerCaseOnly();
        } else if (capitalizeAlp == false && lowercaseAlp == false) {
            bufferCard.setCcvNumberOnly();
        }

        bufferCard.setName(FName);
        bufferCard.setSurname(SName);
        bufferCard.setIssueDate(start);
        bufferCard.setExpiryDate(expiry);
        bufferCard.setType(cardTypeString);
        bufferCard.setTier(cardTierString);
        bufferCard.setMoneyBoundary(moneyBoundString);
        bufferCard.setState(cardStatusString);

        try {

            writer = new FileWriter(file, true);
            writer.write(bufferCard.getSerial() + "#" + bufferCard.getCcv() + "#" + bufferCard.getName() + "#"
                    + bufferCard.getSurname() + "#" + bufferCard.getIssueDate()
                    + "#" + bufferCard.getExpiryDate() + "#" + bufferCard.getType() + "#"
                    + bufferCard.getTier() + "#" + bufferCard.getMoneyBoundary() + "#" + bufferCard.getState() + "\r\n");
            writer.close();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = br.readLine()) != null) {

                    String[] arr = line.split("#");
                    System.out.print(arr[0]);
                    System.out.print(" - ");
                    System.out.print(arr[1]);
                    System.out.print(" - ");
                    System.out.print(arr[2]);
                    System.out.print(" - ");
                    System.out.print(arr[3]);
                    System.out.print(" - ");
                    System.out.print(arr[4]);
                    System.out.print(" - ");
                    System.out.print(arr[5]);
                    System.out.print(" - ");
                    System.out.print(arr[6]);
                    System.out.print(" - ");
                    System.out.print(arr[7]);
                    System.out.print(" - ");
                    System.out.print(arr[8]);
                    System.out.print(" - ");
                    System.out.print(arr[9]);
                    System.out.println("\n");

                }
                br.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CreditCard { " + "Serial= " + serial + ", CCV= " + ccv + ", Name= " + name + ", Surname= " + surname + ", IssueDate= " + issueDate + ", ExpiryDate= " + expiryDate + ", Type= " + type + ", Tier= " + tier + ", MoneyBoundary= " + moneyBoundary + ", State= " + state + " }";
    }

    public static void main(String[] args) throws IOException {

        readAndPrintAll();

    }
}
