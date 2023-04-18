package com.cradlevendor.chat.model;

public class MyUser {
    public String name;
    public String emailMobile;


    public String fcmKey;
    public String profilePic;
    ConversationLocation conversationLocation;

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public ConversationLocation getConversationLocation() {
        return conversationLocation;
    }

    public void setConversationLocation(ConversationLocation conversationLocation) {
        this.conversationLocation = conversationLocation;
    }

    public String getEmailMobile() {
        return emailMobile;
    }

    public void setEmailMobile(String emailMobile) {
        this.emailMobile = emailMobile;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


}