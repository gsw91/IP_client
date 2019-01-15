package com.gui.editor;

import java.util.Comparator;

public class TableComparator implements Comparator<String> {

    public final static String PERCENTAGES = "percentages";
    public final static String PRICES = "prices";
    public final static String AMOUNTS = "amounts";

    private final String column;

    public TableComparator(String column) {
        this.column = column;
    }

    @Override
    public int compare(String field1, String field2) {
        StringBuilder field1Builder;
        StringBuilder field2Builder;
        switch (column) {
            case PERCENTAGES:
                if(field1.contains("-") && field2.contains("-")) {
                    if(field1.length()==7)
                        field1 = "-0" + field1.substring(1);
                    if(field2.length()==7)
                        field2 = "-0" + field2.substring(1);
                    return field2.compareToIgnoreCase(field1);
                }
                if(!field1.contains("-") && !field2.contains("-")) {
                    if(field1.length()==6)
                        field1 = "0" + field1;
                    if(field2.length()==6)
                        field2 = "0" + field2;
                    return field1.compareToIgnoreCase(field2);
                }
                return field1.compareToIgnoreCase(field2);
            case PRICES:
                field1Builder = new StringBuilder(field1);
                field2Builder = new StringBuilder(field2);
                while (field1Builder.length()<12) {
                    field1Builder.insert(0, "0");
                }
                while (field2Builder.length()<12) {
                    field2Builder.insert(0, "0");
                }
                field1 = field1Builder.toString();
                field2 = field2Builder.toString();
                return field1.compareToIgnoreCase(field2);
            case AMOUNTS:
                field1Builder = new StringBuilder(field1);
                field2Builder = new StringBuilder(field2);
                if(field1.contains("-") && field2.contains("-")) {
                    while (field1Builder.length() <= 18) {
                        field1Builder.insert(1, "0");
                    }
                    while (field2Builder.length() <= 18) {
                        field2Builder.insert(1, "0");
                    }
                    field1 = field1Builder.toString();
                    field2 = field2Builder.toString();
                    return field2.compareToIgnoreCase(field1);
                }
                if(!field1.contains("-") && !field2.contains("-")) {
                    while (field1Builder.length() <= 17) {
                        field1Builder.insert(0, "0");
                    }
                    while (field2Builder.length() <= 17) {
                        field2Builder.insert(0, "0");
                    }
                    field1 = field1Builder.toString();
                    field2 = field2Builder.toString();
                    return field1.compareToIgnoreCase(field2);
                }
        }
        return field1.compareToIgnoreCase(field2);
    }
}
