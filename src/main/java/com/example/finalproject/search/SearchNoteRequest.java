package com.example.finalproject.search;

import javax.validation.constraints.NotBlank;

public class  SearchNoteRequest {

    @NotBlank(message = "Can't empty !")
    String titleNote;

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }
}
