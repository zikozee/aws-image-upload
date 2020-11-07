import React, {useEffect, useState} from 'react';
import axios from "axios";
import Dropzone from "../component/Dropzone";

const MyUserProfiles = () => {

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

export default MyUserProfiles;