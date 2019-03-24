package com.gui.editor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EditorTest {

    private Editor editor;

    @Before
    public void setVariables() {
        editor = new Editor();
    }

    @Test
    public void testReplaceDot() {
        //given
        String rateOne = "41.22";
        String rateTwo = "1.1";
        String rateThree = "12.9";
        String expectedOne = "41,22 %";
        String expectedTwo = "1,10 %";
        String expectedThree = "12,90 %";
        //when
        String editedOne = editor.setRate(rateOne);
        String editedTwo = editor.setRate(rateTwo);
        String editedThree = editor.setRate(rateThree);
        //then
        Assert.assertEquals(expectedOne, editedOne);
        Assert.assertEquals(expectedTwo, editedTwo);
        Assert.assertEquals(expectedThree, editedThree);
    }

    @Test
    public void testSetSpacesWithCurrency() {
        //given
        String amountOne = "2423538912";
        String amountTwo = "912";
        String amountThree = "11.98";
        String amountFour = "1237892365982314791.11";
        String expectedOne = "2 423 538 912,00 zł";
        String expectedTwo = "912,00 zł";
        String expectedThree = "11,98 zł";
        String expectedFour = "1 237 892 365 982 314 791,11 zł";
        //when
        String editedAmountOne = editor.setSpacesWithCurrency(amountOne);
        String editedAmountTwo = editor.setSpacesWithCurrency(amountTwo);
        String editedAmountThree = editor.setSpacesWithCurrency(amountThree);
        String editedAmountFour = editor.setSpacesWithCurrency(amountFour);
        //then
        Assert.assertEquals(expectedOne, editedAmountOne);
        Assert.assertEquals(expectedTwo, editedAmountTwo);
        Assert.assertEquals(expectedThree, editedAmountThree);
        Assert.assertEquals(expectedFour, editedAmountFour);
    }

    @Test
    public void testSetSpacesWhenNoDecimal() {
        //given
        String amountOne = "2423538912";
        String amountTwo = "912";
        String amountThree = "1111";
        String expectedOne = "2 423 538 912,00 zł";
        String expectedTwo = "912,00 zł";
        String expectedThree = "1 111,00 zł";
        //when
        String editedAmountOne = editor.setSpacesWithCurrency(amountOne);
        String editedAmountTwo = editor.setSpacesWithCurrency(amountTwo);
        String editedAmountThree = editor.setSpacesWithCurrency(amountThree);
        //then
        Assert.assertEquals(expectedOne, editedAmountOne);
        Assert.assertEquals(expectedTwo, editedAmountTwo);
        Assert.assertEquals(expectedThree, editedAmountThree);
    }

    @Test
    public void testSetSpacesWhenDecimal() {
        //given
        String amountOne = "2423538912.54";
        String amountTwo = "912.54";
        String amountThree = "1111.54";
        String expectedOne = "2 423 538 912,54 zł";
        String expectedTwo = "912,54 zł";
        String expectedThree = "1 111,54 zł";
        //when
        String editedAmountOne = editor.setSpacesWithCurrency(amountOne);
        String editedAmountTwo = editor.setSpacesWithCurrency(amountTwo);
        String editedAmountThree = editor.setSpacesWithCurrency(amountThree);
        //then
        Assert.assertEquals(expectedOne, editedAmountOne);
        Assert.assertEquals(expectedTwo, editedAmountTwo);
        Assert.assertEquals(expectedThree, editedAmountThree);
    }

}
