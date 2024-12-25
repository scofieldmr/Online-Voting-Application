package com.project.service;

import com.project.Dto.OptionVoteDto;
import com.project.Dto.OptionVoteSaveDto;
import com.project.Dto.PollDto;
import com.project.Dto.PollSaveDto;
import com.project.entity.OptionVote;
import com.project.entity.Poll;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollService {

    @Autowired
    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(PollSaveDto pollSaveDto){

        List<OptionVote> optionsVoteList = new ArrayList<>();

        for(OptionVoteSaveDto optionVoteSaveDto:pollSaveDto.getOptions()){
            OptionVote optionsVote = new OptionVote();

            optionsVote.setVoteOption(optionVoteSaveDto.getVoteOption());
            optionsVote.setVoteCount(optionVoteSaveDto.getVoteCount());

            optionsVoteList.add(optionsVote);
        }

        Poll poll = new Poll();

        poll.setQuestions(pollSaveDto.getQuestions());
        poll.setOptions(optionsVoteList);

        pollRepository.save(poll);

        return poll;
    }

    public List<PollDto> findAllPolls(){

        List<Poll> pollList = pollRepository.findAll();

        List<PollDto> pollDtoList = new ArrayList<>();

        for(Poll poll:pollList){
            PollDto pollDto = new PollDto();

            pollDto.setId(poll.getId());
            pollDto.setQuestions(poll.getQuestions());

            List<OptionVote> optionVoteList= poll.getOptions();

            List<OptionVoteDto> optionVoteDtoList = new ArrayList<>();

            for(OptionVote optionVote: optionVoteList){
                OptionVoteDto optionVoteDto = new OptionVoteDto();

                optionVoteDto.setVoteOption(optionVote.getVoteOption());
                optionVoteDto.setVoteCount(optionVote.getVoteCount());

                optionVoteDtoList.add(optionVoteDto);
            }

            pollDto.setOptions(optionVoteDtoList);
            
            pollDtoList.add(pollDto);
        }

        return pollDtoList;
    }

    public PollDto getPollsByPollId(Long id){
        Poll poll = pollRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Poll Id Not Found"));

        PollDto pollDto = new PollDto();
        pollDto.setId(poll.getId());
        pollDto.setQuestions(poll.getQuestions());
        List<OptionVote> optionVoteList = poll.getOptions();

        List<OptionVoteDto> optionVoteDtoList = new ArrayList<>();
        for(OptionVote optionVote:optionVoteList){
            OptionVoteDto optionVoteDto = new OptionVoteDto();

            optionVoteDto.setVoteOption(optionVote.getVoteOption());
            optionVoteDto.setVoteCount(optionVote.getVoteCount());

            optionVoteDtoList.add(optionVoteDto);
        }

        pollDto.setOptions(optionVoteDtoList);

        return pollDto;
    }

    public PollDto vote(Long pollId,int OptionIndex){
        //To get the poll
        Poll poll = pollRepository.findById(pollId).orElseThrow(()->new ResourceNotFoundException("Poll Id not found"));

        //To get the pollOptions
        List<OptionVote> options = poll.getOptions();

        //Invalid Opttions
        if(OptionIndex<0 || OptionIndex>options.size()){
            throw new IllegalArgumentException("Invalid Option Index");
        }

        //To find the options
        OptionVote optionVote = options.get(OptionIndex);

        //Increment the options
        optionVote.setVoteCount(optionVote.getVoteCount()+1);

        //Save the changes
        pollRepository.save(poll);

        List<OptionVoteDto> optionVoteDtoList = new ArrayList<>();

        for(OptionVote optionVote1: options){
            OptionVoteDto optionVoteDto = new OptionVoteDto();

            optionVoteDto.setVoteOption(optionVote1.getVoteOption());
            optionVoteDto.setVoteCount(optionVote1.getVoteCount());

            optionVoteDtoList.add(optionVoteDto);
        }

        PollDto pollDto = new PollDto();
        pollDto.setId(poll.getId());
        pollDto.setQuestions(poll.getQuestions());
        pollDto.setOptions(optionVoteDtoList);

        return pollDto;
    }
}
