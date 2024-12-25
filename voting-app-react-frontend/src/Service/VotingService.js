import axios from "axios";


export const getAllPolls = () => axios.get(`http://localhost:5000/api/v1/polls`);


export const votingOptions = (poll_Id,option_Index) => 
                 axios.post(`http://localhost:5000/api/v1/polls/vote/${poll_Id}/${option_Index}`);

export const createPoll = (poll) => axios.post('http://localhost:5000/api/v1/polls',poll);