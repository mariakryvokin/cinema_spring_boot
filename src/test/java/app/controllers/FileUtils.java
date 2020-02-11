package app.controllers;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileUtils {

    private static String usersJson = "[{\n" +
            "  \"firstName\" : \"TestUser\",\n" +
            "  \"lastName\" : \"TestUser\",\n" +
            "  \"email\" : \"TestUser@gmail.com\",\n" +
            "  \"birthday\" : \"2020-01-22 10:00:00\",\n" +
            "  \"password\" : \"$2a$10$U7XpuIwdvgFlrhpdX9e.UutvcF6rdb6mOTWGkS2UozjQ2wVw1Xewe\",\n" +
            "  \"roles\" : [ {\n" +
            "    \"name\" : \"RESGISTERED_USER\"\n" +
            "  } ],\n" +
            "  \"tickets\" : null\n" +
            "}]";


    public static MockMultipartFile[] getMultipartFiles() {
        MockMultipartFile[] multipartFiles = new MockMultipartFile[2];
        multipartFiles[0] = new MockMultipartFile("files", "user.json",
                "application/json", usersJson.getBytes());
        multipartFiles[1] = new MockMultipartFile("files", "event.json",
                "application/json", "".getBytes());
        return multipartFiles;
    }

}
