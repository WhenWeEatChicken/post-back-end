package com.post.www.application;

import com.post.www.domain.Comment;
import com.post.www.domain.CommentRepository;
import com.post.www.domain.Post;
import com.post.www.interfaces.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments(Post post){
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;
    }

    public Comment addComment(CommentRequestDto requestDto){
        Comment comment = Comment.builder()
                .post(requestDto.getPost())
                .contents(requestDto.getContents())
                .commentIdx(requestDto.getCommentIdx())
                .userIdx(requestDto.getUserIdx())
                .isDel("N")
                .build();
        return commentRepository.save(comment);
    }

}
