package tu.dortmund.de.webtec2.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.tapestry5.annotations.Property;
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
	private Date time;
	
	/**
	 * Default constructor for hibernate
	 */
	public Notification() {

	}
	
	public Notification(User fromUser, User toUser, Date time){
		this.fromUser = Objects.requireNonNull(fromUser);
		this.toUser = toUser;
		this.time = Objects.requireNonNull(time);
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
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getTimeStr() {
		String format = "yyyy-MM-dd hh:mm";
		if(new Date().getDay() == time.getDay()) {
			format = "hh:mm";
		}
		return new SimpleDateFormat(format).format(time);
	}
	
	public User getToUser() {
		return toUser;
	}
	
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
}
