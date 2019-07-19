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
import com.onetomany.MappingOnetoMany.model.Post;
import com.onetomany.MappingOnetoMany.repository.PostRepository;
//Used for create update delete add a post 
@Controller
public class PostController {

	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/posts")
	public Page<Post> getAllPage(Pageable pageable)
	{
		return postRepository.findAll(pageable);
	}
	
	@PostMapping("/posts")
	public Post createPost(@Valid @RequestBody Post post)
	{
		return postRepository.save(post);
	}
	
	@PutMapping("/post/{id}")
	public Post updatePost(@PathVariable("id") long id,@Valid @RequestBody Post postput)
	{
		return postRepository.findById(id).map(post -> {
		    post.setTitle(postput.getTitle());
            post.setDescription(postput.getDescription());
            post.setContent(postput.getContent());
            return postRepository.save(post);
		}
				).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found"));
	}
	
	  @DeleteMapping("/posts/{postId}")
	    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
	        return postRepository.findById(postId).map(post -> {
	            postRepository.delete(post);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	    }
	
}
