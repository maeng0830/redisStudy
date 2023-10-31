package maeng0830.redis;

import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("person")
public class Person {

	@Id // redis id
	private Long id;

	@Indexed // 조회 조건으로 사용할 필드에 적용
	private String name;

	@TimeToLive(unit = TimeUnit.SECONDS) // 유효시간. 유효시간이 지나면 자동으로 데이터가 삭제된다.
	private Long expiration;

	@Builder
	public Person(Long id, String name, Long expiration) {
		this.id = id;
		this.name = name;
		this.expiration = expiration;
	}

	public void changeName(String name) {
		this.name = name;
	}
}
