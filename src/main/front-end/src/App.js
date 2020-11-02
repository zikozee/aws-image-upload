import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

const UserProfiles = () =>{

    const fetchUserProfiles = () => {
        axios.get("http://localhost:8089/api/v1/user-profile")
            .then(response => {
                console.log(response);
            })
    }

    useEffect(() => { //same as componentDidMount in class-based components
        fetchUserProfiles();
    }, []);

    return <h1>Hello</h1>;
}

function App() {
  return (
    <div className="App">
        <UserProfiles />
    </div>
  );
}

export default App;
