package maeng0830.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

@SpringBootTest
public class RedisTemplateTest {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	private final String key1 = "key1";
	private final String key2 = "key2";

	@BeforeEach
	void beforeEach() {
		redisTemplate.delete(key1);
		redisTemplate.delete(key2);
	}

	@Test
	void stringTest() {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

		valueOperations.set(key1, "1");
		System.out.println("key1 = " + valueOperations.get(key1));
		// key1 = 1

		valueOperations.set(key2, "2");
		System.out.println("key2 = " + valueOperations.get(key2));
		// key2 = 2

		valueOperations.set(key2, "1");
		System.out.println("key2 = " + valueOperations.get(key2));
		// key2 = 1
	}

	@Test
	void listTest() {
		ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();

		stringObjectListOperations.rightPush(key1, "A");
		stringObjectListOperations.rightPush(key1, "B");
		stringObjectListOperations.rightPush(key1, "C");
		stringObjectListOperations.rightPush(key1, "D");

		System.out.println(stringObjectListOperations.size(key1));
		// 4

		List<Object> list = stringObjectListOperations.range(key1, 0, 3);
		System.out.println("Arrays.toString(list.toArray()) = " + Arrays.toString(list.toArray()));
		// Arrays.toString(list.toArray()) = [A, B, C, D]
	}

	@Test
	void setTest() {
		SetOperations<String, Object> stringObjectSetOperations = redisTemplate.opsForSet();

		stringObjectSetOperations.add(key1, "A");
		stringObjectSetOperations.add(key1, "B");
		stringObjectSetOperations.add(key1, "C");
		stringObjectSetOperations.add(key1, "D");

		Set<Object> members = stringObjectSetOperations.members(key1);

		System.out.println(stringObjectSetOperations.size(key1));
		// 4

		System.out.println("Arrays.toString(members.toArray()) = " + Arrays.toString(members.toArray()));
		// Arrays.toString(members.toArray()) = [C, D, A, B]
	}

	@Test
	void zSetTest() {
		ZSetOperations<String, Object> stringObjectZSetOperations = redisTemplate.opsForZSet();

		stringObjectZSetOperations.add(key1, "A", 4);
		stringObjectZSetOperations.add(key1, "B", 3);
		stringObjectZSetOperations.add(key1, "C", 2);
		stringObjectZSetOperations.add(key1, "D", 1);

		Set<Object> range = stringObjectZSetOperations.range(key1, 0, 3);

		System.out.println(stringObjectZSetOperations.size(key1));
		// 4

		System.out.println("Arrays.toString(range.toArray()) = " + Arrays.toString(range.toArray()));
		// Arrays.toString(range.toArray()) = [D, C, B, A]
	}
}
