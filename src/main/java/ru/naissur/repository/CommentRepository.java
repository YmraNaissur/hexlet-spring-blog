package ru.naissur.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naissur.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Transactional
  void deleteByPostId(Long postId);
}
