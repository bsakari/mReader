package com.alhusseiny.till_man;

public class Message {
     String code, from, amount, date;

    public Message(String code, String from, String amount, String date) {
        this.code = code;
        this.from = from;
        this.amount = amount;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public String getFrom() {
        return from;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
