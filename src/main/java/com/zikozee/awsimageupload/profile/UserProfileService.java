package com.zikozee.awsimageupload.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserProfileService {

    List<UserProfile> getUserProfiles();
}
