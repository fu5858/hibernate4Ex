package au.com.aussie.chapter09;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Software extends Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 47414923941731639L;
	@Column
	@NotNull
	String version;

	public Software() {}

	public Software(Supplier supplier, String name, String description, double price, String version) {
		this.supplier = supplier;
		this.name = name;
		this.description = description;
		this.price = price;
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Software other = (Software) obj;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Software [version=" + version + "]";
	}
	
	
}
