package maeng0830.redis.repository;

import maeng0830.redis.domain.CompanyDto;
import org.springframework.data.repository.CrudRepository;

public interface CompanyDtoRedisRepository extends CrudRepository<CompanyDto, Long> {

}
