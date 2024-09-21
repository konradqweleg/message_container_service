package com.example.messagecontainer.adapter.in.rest;


public class OutputMessageWS {

    private String from;
    private String text;
    private String to;

    public OutputMessageWS(final String from, final String text, final String to) {

        this.from = from;
        this.text = text;
        this.to = to;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}