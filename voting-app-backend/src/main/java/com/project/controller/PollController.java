package com.project.controller;

import com.project.Dto.PollDto;
import com.project.Dto.PollSaveDto;
import com.project.entity.Poll;
import com.project.exception.ResourceNotFoundException;
import com.project.service.PollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<?> createPolls(@RequestBody PollSaveDto pollSaveDto){
        Poll poll = pollService.createPoll(pollSaveDto);

        return ResponseEntity.ok().body(poll);
    }

    @GetMapping
    public ResponseEntity<?> getAllPolls(){
        List<PollDto> pollDtoList = pollService.findAllPolls();

        return ResponseEntity.ok().body(pollDtoList);
    }

    @GetMapping("/{poll_id}")
    public ResponseEntity<?> getPollsById(@PathVariable("poll_id") Long id){
        try {
            PollDto pollDto = pollService.getPollsByPollId(id);
            return ResponseEntity.ok().body(pollDto);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/vote/{poll_Id}/{option_Index}")
    public ResponseEntity<?> votingTheOptions(@PathVariable("poll_Id") Long pollId,@PathVariable("option_Index") int optionIndex){
        try{
            PollDto pollDto = pollService.vote(pollId,optionIndex);

            return ResponseEntity.ok().body(pollDto);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
