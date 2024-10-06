package org.example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class NetUtils {
    public List<Stock> getStocks() {
        String url = "https://raw.githubusercontent.com/productstar-team/javaTwo/master/resources/monthly_IBM.csv";
        try (BufferedInputStream is = new BufferedInputStream(new URL(url).openStream())) {
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";
            return ConvertCsvToStocks(result);
        } catch (IOException e) {
            System.out.println("Unable to get data from the API" + e.getMessage());
        }
        return Collections.emptyList();
    }

    private List<Stock> ConvertCsvToStocks(String inputData) {
        List<Stock> stocks = new ArrayList<>();
        String[] lines = inputData.split("\n");
        for (int i = 1; i < lines.length; i++) {
            stocks.add(convertLineToStock(lines[i]));
        }
        return stocks; 
    }

    private Stock convertLineToStock(String line) {
        String[] tokens = line.split(",");
        return new Stock(tokens[0], Float.parseFloat(tokens[4]));
    }
}
