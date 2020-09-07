package com.post.www.interfaces;

import com.post.www.application.CommentService;
import com.post.www.domain.Comment;
import com.post.www.interfaces.dto.CommentRequestDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {"2.CommentController"})
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> create(
            @Valid @RequestBody CommentRequestDto requestDto
    ) throws URISyntaxException {
        Comment comment = commentService.addComment(requestDto);
        URI location = new URI("/comment/" + comment.getIdx());
        return ResponseEntity.created(location).body("{}");
    }

}
