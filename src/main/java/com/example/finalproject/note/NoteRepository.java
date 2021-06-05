package com.example.finalproject.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByUserId(Long userId);

    Note findNoteById(Long id);

    Optional<Note>findByIdAndUserId(Long id,Long userId);

    void deleteByIdAndUserId(String id, String userId);

    @Transactional
    @Modifying
    @Query("update Note n set n.note = ?2, n.modifyAt = ?3 where n.id = ?1")
    int updateByIdNote(Long id, String noteUpdate, LocalDateTime modifyUpdate);

    List<Note> findByNoteContainingIgnoreCaseOrTitleContainingIgnoreCase(String note, String title);

    @Query("select id from Note where createAt > ?1 ")
    List<Long> findNotesWithinHour(LocalDateTime localDateTimeSearch);



}
