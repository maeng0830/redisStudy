package maeng0830.redis.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching // 캐시 기능 활성화
@EnableRedisRepositories // RedisRepository 활성화
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

	@Bean
	public RedisCacheManager redisCacheManager() {
		// RedisCache를 사용하기 위한 설정 객체
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.computePrefixWith(CacheKeyPrefix.simple()) // 레디스 캐시의 키를 value::key 형태로 만든다.
				.disableCachingNullValues() // null value에 대해서는 캐시하지 않음
				.entryTtl(Duration.ofMinutes(2)) // 캐시의 기본 유효 기간
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 레디스 캐시의 키 직렬화 방법
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())); // 레디스 캐시의 값 직렬화 방법

		return RedisCacheManager
				.RedisCacheManagerBuilder
				.fromConnectionFactory(redisConnectionFactory())
				.cacheDefaults(redisCacheConfiguration)
				.build();
	}
}
