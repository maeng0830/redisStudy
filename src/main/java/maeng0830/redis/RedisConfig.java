package maeng0830.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@PropertySource("classpath:/env.properties")
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	/**
	 * RedisConnectionFactory는 스프링 어플리케이션과 레디스를 연결하기 위해 사용된다.
	 * 커넥션의 종류로는 Jedis와 Lettuce가 있는데, Lettuce의 성능이 더 좋은 것으로 알려져있다.
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		// redis 연결 정보(host, port, password)를 지정한다.
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
				host, port);
		redisStandaloneConfiguration.setPassword(password);

		// redis 연결 정보를 토대로 LettuceConnectionFactory 객체를 생성하여 빈으로 등록한다.
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	/**
	 * RedisTemplate는 RedisConnection에서 넘겨준 byte 값을 직렬화 하기 위해 사용된다.
	 */
	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();

		// 데이터를 받아 올 RedisConnection 설정
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}
}
