package com.zikozee.awsimageupload.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BucketName {

    PROFILE_IMAGE("zikozee-image-upload-123");

    private final String bucketName;

}
