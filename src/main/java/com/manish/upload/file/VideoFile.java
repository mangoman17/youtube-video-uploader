package com.manish.upload.file;


import lombok.Data;

@Data
public class VideoFile implements Comparable<VideoFile> {
    private String sourceParentDir;
    private String oldFileName;
    private String newFileLabel;
    private String newFileName;
    private String newFileExtension;
    private String destinationParentDir;
    private long lastModified;

    @Override
    public int compareTo(VideoFile file) {
        if (file == null) {
            return 0;
        }
        if (this.lastModified < file.lastModified) {
            return -1;
        }
        if (this.lastModified > file.lastModified) {
            return 1;
        }
        return 0;
    }
}
