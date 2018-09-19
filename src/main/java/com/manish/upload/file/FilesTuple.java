package com.manish.upload.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FilesTuple {
    private File sourceFile;
    private File destinationFile;
}
