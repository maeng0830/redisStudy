package maeng0830.redis;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, Long> {

	List<Person> findByName(String name);
}
