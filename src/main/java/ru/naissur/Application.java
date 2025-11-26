package ru.naissur;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.annotation.RequestScope;
import ru.naissur.daytime.Day;
import ru.naissur.daytime.Daytime;
import ru.naissur.daytime.Night;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Faker faker() {
    return new Faker();
  }

  @Bean
  public Clock clock(@Value("${app.fixedTime:}") String fixedTime) {
    if (!fixedTime.isEmpty()) {
      return Clock.fixed(
          LocalDateTime.parse(fixedTime)
              .atZone(ZoneId.systemDefault())
              .toInstant(),
          ZoneId.systemDefault());
    }
    return Clock.systemDefaultZone();
  }

  @Bean
  @RequestScope
  public Daytime daytime(Clock clock) {
    var hours = LocalDateTime.now(clock).getHour();
    if (hours >= 6 && hours < 22) {
      return new Day();
    } else {
      return new Night();
    }
  }

}