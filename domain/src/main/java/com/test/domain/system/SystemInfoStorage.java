package com.test.domain.system;

import java.io.File;

public interface SystemInfoStorage {

    String save(int cpuCount, long totalMemory, long freeMemory, long allocatedMemory, long maxMemory);

    File get(String bucketName, String key);
}
