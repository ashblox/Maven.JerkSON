package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.*;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        String groceryList = "";
        ItemParser itemParser = new ItemParser();
        List<Item> items = itemParser.parseItemList(originalFileText);
        Map<String, List<Double>> frequencies = mapFrequencies(items);
        for (Map.Entry<String, List<Double>> entry : frequencies.entrySet()) {
           groceryList += formatItemBlock(entry.getKey(), entry.getValue());
           if (useSinglePriceMethod(entry.getValue())) {
               groceryList += formatWithSinglePrice(entry.getValue());
           } else {
               groceryList += formatWithMultiplePrices(entry.getValue());
           }
           groceryList += '\n';
        }
        int exceptionCount = itemParser.getExceptionCount();
        groceryList += String.format("Errors      \t\t seen:%2d %s\n", exceptionCount, timeOrTimes(exceptionCount));
        return groceryList;
    }

    public String capitalizeString(String str) {
           return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String formatItemBlock(String itemName, List<Double> frequencies) {
        String formatted = "";
        formatted += String.format("name:%8s \t\t seen:%2d %s", capitalizeString(itemName),
                frequencies.size(), timeOrTimes(frequencies.size())) + '\n' + "============= \t\t =============\n";
        return formatted;
    }

    public boolean useSinglePriceMethod(List<Double> prices) {
        Set<Double> uniquePrices = new HashSet<>();
        for (Double price : prices) {
            uniquePrices.add(price);
        }
        if (uniquePrices.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String formatWithSinglePrice(List<Double> prices) {
        return String.format("Price:%7.2f \t\t seen:%2d %s", prices.get(0), prices.size(), timeOrTimes(prices.size()))
                + '\n' + "------------- \t\t -------------\n";
    }

    public String formatWithMultiplePrices(List<Double> prices) {
        String formatted = "";
        int counter = 0;
        Map<Double, Integer> priceFreq = mapPriceFrequencies(prices);
        for (Map.Entry<Double, Integer> entry : priceFreq.entrySet()) {
            counter++;
            formatted += String.format("Price:%7.2f \t\t seen:%2d %s\n", entry.getKey(), entry.getValue(), timeOrTimes(entry.getValue()));
            if (counter != priceFreq.entrySet().size()) {
                formatted += "------------- \t\t -------------\n";
            }
        }
        return formatted;
    }

    public Map<Double, Integer> mapPriceFrequencies(List<Double> prices) {
        Map<Double, Integer> priceFreq = new LinkedHashMap<>();
        for (Double price : prices) {
            if (priceFreq.containsKey(price)) {
                Integer freq = priceFreq.get(price);
                priceFreq.put(price, freq + 1);
            } else {
                priceFreq.put(price, 1);
            }
        }
        return priceFreq;
    }

    public String timeOrTimes(int i) {
        if (i == 1) {
            return "time";
        } else {
            return "times";
        }
    }

    public static void main(String[] args) {
        GroceryReporter gp = new GroceryReporter("");
        List<Double> test = new ArrayList<>();
        test.add(0.0);
        test.add(0.2);
        test.add(0.2);
        System.out.println(gp.formatItemBlock("hello", test));
        System.out.println(gp.formatWithMultiplePrices(test));
    }

    public Map<String, List<Double>> mapFrequencies(List<Item> items) {
        Map<String, List<Double>> frequencies = new LinkedHashMap<>();
        for (Item item : items) {
            String itemName = item.getName();
            if (frequencies.containsKey(itemName)) {
                List<Double> prices = frequencies.get(itemName);
                prices.add(item.getPrice());
                frequencies.put(itemName, prices);
            } else {
                List<Double> prices = new ArrayList<>();
                prices.add(item.getPrice());
                frequencies.put(itemName, prices);
            }
        }
        return frequencies;
    }

}
