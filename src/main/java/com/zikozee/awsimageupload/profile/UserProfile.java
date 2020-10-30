package com.zikozee.awsimageupload.profile;

import lombok.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserProfile {

    private UUID userProfileId;
    private String username;

    @Getter(AccessLevel.NONE)//exclude from Getter
    private String userProfileImageLink; // S3 Key

    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserProfile that = (UserProfile) o;
//        return Objects.equals(userProfileId, that.userProfileId) &&
//                Objects.equals(username, that.username) &&
//                Objects.equals(userProfileImageLink, that.userProfileImageLink);
//        // we overrode the default in case any is null, so it doesn't blow
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userProfileId, username, userProfileImageLink);
//    }
}
