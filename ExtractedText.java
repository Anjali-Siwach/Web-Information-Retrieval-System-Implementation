package com.stephen.crawler;

public class ExtractedText {
	private String text;
    private String url;

    public ExtractedText(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
