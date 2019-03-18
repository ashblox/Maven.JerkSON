package io.zipcoder.itemparser;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import io.zipcoder.ItemParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParseSingleItemTest {
    @Test
    public void test1() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";
        Item expected = new Item("milk", 3.23, "food", "1/25/2016");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void test2() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe:eggS;price:1.25;type:Food;expiration:1/25/2016##";
        Item expected = new Item("eggs", 1.25, "food", "1/25/2016");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }


    @Test
    public void test3() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe:teleVision;price:323.5;type:electRoniCs;expiration:3/25/2019##";
        Item expected = new Item("television", 323.5, "electronics", "3/25/2019");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }


    @Test
    public void test4() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe@teleVision;price@333.5;type@electRoniCs;expiration@3/25/2019##";
        Item expected = new Item("television", 333.5, "electronics", "3/25/2019");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }


    @Test
    public void test5() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe^teleVision;price^423.5;type^electRoniCs;expiration^3/25/2019##";
        Item expected = new Item("television", 423.5, "electronics", "3/25/2019");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }



    @Test
    public void test6() throws ItemParseException {
        // given
        ItemParser itemParser = new ItemParser();
        String valueToParse = "naMe^teleVision;price@325.5;type%electRoniCs;expiration%3/25/2019##";
        Item expected = new Item("television", 325.5, "electronics", "3/25/2019");

        // when
        Item actual = itemParser.parseSingleItem(valueToParse);

        // then
        assertEquals(expected, actual);
    }

}