package com.post.www.interfaces;

import com.post.www.application.FileService;
import com.post.www.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class FileController {

    @Value("${temp.path}")
    private String tempPath;

    private final FileService fileService;

    /*@PostMapping("/upload")
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
    }*/

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Long fileId) throws IOException {
        File file = fileService.getFile(fileId);
        Path path = Paths.get(file.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }


}
