package au.com.aussie.chapter07.lifecyle;

import java.util.BitSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.jboss.logging.Logger;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class LifecycleThing {
	
	public static Logger logger = Logger.getLogger(LifecycleThing.class);
	public static BitSet lifecycleCalls = new BitSet();

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter
	@Setter
	Integer id;
	
	@Getter
	@Setter
	@Column
	String name;
	
	@PostLoad
	public void postLoad(){
		log("postLoad", 0);
	}
	
	@PrePersist
	public void prePersist(){
		log("prePersist", 1);
	}
	
	@PostPersist
	public void postPersist(){
		log("postPersist", 2);
	}
	
	@PreUpdate
	public void preUpdate(){
		log("preUpdate", 3);
	}
	
	@PostUpdate
	public void postUpdate(){
		log("postUpdate", 4);
	}
	
	@PreRemove
	public void preRemove(){
		log("preRemove", 5);
	}
	
	@PostRemove
	public void postRemove(){
		log("postRemove", 6);
	}
	
	
	private void log(String method, int index){
		lifecycleCalls.set(index, true);
		logger.errorf("%12s: %s (%s)", method, this.getClass().getSimpleName(), this.toString());
	}
}
