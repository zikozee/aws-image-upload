package com.zikozee.awsimageupload.profile;

import com.zikozee.awsimageupload.bucket.BucketName;
import com.zikozee.awsimageupload.datastore.FakeUserProfileDataStore;
import com.zikozee.awsimageupload.filestore.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class UserProfileDataAccessService implements UserProfileService{

    private final FakeUserProfileDataStore fakeUserProfileDataStore;
    private final FileStore fileStore;


    @Override
    public List<UserProfile> getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfiles();
    }

    @Override
    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1. Check if Image is not empty
        if(file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");//throw custom exception
        //2. If file is an Image
        if(!Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image");
        }

        //3. The user exists in our database
        UserProfile userProfile = getUserProfiles()
                .stream()
                .filter(userProf -> userProf.getUserProfileId().equals(userProfileId))
                .findFirst().orElseThrow(() -> new IllegalStateException("User profile with id: " + userProfileId+ " not found"));

        //4. Grab some metadata from file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        //creating a folder per user
        String path = BucketName.PROFILE_IMAGE.getBucketName() + "/" + userProfile.getUserProfileId();
        String fileName = file.getName() + "-" + UUID.randomUUID();
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
