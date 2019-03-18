package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private int exceptionCount;

    public ItemParser() {
        this.exceptionCount = 0;
    }

    public int getExceptionCount() {
        return exceptionCount;
    }

    public List<Item> parseItemList(String valueToParse) {
        String[] strArray = valueToParse.split("##");
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < strArray.length; i++) {
            try {
                items.add(parseSingleItem(strArray[i]));
            } catch (Exception e) {
                exceptionCount ++;
                System.out.println("Failed to add item to list (invalid).");
            }
        }
        return items;
    }

    public static void main(String[] args) {
        ItemParser itemParser = new ItemParser();
        List<Item> items = itemParser.parseItemList("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##");
        for (Item i : items) {
            System.out.println(i.toString());
        }
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        String strPattern = "(\\w+)[;*:,@^%!](\\w+)[;*:,@^%!](\\w+)[;*:,@^%!](\\d.{3,4})[;*:,@^%!](\\w+)[;*:,@^%!](\\w+)" +
                "[;*:,@^%!](\\w+)[;*:,@^%!](\\d{1,2}/\\d{1,2}/\\d{4})";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(singleItem);
        try {
            matcher.matches();
            String name = matcher.group(2).toLowerCase();
            if (name.contains("0")) {
                name = name.replace('0', 'o');
            }
            Double price = Double.valueOf(matcher.group(4));
            String type = matcher.group(6).toLowerCase();
            String expDate = matcher.group(8);
            return new Item(name, price, type, expDate);
        } catch (Exception e) {
            throw new ItemParseException();
        }
    }

    public String convertZerosToOs(String str) {
        return str.replace('0', 'o');
    }
}
