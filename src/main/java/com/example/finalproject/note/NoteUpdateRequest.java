package com.example.finalproject.note;

public class NoteUpdateRequest {
    Long Id;
    String noteDetail;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }
}
