package app.controllers;

import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    public static MockMultipartFile[] getMultipartFiles() {
        MockMultipartFile[] multipartFiles = new MockMultipartFile[2];
        try {
            multipartFiles[0] = new MockMultipartFile("files", "user.json",
                    "application/json",
                    new FileInputStream(FileUtils.class.getClassLoader().getResource("users.json").getFile()));
            multipartFiles[1] = new MockMultipartFile("files", "event.json",
                    "application/json",
                    new FileInputStream(FileUtils.class.getClassLoader().getResource("events.json").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFiles;
    }

}
