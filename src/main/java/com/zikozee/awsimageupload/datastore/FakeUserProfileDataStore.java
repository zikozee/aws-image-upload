package com.zikozee.awsimageupload.datastore;

import com.zikozee.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile>  USER_PROFILES= new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("5fea4825-1bf2-4f80-b819-b00aa44b884c"), "janetjones", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("dc35f296-3be9-4356-977b-b0ce296fd1dd"), "antoniojunior", null));
    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
