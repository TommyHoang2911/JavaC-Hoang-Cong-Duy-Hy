package com.example.finalproject.note;

import com.example.finalproject.appuser.AppUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Note {

    @SequenceGenerator(
            name = "note_sequence",
            sequenceName = "note_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "note_sequence"
    )
    private Long id;

    private String title;

    private String note;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser user;

    private LocalDateTime createAt;

    private LocalDateTime modifyAt;

    public Note(String title,String note,AppUser user,LocalDateTime createAt,LocalDateTime modifyAt){
        this.title = title;
        this.note = note;
        this.user = user;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }
}
