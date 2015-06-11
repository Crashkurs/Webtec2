package tu.dortmund.de.webtec2.entities;

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
	
	@Validate("minlength=1")
	@Property
	private String text;

	@Validate("required")
	@Property
	private Long timestamp;
	
	public Croak(User user, String text){
		this.user = Objects.requireNonNull(user);
		this.text = Objects.requireNonNull(text);
	}
}
