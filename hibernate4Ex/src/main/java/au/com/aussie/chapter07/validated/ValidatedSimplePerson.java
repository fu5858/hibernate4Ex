package au.com.aussie.chapter07.validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

@Entity
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatedSimplePerson {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter
	@Setter
	Long id;
	
	@Getter
	@Setter
	@Size(min=2, max=60)
	@Column
	@NotNull
	String fname;
	
	@Getter
	@Setter
	@Size(min=2, max=60)
	@NotNull
	@Column
	String lname;
	
	@Getter
	@Setter
	@Min(value=13)
	@Column
	Integer age;
}
