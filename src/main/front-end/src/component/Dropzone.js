import axios from "axios";
import {useDropzone} from "react-dropzone";
import {useCallback} from "react";

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

export default Dropzone;