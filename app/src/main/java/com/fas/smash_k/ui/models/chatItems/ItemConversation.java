package com.fas.smash_k.ui.models.chatItems;

public class ItemConversation {
    String conttactName;
    String date;
    String imageURL;
    String lastMessage;
    int unreadCount;

    public ItemConversation(String imageURL2, String conttactName2, String lastMessage2, String date2, int unreadCount2) {
        this.imageURL = imageURL2;
        this.conttactName = conttactName2;
        this.lastMessage = lastMessage2;
        this.date = date2;
        this.unreadCount = unreadCount2;
    }

    public void setUnreadCount(int unreadCount2) {
        this.unreadCount = unreadCount2;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getConttactName() {
        return this.conttactName;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public String getDate() {
        return this.date;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }
}
