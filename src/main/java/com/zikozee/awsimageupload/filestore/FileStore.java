package com.zikozee.awsimageupload.filestore;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStore implements FileStoreService{

    private final AmazonS3 s3;

    @Override
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream, long contentLength) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()){
                map.forEach(metadata::addUserMetadata);
            }
        });

        try{
//            s3.putObject(path, fileName, inputStream, metadata);

            TransferManager tm = new TransferManager(s3);
            PutObjectRequest request = new PutObjectRequest(path, fileName, inputStream, metadata);

            request.setGeneralProgressListener(new ProgressListener() {
                @Override
                public void progressChanged(ProgressEvent progressEvent) {
                    final ProgressEventType progressEventType = progressEvent.getEventType();
                    System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());

                    if(progressEventType == ProgressEventType.TRANSFER_COMPLETED_EVENT){
                        System.out.println("document transfer completed successfully");
                    }
                }
            });


            tm.upload(request);
        } catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    @Override
    public byte[] download(String path, String key) {
        try{
            S3Object object = s3.getObject(path, key);
            S3ObjectInputStream inputStream = object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        }catch (AmazonServiceException| IOException e){
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }

}
