package com.post.www.application;

import com.post.www.domain.*;
import com.post.www.interfaces.dto.CommentRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class CommentServiceTests {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void getComments(){
        List<Comment> comments = new ArrayList<>();
        Post post = Post.builder()
                .idx(1L)
                .user(any())
                .title("Seoul")
                .contents("Bob zip")
                .build();
        Comment comment = Comment.builder()
                .idx(1L)
                .commentIdx(0L)
                .contents("asdf")
                .post(post)
                .isDel("N")
                .build();

        comments.add(comment);
        given(commentRepository.findAllByPost(post)).willReturn(comments);

        assertThat(commentService.getComments(post).get(0).getIdx()).isEqualTo(1L);
    }

    @Test
    void addComment(){
        User user = User.builder()
                .idx(1L)
                .build();
        given(userRepository.findByIdx(any())).willReturn(Optional.of(user));

        given(commentRepository.save(any())).will(invocation -> {
            Comment comment = invocation.getArgument(0);
            return comment;
        });
        Post post = Post.builder()
                .idx(1L)
                .user(user)
                .title("Seoul")
                .contents("Bob zip")
                .build();
        given(postRepository.findByIdx(any())).willReturn(Optional.of(post));
        CommentRequestDto requestDto = CommentRequestDto.builder()
                .postIdx(post.getIdx())
                .contents("Busan")
                .commentIdx(0L)
                .build();
        Comment created = commentService.addComment(1L, requestDto);
        assertThat(created.getContents()).isEqualTo("Busan");
    }

    @Test
    public void updateComment() {
        Comment comment = Comment.builder()
                .idx(1L)
                .user(User.builder().idx(1L).build())
                .contents("Seoul")
                .build();

        given(commentRepository.findByIdx(1L))
                .willReturn(Optional.of(comment));
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .contents("Busan")
                .build();

        commentService.updateComment(1L,1L, commentRequestDto);

        assertThat(comment.getContents()).isEqualTo("Busan");
    }

}