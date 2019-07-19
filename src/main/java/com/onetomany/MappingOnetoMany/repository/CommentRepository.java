package com.onetomany.MappingOnetoMany.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.onetomany.MappingOnetoMany.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

	Page<Comment> findByPostId(long postid, Pageable pageable);
	 
	Optional<Comment> findByIdAndPostId(long id,long postid);
}
