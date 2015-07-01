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
public class Croak {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Property
	private Long id;
	  
	@Validate("required")
	@OneToOne
	@Property
	private User user;
	
	@Validate("minlength=1,maxlength=256")
	@Property
	private String text;

	@Validate("required")
	@Property
	private Date time;
	
	/**
	 * Default constructor for hibernate
	 */
	public Croak() {
		
	}
	
	public Croak(User user, String text, Date time){
		this.user = Objects.requireNonNull(user);
		this.text = Objects.requireNonNull(text);
		this.time = time;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getTimeStr() {
		String format = "yyyy-MM-dd hh:mm";
		if(new Date().getDay() == time.getDay()) {
			format = "hh:mm";
		}
		return new SimpleDateFormat(format).format(time);
	}
}
