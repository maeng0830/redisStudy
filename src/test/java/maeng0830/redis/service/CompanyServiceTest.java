package maeng0830.redis.service;

import maeng0830.redis.domain.Company;
import maeng0830.redis.domain.CompanyDto;
import maeng0830.redis.repository.CompanyDtoRedisRepository;
import maeng0830.redis.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class CompanyServiceTest {

	@Autowired
	CompanyService companyService;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CompanyDtoRedisRepository companyDtoRedisRepository;

	@AfterEach
	void afterEach() {
		companyDtoRedisRepository.deleteAll();
	}

	@Test
	void cacheable() {
		// company 데이터 저장
		Company company = Company.builder()
				.name("AAA")
				.workerNumber(5L)
				.build();

		companyRepository.save(company);

		// 첫 번째 조회
		CompanyDto first = companyService.getCompany(company.getId());
		// companyRepository.findById 실행

		// 두 번째 조회
		CompanyDto second = companyService.getCompany(company.getId());
		// 첫 번째 조회에서 캐시에 데이터가 저장되었음 -> companyService.getCompany()는 실행되지 않고, 캐시에 저장된 데이터를 반환
	}

	@Test
	void cachePut() {
		// company 데이터 저장
		Company company = Company.builder()
				.name("AAA")
				.workerNumber(5L)
				.build();

		companyRepository.save(company);

		CompanyDto first = companyService.modCompany(company.getId(), 8L);
		// modCompany 실행

		CompanyDto second = companyService.modCompany(company.getId(), 10L);
		// modCompany 실행
	}

	@Test
	void cacheEvict() {
		// company 데이터 저장
		Company company = Company.builder()
				.name("AAA")
				.workerNumber(5L)
				.build();

		companyRepository.save(company);

		// 첫 번째 조회: companyService.getCompany() 실행
		CompanyDto first = companyService.getCompany(company.getId());
		// getCompany 실행

		// 두 번째 조회: 첫 번째 조회에서 캐시에 데이터가 저장되었음 -> companyService.getCompany()는 실행되지 않고, 캐시에 저장된 데이터를 반환
		CompanyDto second = companyService.getCompany(company.getId());

		// 캐시 삭제
		companyService.deleteCompany(company.getId());
		// deleteCompany 실행: Company 데이터가 삭제되었습니다.

		// 세 번째 조회: deleteCompany()를 호출하면서 캐시 데이터가 삭제되었음 -> companyService.getCompany() 다시 실행
		CompanyDto third = companyService.getCompany(company.getId());
		// getCompany 실행
	}
}