package maeng0830.redis.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maeng0830.redis.domain.Company;
import maeng0830.redis.domain.CompanyDto;
import maeng0830.redis.repository.CompanyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	@Cacheable(value = "companyDto", key = "#companyId")
	public CompanyDto getCompany(Long companyId) {
		log.info("getCompany 실행");
		Company company = companyRepository.findById(companyId)
				.orElseThrow(NoSuchElementException::new);

		return CompanyDto.from(company);
	}

	@CachePut(value = "companyDto", key = "#companyId")
	public CompanyDto modCompany(Long companyId, Long workerNumber) {
		log.info("modCompany 실행");
		Company company = companyRepository.findById(companyId)
				.orElseThrow(NoSuchElementException::new);

		company.changeWorkerNumber(workerNumber);

		return CompanyDto.from(company);
	}

	@CacheEvict(value = "companyDto", key = "#companyId")
	public void deleteCompany(Long companyId) {
		log.info("deleteCompany 실행: Company 데이터가 삭제되었습니다.");
	}
}
