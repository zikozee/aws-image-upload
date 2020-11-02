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

    }
}
