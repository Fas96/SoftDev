package com.fas.smash_k.ui.models.chatItems;

public class ItemMessages {
    public static int DELIVERED = 1;
    public static int SEEN = 0;
    public static int SENT = 2;
    private Contact contact;
    private String date;
    private String messages;
    private int status;

    public ItemMessages(String messages2, String date2, int status2, Contact contact2) {
        this.messages = messages2;
        this.date = date2;
        this.status = status2;
        this.contact = contact2;
    }

    public String getMessages() {
        return this.messages;
    }

    public void setMessages(String messages2) {
        this.messages = messages2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact2) {
        this.contact = contact2;
    }
}
