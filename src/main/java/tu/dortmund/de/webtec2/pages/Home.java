package tu.dortmund.de.webtec2.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;

import tu.dortmund.de.webtec2.entities.Croak;

public class Home {
	
	@Property
	private List<Croak> croaks;
	
	@Property
	private Croak croak;
	
}
