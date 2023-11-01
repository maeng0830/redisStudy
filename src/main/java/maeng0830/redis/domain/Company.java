package maeng0830.redis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Long workerNumber;

	@Builder
	public Company(String name, Long workerNumber) {
		this.name = name;
		this.workerNumber = workerNumber;
	}

	public void changeWorkerNumber(Long number) {
		this.workerNumber = number;
	}
}
