package seedu.duke;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Storage {

    private static final String FILE_PATH = "trips.json";

    public static ArrayList<Trip> listOfTrips = new ArrayList<>();
    private static Trip openTrip = null;
    private static Scanner scanner;
    private static Logger logger;

    private static final ArrayList<String> validCommands = new ArrayList<>(
            Arrays.asList("create", "edit", "view", "open", "list", "summary",
                    "delete", "expense", "quit", "help", "amount"));

    private static final HashMap<String, String[]> availableCurrency = new HashMap<>() {{
            put("USD", new String[] {"$", "%.02f"});
            put("SGD", new String[] {"$", "%.02f"});
            put("AUD", new String[] {"$", "%.02f"});
            put("CAD", new String[] {"$", "%.02f"});
            put("EUR", new String[] {"€", "%.02f"});
            put("GBP", new String[] {"£", "%.02f"});
            put("MYR", new String[] {"RM", "%.02f"});
            put("HKD", new String[] {"$", "%.02f"});
            put("THB", new String[] {"฿", "%.02f"});
            put("CNY", new String[] {"¥", "%.0f"});
            put("JPY", new String[] {"¥", "%.0f"});
            put("KRW", new String[] {"₩", "%.0f"});
            put("IDR", new String[] {"Rp", "%.0f"});
            put("INR", new String[] {"Rs", "%.0f"});
    }};

    public static HashMap<String, String[]> getAvailableCurrency() {
        return availableCurrency;
    }

    protected static void writeToFile() throws IOException {
        String jsonString = new Gson().toJson(listOfTrips);
        FileWriter fileWriter = new FileWriter(FILE_PATH);
        fileWriter.write(jsonString);
        fileWriter.close();
    }

    protected static void readFromFile() {
        File file = new File(FILE_PATH);
        try {
            Scanner scanner = new Scanner(file);
            String jsonString = scanner.nextLine();
            Type tripType = new TypeToken<ArrayList<Trip>>(){}.getType();
            listOfTrips = new Gson().fromJson(jsonString, tripType);
        } catch (JsonParseException e) {
            Ui.printJsonParseError();
        } catch (FileNotFoundException e) {
            Ui.printFileNotFoundError();
        }
    }


    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Storage.scanner = scanner;
    }

    public static ArrayList<String> getValidCommands() {
        return validCommands;
    }


    public static Trip getOpenTrip() {
        if (openTrip == null) {
            Ui.printNoOpenTripError();
            promptUserForValidTrip();
        }
        return openTrip;
    }

    private static void promptUserForValidTrip() {
        try {
            int tripIndex = Integer.parseInt(scanner.nextLine().strip()) - 1;
            setOpenTrip(listOfTrips.get(tripIndex));
        } catch (NumberFormatException e) {
            Ui.argNotNumber();
        }
    }

    /**
     * Checks if there is an open trip or not.
     *
     * @return true if there is an open trip
     */
    public static boolean checkOpenTrip() {
        return openTrip != null;
    }

    public static void setOpenTrip(Trip openTrip) {
        Storage.openTrip = openTrip;
    }

    public static void closeTrip() {
        Storage.openTrip = null;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        Storage.logger = logger;
    }


}
