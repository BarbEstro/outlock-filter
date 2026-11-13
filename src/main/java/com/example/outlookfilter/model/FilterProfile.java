package com.example.outlookfilter.model;

import java.util.ArrayList;
import java.util.List;

public class FilterProfile {
    private String nameContact;
    private String surnameContact;
    private String nameCompany;
    private List<String> nameAttachmentUsed = new ArrayList<>();
    private List<String> nameAttachmentChoose = new ArrayList<>();

    public FilterProfile() {
    }

    public FilterProfile(String nameContact, String surnameContact, String nameCompany){
        this.nameContact = nameContact;
        this.surnameContact = surnameContact;
        this.nameCompany = nameCompany;
    }

    public String getNameContact() {
        return nameContact;
    }

    public String getSurnameContact() {
        return surnameContact;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public List<String> getNameAttachmentUsed() {
        return nameAttachmentUsed;
    }

    public List<String> getNameAttachmentChoose() {
        return nameAttachmentChoose;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public void setSurnameContact(String surnameContact) {
        this.surnameContact = surnameContact;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setNameAttachmentUsed(List<String> nameAttachmentUsed) {
        this.nameAttachmentUsed = nameAttachmentUsed;
    }

    public void setNameAttachmentChoose(List<String> nameAttachmentChoose) {
        this.nameAttachmentChoose = nameAttachmentChoose;
    }
}
