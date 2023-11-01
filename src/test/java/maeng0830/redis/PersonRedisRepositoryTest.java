package maeng0830.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import maeng0830.redis.domain.Person;
import maeng0830.redis.repository.PersonRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PersonRedisRepositoryTest {

	@Autowired
	private PersonRedisRepository personRedisRepository;

	@BeforeEach
	void beforeEach() {
		personRedisRepository.deleteAll();
	}

	@Test
	void findByName() {
		// person 데이터 생성 및 저장
		ArrayList<Person> persons = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0) {
				Person person = Person.builder()
						.name("AAA")
						.expiration(60L)
						.build();

				persons.add(person);
			} else {
				Person person = Person.builder()
						.name("BBB")
						.expiration(60L)
						.build();

				persons.add(person);
			}
		}

		personRedisRepository.saveAll(persons);

		Person person = persons.get(0);
		System.out.println("person.getId() = " + person.getId());
		// person.getId() = 2131709625338483644

		// 조회
		List<Person> aaa = personRedisRepository.findByName("AAA");
		List<Person> bbb = personRedisRepository.findByName("BBB");

		assertThat(aaa).hasSize(3)
				.extracting("name")
				.containsExactlyInAnyOrder("AAA", "AAA", "AAA");

		assertThat(bbb).hasSize(3)
				.extracting("name")
				.containsExactlyInAnyOrder("BBB", "BBB", "BBB");
	}

	@Test
	void changeData() {
		// person 데이터 생성 및 저장
		ArrayList<Person> persons = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0) {
				Person person = Person.builder()
						.name("AAA")
						.expiration(60L)
						.build();

				persons.add(person);
			} else {
				Person person = Person.builder()
						.name("BBB")
						.expiration(60L)
						.build();

				persons.add(person);
			}
		}

		personRedisRepository.saveAll(persons);

		// 변경 전 조회
		List<Person> before = personRedisRepository.findByName("CCC");
		assertThat(before).hasSize(0);

		// 변경 및 변경 후 조회
		persons.get(0).changeName("CCC");
		personRedisRepository.save(persons.get(0));

		List<Person> after = personRedisRepository.findByName("CCC");
		assertThat(after).hasSize(1);
	}
}