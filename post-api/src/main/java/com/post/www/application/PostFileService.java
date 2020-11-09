package com.post.www.application;


import com.post.www.application.exception.FileNotFoundException;
import com.post.www.config.enums.FileType;
import com.post.www.domain.PostFile;
import com.post.www.domain.FileRepository;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostFileService {

    private final FileRepository fileRepository;

    public PostFile addFile(Object obj, String type, String filePath, String fileName) {
        PostFile file;
        file = PostFile.builder()
                .fileName(fileName)
                .filePath(filePath)
                .post((Post) obj)
                .refType(FileType.POST)
                .build();
//        if(type.equals("post")){
//            file = PostFile.builder()
//                    .fileName(fileName)
//                    .filePath(filePath)
//                    .post((Post) obj)
//                    .refType(FileType.POST)
//                    .build();
//        }else{
//            file = PostFile.builder()
//                    .fileName(fileName)
//                    .filePath(filePath)
//                    .comment((Comment) obj)
//                    .refType(FileType.COMMENT)
//                    .build();
//        }
        return fileRepository.save(file);
    }

    public FileResponseDto getFile(Long fileId) {
        PostFile file = fileRepository.findByIdx(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));

        return new FileResponseDto(file);
    }
}
