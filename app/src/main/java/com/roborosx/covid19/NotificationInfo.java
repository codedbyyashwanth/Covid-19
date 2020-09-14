package com.roborosx.covid19;

public class NotificationInfo {
    public String title,link,date;

    public NotificationInfo(String title, String link, String date) {
        this.title = title;
        this.link = link;
        this.date = date;
    }

    public NotificationInfo() {
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }
}
