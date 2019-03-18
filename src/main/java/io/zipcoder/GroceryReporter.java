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
        ItemParser itemParser = new ItemParser();
        List<Item> items = itemParser.parseItemList(originalFileText);
        Map<String, List<Double>> frequencies = mapFrequencies(items);
//        for (Map.Entry<String, List<Double>> entry : frequencies.entrySet()) {
//            System.out.print(entry.getKey() + " ");
//            System.out.println(entry.getValue());
//        }

        return "";
    }

    public void capitalizeItemNames(List<Item> items) {
        for (Item item : items) {
            String name = item.getName();
            String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
            name.setName(cap);
        }
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
