package au.com.aussie.chapter07.lombok;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Thing {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter
	@Setter
	Integer id;
	
	@Getter
	@Setter
	@Column
	String name;
}

//class ThingTest{
//	public static void main(String...strings){
//		Thing t = new Thing();
//		t.setName("Test Name");
//		
//		System.out.println(t);
//	}
//}