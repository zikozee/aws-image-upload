import React, {useState, useEffect, useCallback} from 'react';
import {useDropzone} from 'react-dropzone'
import './App.css';
import axios from 'axios';

const UserProfiles = () => {

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
                {userProfile.userProfileId ? (
                    <img src={`http://localhost:8089/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
                    />
                    ) : null }

                {/*todo: profile image*/}
                <br/>
                <br/>
                <h1>{userProfile.username}</h1>
                <p>{userProfile.userProfileId}</p>
                <Dropzone {...userProfile}/> {/*same as userProfileId = userprofile.userProfileId */}
                <br/>
            </div>)
    })
}

function Dropzone({userProfileId}) {
    const onDrop = useCallback(acceptedFiles => {
        const file = acceptedFiles[0];

        console.log(file);

        const formData = new FormData();
        formData.append("file", file); // String file must be same used in request param

        axios.post(`http://localhost:8089/api/v1/user-profile/${userProfileId}/image/upload`,
            formData, {
                headers: {
                    "content-type": "multipart/form-data"
                }
            }
        ).then((response) => {
            console.log("file uploaded successfully")
        }).catch(err => {
                console.log(err);
            });
    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Drag 'n' drop profile image, or click to select profile image</p>
            }
        </div>
    )
}

function App() {
    return (
        <div className="App">
            <UserProfiles/>
        </div>
    );
}

export default App;
