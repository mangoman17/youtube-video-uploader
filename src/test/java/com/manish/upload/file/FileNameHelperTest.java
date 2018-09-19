package com.manish.upload.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class FileNameHelperTest {

    private FileNameHelper helper = new FileNameHelper();

    @Test
    public void testScanDirectory_CurrentParent() {
        String parentPath = "/Users/m0g00tm/Documents/Personal/youtube-video-uploader/src";
        List<VideoFile> videoFiles = new ArrayList<>();
        helper.scanDirectory(videoFiles, new File(parentPath));
        Assert.assertEquals(5, videoFiles.size());
        for (VideoFile file : videoFiles) {
            System.out.println(file);
        }
        System.out.println("**************************");
        Map<Long, List<VideoFile>> videoFilesMap = helper.generateVideoFilesMap(videoFiles);
        for (Long lastModified : videoFilesMap.keySet()) {
            System.out.println(lastModified + " - " + videoFilesMap.get(lastModified).size());
            System.out.println(videoFilesMap.get(lastModified));
        }
    }
}
