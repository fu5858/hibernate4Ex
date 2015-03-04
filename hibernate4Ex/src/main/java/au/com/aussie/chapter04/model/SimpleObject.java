package au.com.aussie.chapter04.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SimpleObject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Column
	String key;
	
	@Column
	Long value;
	
	public SimpleObject(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof SimpleObject)) return false;
		
		SimpleObject other = (SimpleObject) obj;
		
		if(getId() != null ? !getId().equals(other.getId()) : other.getId() != null) return false;
		if(getKey() != null ? !getKey().equals(other.getKey()) : other.getKey() != null) return false;
		if(getValue() != null ? !getValue().equals(other.getValue()) : other.getValue() != null) return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "SimpleObject [id=" + id + ", key=" + key + ", value=" + value
				+ "]";
	}
	
}
