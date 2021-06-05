package com.example.finalproject.note;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> getAllNotesByUserId(Long userId){
        return noteRepository.findByUserId(userId);
    }

    public Note findNoteById(Long id){ return noteRepository.findNoteById(id); };

    public void createNote(Note note){
        noteRepository.save(note);
    }

    public int updateNote(Long id, String noteUpdate,LocalDateTime modifyAtUpdate) {

        return noteRepository.updateByIdNote(id,noteUpdate,modifyAtUpdate);
    }

    public List<Note> getAllNotesByUserIdSearchByTitleOrNote(Long userId, String searchInput) {
        List<Note> searchResults = noteRepository.findByNoteContainingIgnoreCaseOrTitleContainingIgnoreCase(searchInput, searchInput);
        List<Note> listResultsByUserId = searchResults.stream().filter(note -> userId.equals(note.getUser().getId())).collect(Collectors.toList());
        return listResultsByUserId;
    }

    public List<Note> getAllNoteSortHour(Long userId,@NotNull LocalDateTime localDateTime){
        List<Long> idResults = noteRepository.findNotesWithinHour(localDateTime.minusHours(1));
        List<Note> searchResults = new ArrayList<>();
        for (int i = 0; i < idResults.size(); i++) {
            searchResults.add(findNoteById(idResults.get(i)));
        }
        return searchResults.stream().filter(note -> userId.equals(note.getUser().getId())).collect(Collectors.toList());
    }

    public List<Note> getAllNoteSortDay(Long userId,@NotNull LocalDateTime localDateTime){
        List<Long> idResults = noteRepository.findNotesWithinHour(localDateTime.minusDays(1));
        List<Note> searchResults = new ArrayList<>();
        for (int i = 0; i < idResults.size(); i++) {
            searchResults.add(findNoteById(idResults.get(i)));
        }
        return searchResults.stream().filter(note -> userId.equals(note.getUser().getId())).collect(Collectors.toList());
    }

    public List<Note> getAllNoteSortMonth(Long userId,@NotNull LocalDateTime localDateTime){
        List<Long> idResults = noteRepository.findNotesWithinHour(localDateTime.minusMonths(1));
        List<Note> searchResults = new ArrayList<>();
        for (int i = 0; i < idResults.size(); i++) {
            searchResults.add(findNoteById(idResults.get(i)));
        }
        return searchResults.stream().filter(note -> userId.equals(note.getUser().getId())).collect(Collectors.toList());
    }
}
