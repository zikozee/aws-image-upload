import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

const UserProfiles = () =>{

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios.get("http://localhost:8089/api/v1/user-profile")
            .then(response => {
               console.log(response)
                setUserProfiles(response.data)
            })
    }

    useEffect(() => { //same as componentDidMount in class-based components
        fetchUserProfiles();
    }, []);

    return userProfiles.map((userProfile, index) => {
        return (
            <div key={index}>
                <h1>{userProfile.username}</h1>
                <p>{userProfile.userProfileId}</p>
            </div>)
    })
}

function App() {
  return (
    <div className="App">
        <UserProfiles />
    </div>
  );
}

export default App;
