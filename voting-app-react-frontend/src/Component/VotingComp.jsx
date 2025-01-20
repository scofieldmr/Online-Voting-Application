import React, { useEffect, useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { createPoll, getAllPolls,votingOptions } from '../Service/VotingService';

const VotingComp = () => {

    const [id, setId] = useState('');
    const [questions, setQuestions] = useState('');
    const [options, setOptions] = useState(['','']);
    const [voteOption, setVoteOption] = useState('');
    const [voteCount, setVoteCount] = useState('');

    const [polls, setPolls] = useState([]);


    function getAllPollsAndVotes() {
        getAllPolls().then((response) => {
            setPolls(response.data);
        })
            .catch((error) => {
                console.error(error);
            })
    }

    useEffect(() => {
        getAllPollsAndVotes();
    }, []);

    //TO vote in the poll Questions
    function vote(pollId,optionIndex){
        votingOptions(pollId,optionIndex).then((response)=> {
            console.log(response);

            getAllPollsAndVotes();
        })
        .catch((error) => {
            console.error(error);
        })
    }

    //TO Create new Poll

    function savePoll(e){
        e.preventDefault();

        // Create poll data with the correct structure for options
        //Since the options are separate array content and contains 
        const formattedOptions = options.map(option => ({
            voteOption: option,  // Each option is mapped to the voteOption field
            voteCount: 0         // Initialize voteCount as 0
        }));

        // Poll data with the correct structure
        const poll = {
            questions,
            options: formattedOptions
        };

        console.log(poll);
        
        //TO create polls using backend
        createPoll(poll).then((response) => {
            console.log(response);

            getAllPollsAndVotes();

            setQuestions('');
            setOptions(['', '', '', '']);
        })
        .catch((error) => {
            console.error(error);
        })
    }

    function handleOptionChange(e,index){
      const newOptions = [...options];
      newOptions[index] = e.target.value;

      setOptions(newOptions);
    }

     // Function to handle removal of an option
     function removeOption(index) {
        if(index>1){
           const newOptions = options.filter((_, i) => i !== index);
           setOptions(newOptions);
        }
    }


    // useEffect(()=>{
    //   setPolls([
    //     {
    //       id: 1,
    //       questions: "Favourite Sport?",
    //       options: [
    //           {
    //             voteOption: "Football",
    //             voteCount: 10
    //           },
    //           {
    //             voteOption: "Cricket",
    //             voteCount: 4
    //           }
    //         ]
    //       },
    //       {
    //         id : 2,
    //         questions: "Favourite Programming Language?",
    //         options: [
    //             {
    //               voteOption: "Java",
    //               voteCount: 5
    //             },
    //             {
    //               voteOption: "Python",
    //               voteCount: 3
    //             }
    //           ]
    //         }
    //     ])
    // },[]);

    return (
        <div>
            <div className='container' >
                <div className='card m-lg-0'style={{padding:'5px'}}>
                    <h2 className='text-center'> Create Poll </h2>

                    <div className='card-body'>
                        <form>
                            <div className='form-group mt-2'>
                                <input
                                    type='text'
                                    placeholder='Poll Questions'
                                    name='questions'
                                    value={questions}
                                    className={`form-control`}
                                    onChange={(e) => setQuestions(e.target.value)}
                                >
                                </input>
                            </div>

                            <div>

                            {
                            options.map((option, index) => (
                                <div className='form-group mt-3' key={index} style={{display:'flex'}}>
                                    <input
                                        type='text'
                                        placeholder={`Option ${index + 1}`}
                                        name={`option${index}`}
                                        value={option}
                                        className={`form-control`}
                                        onChange={(e) => handleOptionChange(e, index)} // Update individual option
                                    />
                                    <button
                                            type='button'
                                            className='btn btn-danger btn-sm'
                                            onClick={() => removeOption(index)}
                                      >  Remove </button>
                                </div>
                            ))}

                         </div>
                        

                            <span>
                                <button className='btn btn-primary'
                                    type='button'
                                    style={{ marginTop: '15px'}}
                                    onClick={() => setOptions([...options, ''])}
                                >Add Another Option</button>

                                <button className='btn btn-primary'
                                    style={{ marginTop: '15px',marginLeft:'5px' }}
                                    onClick={savePoll}
                                >Create Poll</button>
                            </span>
                        </form>
                    </div>
                </div>
            </div>
            <div className='mt-5'>
                <h2 className='text-center'> Available Polls</h2>

                <div className='container'>
                    <div>
                        {
                            polls.map(poll =>
                                <div className='card' key={poll.id} style={{ marginTop: '20px', padding: '30px' }}>
                                    <h4>{poll.id} . {poll.questions}</h4>
                                    {
                                        poll.options.map((option, index) =>
                                            <button key={index}
                                             className='btn btn-outline-primary' 
                                             style={{width:'15%',marginTop:'5px'}}
                                             onClick={()=>vote(poll.id,index)}
                                            >
                                             {option.voteOption} - {option.voteCount} Votes</button>
                                        )
                                    }
                                </div>
                            )
                        }
                    </div>
                </div>
            </div>

        </div>
    )
}

export default VotingComp