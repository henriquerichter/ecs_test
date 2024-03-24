package com.test.ports.game;

import com.test.adapters.aws.s3.S3ClientWrapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class GameStoragePort implements GameStorage {

    private final S3ClientWrapper s3ClientWrapper;

    public GameStoragePort(S3ClientWrapper s3ClientWrapper) {
        this.s3ClientWrapper = s3ClientWrapper;
    }

    @Override
    public void save(String bucketName, String key, String content) {
        Objects.requireNonNull(bucketName, "bucketName must not be null");
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(content, "content must not be null");
        this.s3ClientWrapper.putObject(bucketName, key, content);
    }

    @Override
    public File get(String bucketName, String key) {
        Objects.requireNonNull(bucketName, "bucketName must not be null");
        Objects.requireNonNull(key, "key must not be null");
        return this.s3ClientWrapper.getObject(bucketName, key);
    }
}
