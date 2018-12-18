package com.gui.editor;

import com.gui.config.Currency;

public final class Editor {

    public String setSpacesToQuantity(String quantity) {
        return setSpaces(quantity, false);
    }

    public String setSpacesWithCurrency(String amount) {
        return setSpaces(amount, true) + " " + Currency.CURRENCY;
    }

    public String setRate(String rate) {
        StringBuilder builder = new StringBuilder(replaceDot(rate));
        builder.reverse();
        if (builder.indexOf(",") == 2) {
            return replaceDot(rate) + " %";
        } else if (builder.indexOf(",") == 1) {
            builder.reverse();
            builder.append("0");
            builder.append(" ");
            builder.append("%");
        }
        return builder.toString();
    }

    private String setSpaces(String amount, boolean isItAmount) {
        if (amount.contains(".") && amount.length() > 6) {
            StringBuilder builder = new StringBuilder(replaceDot(amount));
            builder.reverse();
            builder.insert(6, " ");
            if (builder.length() > 9) {
                for (int i = 10; i < builder.length(); i += 4) {
                    builder.insert(i, " ");
                }
            }
            builder.reverse();
            return builder.toString();
        } else if (!amount.contains(".") && amount.length() > 3 && isItAmount) {
            StringBuilder builder = new StringBuilder(amount);
            builder.reverse();
            if (builder.length() > 3) {
                builder.insert(3, " ");
                if (builder.length() > 6) {
                    for (int i = 7; i < builder.length(); i += 4) {
                        builder.insert(i, " ");
                    }
                }
                builder.reverse();
                builder.append(",00");
            }
            return builder.toString();
        } else if (!amount.contains(".") && amount.length() <= 3 && isItAmount) {
            return amount + ",00";
        } else if (!amount.contains(".") && amount.length() > 3 && !isItAmount) {
            StringBuilder builder = new StringBuilder(amount);
            builder.reverse();
            if (builder.length() > 3) {
                builder.insert(3, " ");
                if (builder.length() > 6) {
                    for (int i = 7; i < builder.length(); i += 4) {
                        builder.insert(i, " ");
                    }
                }
            }
            return builder.reverse().toString();
        }
        return replaceDot(amount);
    }

    private String replaceDot(String amount) {
        return amount.replace(".", ",");
    }

    public String replaceComma(String price) {
        return price.replace(",", ".");
    }
}
