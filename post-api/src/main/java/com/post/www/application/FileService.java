package com.post.www.application;


import com.post.www.application.exception.FileNotFoundException;
import com.post.www.config.enums.FileType;
import com.post.www.domain.Comment;
import com.post.www.domain.File;
import com.post.www.domain.FileRepository;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    public File addFile(Object obj, String type, String filePath, String fileName) {
        File file;
        file = File.builder()
                .fileName(fileName)
                .filePath(filePath)
                .post((Post) obj)
                .refType(FileType.POST)
                .build();
//        if(type.equals("post")){
//            file = File.builder()
//                    .fileName(fileName)
//                    .filePath(filePath)
//                    .post((Post) obj)
//                    .refType(FileType.POST)
//                    .build();
//        }else{
//            file = File.builder()
//                    .fileName(fileName)
//                    .filePath(filePath)
//                    .comment((Comment) obj)
//                    .refType(FileType.COMMENT)
//                    .build();
//        }
        return fileRepository.save(file);
    }

    public FileResponseDto getFile(Long fileId) {
        File file = fileRepository.findByIdx(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));

        return new FileResponseDto(file);
    }
}
