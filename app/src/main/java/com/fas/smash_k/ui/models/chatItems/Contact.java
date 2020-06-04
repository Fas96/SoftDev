package com.fas.smash_k.ui.models.chatItems;

public class Contact {

    /* renamed from: id */
    private int f7id;
    private String name;

    public Contact(String name2, int id) {
        this.name = name2;
        this.f7id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getId() {
        return this.f7id;
    }

    public void setId(int id) {
        this.f7id = id;
    }
}
