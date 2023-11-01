package maeng0830.redis.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("companyDto")
@NoArgsConstructor
@Getter
public class CompanyDto {

	@Id
	private Long id;
	private String name;
	private Long workerNumber;

	@Builder
	public CompanyDto(Long id, String name, Long workerNumber) {
		this.id = id;
		this.name = name;
		this.workerNumber = workerNumber;
	}

	public static CompanyDto from(Company company) {
		return CompanyDto.builder()
				.id(company.getId())
				.name(company.getName())
				.workerNumber(company.getWorkerNumber())
				.build();
	}
}
