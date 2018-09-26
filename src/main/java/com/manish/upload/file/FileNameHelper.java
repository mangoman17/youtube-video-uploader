
package com.manish.upload.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class FileNameHelper {

    public static final String FILENAME_PATTERN = "dd-MMM-yyyy";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(FILENAME_PATTERN);

    private static final String[] ignoredFiles = {".DS_Store"};
    private static final List<String> IGNORED_FILES = CollectionUtils.arrayToList(ignoredFiles);

    private static final String[] ignoredExtensions = {"iml"};
    private static final List<String> IGNORED_EXTENSIONS = CollectionUtils.arrayToList(ignoredExtensions);

    private String sourceDirectory;
    private String destinationDirectory;

    public void scanDirectory(List<VideoFile> videoFiles, File directory) {
        if (!directory.isDirectory()
                && !IGNORED_FILES.contains(directory.getName())
                && !IGNORED_EXTENSIONS.contains(FilenameUtils.getExtension(directory.getName()))) {
            videoFiles.add(getVideoFile(directory));
        } else {
            File[] children = directory.listFiles();
            if (ArrayUtils.isNotEmpty(children)) {
                for (File child : children) {
                    scanDirectory(videoFiles, child);
                }
            }
        }
    }

    protected VideoFile getVideoFile(File file) {
        VideoFile videoFile = new VideoFile();
        videoFile.setSourceParentDir(file.getParent());
        videoFile.setOldFileName(file.getName());
        videoFile.setLastModified(file.lastModified());
        videoFile.setDestinationParentDir(this.destinationDirectory);
        videoFile.setNewFileLabel(DATE_FORMAT.format(new Date(file.lastModified())));
        videoFile.setNewFileExtension(FilenameUtils.getExtension(file.getName()));
        return videoFile;
    }

    public Map<Long, List<VideoFile>> generateVideoFilesMap(List<VideoFile> videoFiles) {
        Map<Long, List<VideoFile>> videoFilesMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(videoFiles)) {
            for (VideoFile videoFile : videoFiles) {
                long lastmodified = videoFile.getLastModified();
                List<VideoFile> subVideoFilesList = videoFilesMap.get(lastmodified);
                if(subVideoFilesList == null || subVideoFilesList.isEmpty()) {
                    subVideoFilesList = new ArrayList<>();
                    videoFilesMap.put(lastmodified, subVideoFilesList);
                }
                
                subVideoFilesList.add(videoFile);
            }
        }

        return videoFilesMap;
    }
    
    public void populateVideoFileNewNames(Map<Long, List<VideoFile>> videoFilesMap) {
    	for(Map.Entry<Long, List<VideoFile>> entry : videoFilesMap.entrySet()) {
    		long lastModified = entry.getKey();
    		List<VideoFile> subVideoFilesList = entry.getValue();
    		if(!CollectionUtils.isEmpty(subVideoFilesList)) {
                if(subVideoFilesList.size() == 1) {
                	VideoFile videoFile = subVideoFilesList.get(0);
                	videoFile.setNewFileLabel(DATE_FORMAT.format(new Date(lastModified)));
                } else {
                	int count = 1;
                	for(VideoFile videoFile : subVideoFilesList) {
                		videoFile.setNewFileLabel(DATE_FORMAT.format(new Date(lastModified)) + count);
                		count++;
                	}
                }
            }
    	}
    }
}
