package com.post.www.application;

import com.post.www.application.exception.CommentNotFoundException;
import com.post.www.application.exception.PostNotFoundException;
import com.post.www.application.exception.UserNotExistedException;
import com.post.www.domain.*;
import com.post.www.interfaces.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public Comment addComment(Long userIdx, CommentRequestDto requestDto) {
        User user = userRepository.findByIdx(userIdx)
                .orElseThrow(() -> new UserNotExistedException(userIdx));
        Post post = postRepository.findByIdx(requestDto.getPostIdx())
                .orElseThrow(() -> new PostNotFoundException(requestDto.getPostIdx()));
        Comment comment = Comment.builder()
                .post(post)
                .contents(requestDto.getContents())
                .commentIdx(requestDto.getCommentIdx())
                .user(user)
                .isDel("N")
                .build();
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long idx, Long userIdx, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findByIdx(idx)
                .orElseThrow(() -> new CommentNotFoundException(idx));

        if(comment.getUser().getIdx().equals(userIdx)){
            String contents = requestDto.getContents();
            comment.updateComment(contents);
        }
        return comment;
    }
}
