package vn.io.vutiendat3601.instamini.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(redisNamespace = "vutiendat3601:instamini")
public class SessionConfiguration {}
