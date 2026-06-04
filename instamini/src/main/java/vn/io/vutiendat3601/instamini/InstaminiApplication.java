package vn.io.vutiendat3601.instamini;

import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.io.vutiendat3601.instamini.dto.request.feed.CreatePostRequest;
import vn.io.vutiendat3601.instamini.dto.request.profile.FollowProfileRequest;
import vn.io.vutiendat3601.instamini.entity.User;
import vn.io.vutiendat3601.instamini.model.UserPrincipal;
import vn.io.vutiendat3601.instamini.repository.UserRepository;
import vn.io.vutiendat3601.instamini.service.feed.PostService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileFollowingService;
import vn.io.vutiendat3601.instamini.service.profile.ProfileService;

@SpringBootApplication
public class InstaminiApplication {
  public static void main(String[] args) {
    SpringApplication.run(InstaminiApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  void onStartup(ApplicationReadyEvent event) {
    var ctx = event.getApplicationContext();
    var userRepository = ctx.getBean(UserRepository.class);
    var postService = ctx.getBean(PostService.class);
    var profileService = ctx.getBean(ProfileService.class);
    var profileFollowingService = ctx.getBean(ProfileFollowingService.class);

    // Preparing users, profile following
    var vutiendat3601User =
        User.builder().id(UUID.randomUUID()).username("vutien.dat.3601@gmail.com").name("Dat Vu").build();
    var user1 = User.builder().id(UUID.randomUUID()).username("username1").name("User 1").build();
    vutiendat3601User = userRepository.save(vutiendat3601User);
    user1 = userRepository.save(user1);
    var vutiendat3601Principal = UserPrincipal.authenticated(vutiendat3601User);
    var user1Principal = UserPrincipal.authenticated(user1);
    var vutiendat3601UserProfileDto = profileService.getProfile(vutiendat3601Principal).profile();
    var user1ProfileDto = profileService.getProfile(user1Principal).profile();
    profileFollowingService.follow(
        vutiendat3601Principal, new FollowProfileRequest(user1ProfileDto.id()));
    profileFollowingService.follow(
        user1Principal, new FollowProfileRequest(vutiendat3601UserProfileDto.id()));
    // Mocking posts
    postService.createPost(user1Principal, new CreatePostRequest(base64ImageString, "Post 1"));
  }

  private String base64ImageString =
      "data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAIAAABMXPacAAACtUlEQVR4nOzcu0vVcRyH8dSDdIUKogYLWg1KQjMSNG1IiMKiMIJAGmzqMkRFDRUNIqIZLkJRB6MoHCS7EEGECRVdCLEbRUYNDQ4JQRJR0t/whuBZntf8/k4Pn+UnnsLpsQezEqte1EX7rRfWRfu2geloP/9lWbSvffc52tccnoz2I2390b40Wuu/MwDMADADwAwAMwDMADADwAwAMwDMADADwAwAMwDMALBC9aOd0YO71eej/ZIv+6J98c+uaD/+tTXad524HO2fFjdH+7WzP0V7LwBmAJgBYAaAGQBmAJgBYAaAGQBmAJgBYAaAGQBmAJgBYIVic3P0YE9xR7R//XYi2jcO1kf7ib03o/1I6ZFo/2FyabS/VLUm2nsBMAPADAAzAMwAMAPADAAzAMwAMAPADAAzAMwAMAPADAArdH48Gz1Y3d0S7YebmqJ9/5bs+/6zZYuj/c+eedF+8uL6aP99+8Fo7wXADAAzAMwAMAPADAAzAMwAMAPADAAzAMwAMAPADAAzAKyko/x69ODvucpoP7poKtp3n7wS7V992xbt+1rKo33D0PFov7JjKNp7ATADwAwAMwDMADADwAwAMwDMADADwAwAMwDMADADwAwAK1RNPYwetFcuj/atM53RvmHD7Wh/qO9otL81PhbtB2cqon3jpvfR3guAGQBmAJgBYAaAGQBmAJgBYAaAGQBmAJgBYAaAGQBmAFihs+JG9OBXffZ7QWU1V6P97q7H0X544fNo3/DkTLTfONAe7XvfZP9/4AXADAAzAMwAMAPADAAzAMwAMAPADAAzAMwAMAPADAAzAKzk1IHe6MHvnuz7/o/9x6L9vWt3ov3M6IpoXzs9N9rXLZgT7e9XZ39f8QJgBoAZAGYAmAFgBoAZAGYAmAFgBoAZAGYAmAFgBoAZAPYvAAD//+bOYfvb2ofdAAAAAElFTkSuQmCC";
}
