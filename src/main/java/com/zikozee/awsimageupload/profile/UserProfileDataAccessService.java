package com.zikozee.awsimageupload.profile;

import com.zikozee.awsimageupload.datastore.FakeUserProfileDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileDataAccessService implements UserProfileService{

    private final FakeUserProfileDataStore fakeUserProfileDataStore;


    @Override
    public List<UserProfile> getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfiles();
    }
}
