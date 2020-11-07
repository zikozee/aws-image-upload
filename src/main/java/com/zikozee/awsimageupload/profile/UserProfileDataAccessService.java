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
        isFileEmpty(file);
        //2. If file is an Image
        isImage(file);

        //3. The user exists in our database
        UserProfile userProfile = getUserProfileOrThrow(userProfileId);

        //4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        //5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        //creating a folder per user
        String path = BucketName.PROFILE_IMAGE.getBucketName() + "/" + userProfile.getUserProfileId();
        String fileName = file.getOriginalFilename() + "-" + UUID.randomUUID();
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream(), file.getSize());
            userProfile.setUserProfileImageLink(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile userProfile = getUserProfileOrThrow(userProfileId);
        String path = BucketName.PROFILE_IMAGE.getBucketName()
                + "/" + userProfile.getUserProfileId();

        //optional can be used as streams
        return userProfile.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if(!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType()))
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");//throw custom exception
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId){
        return getUserProfiles()
                .stream()
                .filter(userProf -> userProf.getUserProfileId().equals(userProfileId))
                .findFirst().orElseThrow(() -> new IllegalStateException("User profile with id: " + userProfileId+ " not found"));

    }
}
