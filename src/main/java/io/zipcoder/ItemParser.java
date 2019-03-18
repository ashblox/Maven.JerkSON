package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    public List<Item> parseItemList(String valueToParse) {
        String strPattern = "(\\w+)[;*:,@^%](\\w+)[;*:,@^%](\\w+)[;*:,@^%](\\d.{3,4})[;*:,@^%](\\w+)[;*:,@^%](\\w+)" +
                "[;*:,@^%](\\w+)[;*:,@^%](\\d{1,2}/\\d{1,2}/\\d{4})(##)";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(valueToParse);
        List<Item> items = new ArrayList<>();
        for (int i = 0; matcher.find(); i++) {
            String name = matcher.group(2).toLowerCase();
            Double price = Double.valueOf(matcher.group(4));
            String type = matcher.group(6).toLowerCase();
            String expDate = matcher.group(8);

            items.add(new Item(name, price, type, expDate));
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
        List<Item> items = parseItemList(singleItem);
        return items.get(0);
    }
}
