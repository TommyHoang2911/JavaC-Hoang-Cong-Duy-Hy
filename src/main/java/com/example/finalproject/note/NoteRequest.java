package com.example.finalproject.note;

import com.example.finalproject.appuser.AppUser;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NoteRequest {

    private String title;
    private String note;
    private AppUser user;
    private String createAt;
    private String modifyAt;

    public NoteRequest() {
    }

}
