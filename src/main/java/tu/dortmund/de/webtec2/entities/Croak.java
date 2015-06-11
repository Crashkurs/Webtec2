package tu.dortmund.de.webtec2.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Croak {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  
	@Validate("required")
	private String text;
	  
	@Validate("required")
	private User user;
	  
	@Validate("required")
	private Long timestamp;
}
