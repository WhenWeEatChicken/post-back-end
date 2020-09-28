package com.post.www.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
public class FileController {

    @Value("${temp.path}")
    private String tempPath;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException, URISyntaxException {
        LocalDate localDate = LocalDate.now();
        String filePath = tempPath + localDate + UUID.randomUUID().toString().replace("-", "");
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("Error file upload.", e);
            throw e;
        }
        URI url = new URI("/upload/" + 1);
        return ResponseEntity.created(url).body(filePath);
    }


}
