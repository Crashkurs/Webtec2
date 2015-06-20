package tu.dortmund.de.webtec2.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Validate("required")
	@OneToOne
	private User fromUser;
	
	@Validate("required")
	@OneToOne
	private User toUser;
	
	@Validate("required")
	private Long timestamp;
	
	/**
	 * Default constructor for hibernate
	 */
	public Notification() {

	}
	public Notification(User fromUser, User toUser, Long timestamp){
		this.fromUser = Objects.requireNonNull(fromUser);
		this.toUser = toUser;
		this.timestamp = Objects.requireNonNull(timestamp);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
}
