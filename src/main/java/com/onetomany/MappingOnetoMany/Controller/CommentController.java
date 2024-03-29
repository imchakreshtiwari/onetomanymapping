package com.onetomany.MappingOnetoMany.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onetomany.MappingOnetoMany.Exception.ResourceNotFoundException;
import com.onetomany.MappingOnetoMany.model.Comment;
import com.onetomany.MappingOnetoMany.repository.CommentRepository;
import com.onetomany.MappingOnetoMany.repository.PostRepository;

//for post create update and delete comments

@Controller
public class CommentController {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@GetMapping("/post/{postid}/comments")
	 public Page<Comment> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId,
             Pageable pageable) {
return commentRepository.findByPostId(postId, pageable);
}
	
	@PostMapping("/post/{postid}/comments")
	 public Comment createComment(@PathVariable (value = "postId") Long postId,
             @Valid @RequestBody Comment comment) {
return postRepository.findById(postId).map(post -> {
comment.setPost(post);
return commentRepository.save(comment);
}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	  public Comment updateComment(@PathVariable (value = "postId") Long postId,
              @PathVariable (value = "commentId") Long commentId,
              @Valid @RequestBody Comment commentRequest) {
if(!postRepository.existsById(postId)) {
throw new ResourceNotFoundException("PostId " + postId + " not found");
}

return commentRepository.findById(commentId).map(comment -> {
comment.setText(commentRequest.getText());
return commentRepository.save(comment);
}).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	  public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
              @PathVariable (value = "commentId") Long commentId) {
return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
commentRepository.delete(comment);
return ResponseEntity.ok().build();
}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
}
}
