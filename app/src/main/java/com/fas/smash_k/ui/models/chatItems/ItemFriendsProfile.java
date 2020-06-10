package com.fas.smash_k.ui.models.chatItems;

public class ItemFriendsProfile {
    private String profileName;
    private  String stateOnApp;
    private String stateWithUser;
    private String addFriends;
    private String profImageUrl;

    public ItemFriendsProfile(String profileName, String stateOnApp, String stateWithUser, String addFriends,String profImageUrl) {
        this.profileName = profileName;
        this.stateOnApp = stateOnApp;
        this.stateWithUser = stateWithUser;
        this.addFriends = addFriends;
        this.profImageUrl=profImageUrl;
    }

    public String getProfImageUrl() {
        return profImageUrl;
    }

    public void setProfImageUrl(String profImageUrl) {
        this.profImageUrl = profImageUrl;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getStateOnApp() {
        return stateOnApp;
    }

    public void setStateOnApp(String stateOnApp) {
        this.stateOnApp = stateOnApp;
    }

    public String getStateWithUser() {
        return stateWithUser;
    }

    public void setStateWithUser(String stateWithUser) {
        this.stateWithUser = stateWithUser;
    }

    public String getAddFriends() {
        return addFriends;
    }

    public void setAddFriends(String addFriends) {
        this.addFriends = addFriends;
    }
}
