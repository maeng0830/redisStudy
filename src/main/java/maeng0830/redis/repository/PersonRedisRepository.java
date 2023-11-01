package maeng0830.redis.repository;

import java.util.List;
import maeng0830.redis.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, Long> {

	List<Person> findByName(String name);
}
