package com.zikozee.awsimageupload.profile;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface UserProfileService {

    List<UserProfile> getUserProfiles();

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file);
}
