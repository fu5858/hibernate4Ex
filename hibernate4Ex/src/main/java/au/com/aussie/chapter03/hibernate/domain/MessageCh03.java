package au.com.aussie.chapter03.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageCh03 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long ID;
	
	@Column(nullable=false)
	String text;

	public MessageCh03() {}

	public MessageCh03(String text) {
		super();
		this.text = text;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Message [ID=" + ID + ", text=" + text + "]";
	}
	
}
