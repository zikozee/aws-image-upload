package com.zikozee.awsimageupload.profile;

import com.zikozee.awsimageupload.datastore.FakeUserProfileDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileDataAccessService implements UserProfileService{

    private final FakeUserProfileDataStore fakeUserProfileDataStore;


    @Override
    public List<UserProfile> getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfiles();
    }

    @Override
    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1. Check if Image is not empty
        //2. If file is an Image
        //3. The user exists in our database
        //4. Grab some metadata from file if any
        //5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
    }
}
