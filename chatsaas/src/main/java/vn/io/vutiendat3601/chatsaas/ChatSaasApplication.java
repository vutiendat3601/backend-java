package vn.io.vutiendat3601.chatsaas;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.chatsaas.entity.App;
import vn.io.vutiendat3601.chatsaas.entity.Channel;
import vn.io.vutiendat3601.chatsaas.entity.Message;
import vn.io.vutiendat3601.chatsaas.entity.User;
import vn.io.vutiendat3601.chatsaas.repository.AppRepository;
import vn.io.vutiendat3601.chatsaas.repository.ChannelRepository;
import vn.io.vutiendat3601.chatsaas.repository.MessageRepository;
import vn.io.vutiendat3601.chatsaas.repository.UserRepository;

@Slf4j
@SpringBootApplication
public class ChatSaasApplication {
  public static void main(String[] args) {
    SpringApplication.run(ChatSaasApplication.class, args);
  }

  @EventListener
  void onStartup(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var userRepository = ctx.getBean(UserRepository.class);
    var appRepository = ctx.getBean(AppRepository.class);
    var channelRepository = ctx.getBean(ChannelRepository.class);
    var messageRepository = ctx.getBean(MessageRepository.class);

    var apiKey = "vutiendat3601-api-client-key";
    var clientUserId = "vutiendat3601";

    if (appRepository.findByApiKey(apiKey).isEmpty()) {
      var app = App.builder().name("LaiXe").isActive(true).apiKey(apiKey).build();
      app = appRepository.save(app);
      var channel =
          Channel.builder()
              .clientReferenceId("channel1")
              .name("channel1")
              .createdAt(Instant.now())
              .app(app)
              .build();
      channel = channelRepository.save(channel);

      var user =
          User.builder()
              .app(app)
              .clientUserId(clientUserId)
              .name("Dat Vu")
              .profileImgUrl(
                  "https://lh3.googleusercontent.com/a/ACg8ocKr8LQ5YHKZx4ULgyvfLAzMR6vjCU7khlsO1PugLvRHXVSP1ydYXw=s400-c")
              .build();
      userRepository.save(user);
    }
    if (messageRepository.count() == 0) {
      var channel = channelRepository.findAll().get(0);
      var user = userRepository.findAll().get(0);
      for (int i = 0; i < 100; i++) {
        messageRepository.save(
            Message.builder()
                .channel(channel)
                .user(user)
                .text("Message %d".formatted(i + 1))
                .imgUrl("imgurl")
                .isDeleted(false)
                .build());
      }
    }
  }
}
