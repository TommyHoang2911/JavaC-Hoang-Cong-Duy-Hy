package com.example.finalproject.controller;

import com.example.finalproject.appuser.AppUser;
import com.example.finalproject.note.*;
import com.example.finalproject.search.SearchNoteRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @RequestMapping("/notes")
    public String getAllNotes(@AuthenticationPrincipal AppUser currentUser, Model model) {

        List<Note> notesList;
        if (model.getAttribute("notes") == null){
            notesList = noteService.getAllNotesByUserId(currentUser.getId());
            model.addAttribute("notes", notesList);
        }
        model.addAttribute("createNote", new Note());

        NoteRequest newNote = new NoteRequest();
        SearchNoteRequest searchNoteRequest = new SearchNoteRequest();
        model.addAttribute("newNote", newNote);
        model.addAttribute("search", searchNoteRequest);

        return "notes";
    }

    @RequestMapping(value = {"search","notes/search"}, method = RequestMethod.POST)
    public String getSearchResult(@AuthenticationPrincipal AppUser user,@Valid SearchNoteRequest search,Model model) {
        List<Note> noteSearchList = noteService.getAllNotesByUserIdSearchByTitleOrNote(user.getId(),search.getTitleNote());
        if(noteSearchList.isEmpty()){
            model.addAttribute("notfound","Not found result");
            return  "forward:/notes";
        }
        model.addAttribute("notesSearch",noteSearchList);
        return "forward:/notes";
    }

    @RequestMapping(path = "/notes/search")
    public String search(@AuthenticationPrincipal AppUser user, @RequestParam String searchType, Model model) {
        List<Note> noteSearchList;
        switch (searchType) {
            case "month": {
                System.out.println("Search month success");
                noteSearchList = noteService.getAllNoteSortMonth(user.getId(), LocalDateTime.now());
                model.addAttribute("notesSearch", noteSearchList);
                break;
            }
            case "day": {
                System.out.println("Search day success");
                noteSearchList = noteService.getAllNoteSortDay(user.getId(), LocalDateTime.now());
                model.addAttribute("notesSearch", noteSearchList);
                break;
            }
            case "hour": {
                System.out.println("Search hour success");
                noteSearchList = noteService.getAllNoteSortHour(user.getId(), LocalDateTime.now());
                model.addAttribute("notesSearch", noteSearchList);
                break;
            }
            default: {
                model.addAttribute("notfound", "Not found result");
                return "forward:/notes";
            }
        }
        return "forward:/notes";
    }

    @RequestMapping(path = "/notes/details")
    public String openNote(@AuthenticationPrincipal AppUser user,@RequestParam Long noteId, Model model) {
        Note note = noteService.findNoteById(noteId);
        model.addAttribute("noteTitle", note.getTitle());
        model.addAttribute("noteDetail", note.getNote());
        return "forward:/notes";
    }

    @RequestMapping(value = {"createNote","/notes/createNote","search/createNote"}, method = RequestMethod.POST)
    public String createNote(@AuthenticationPrincipal AppUser user, NoteRequest note) {
        Note newNote = new Note(note.getTitle(), note.getNote(), user, LocalDateTime.now(), LocalDateTime.now());
        noteService.createNote(newNote);
        return "redirect:/notes";
    }

    @RequestMapping(value = "/notes/details", method = RequestMethod.POST)
    public ResponseEntity<?> updateNote(@AuthenticationPrincipal AppUser currentUser , @RequestBody NoteUpdateRequest noteUpdateRequest, Errors errors) {

        ResponseUpdate responseUpdate = new ResponseUpdate();

        if (errors.hasErrors()) {
            responseUpdate.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(responseUpdate);
        }
        responseUpdate.setMsg("Success");
        responseUpdate.setResult(noteUpdateRequest.getNoteDetail());
        noteService.updateNote(noteUpdateRequest.getId(),noteUpdateRequest.getNoteDetail(),LocalDateTime.now());
        return ResponseEntity.ok(responseUpdate);
    }
}
