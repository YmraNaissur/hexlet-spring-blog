package ru.naissur.model;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.naissur.repository.CommentRepository;
import ru.naissur.repository.PostRepository;
import ru.naissur.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class ModelGenerator {

  private final Faker faker;
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  @PostConstruct
  public void generateData() {
    for (int i = 0; i < 5; i++) {
      var user = new User();
      user.setFirstName(faker.name().firstName());
      user.setLastName(faker.name().lastName());
      user.setEmail(faker.internet().emailAddress());
      user.setBirthDate(faker.timeAndDate().birthday());
      userRepository.save(user);

      var post = new Post();
      post.setTitle(faker.book().title());
      post.setBody(faker.lorem().sentence(5, 5));
      postRepository.save(post);

      var comment = new Comment();
      comment.setPostId((long) i + 1);
      comment.setBody(faker.lorem().sentence(3, 5));
      commentRepository.save(comment);
    }
  }

}
