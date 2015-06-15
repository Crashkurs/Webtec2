package tu.dortmund.de.webtec2.entities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class User {

	@Id
	@Validate("required")
	@Property
	private String name;
	
	@Validate("required")
	@Property
	private String password;
	
	@OneToMany
	@Property
	@Fetch(value = FetchMode.JOIN)
	private List<User> following;
	
	@OneToMany
	@Property
	@Fetch(value = FetchMode.JOIN)
	private List<User> followers;
	
	@ElementCollection
	@Property
	private Collection<String> permissions;
	
	//private List<Notification> notifications;
	
	/**
	 * Default constructor for hibernate
	 */
	public User() {
		
	}
	
	public User(String name, String password) {
		this.name = Objects.requireNonNull(name);
		this.password = Objects.requireNonNull(password);
		this.following = new LinkedList<User>();
		this.followers = new LinkedList<User>();
		permissions = new LinkedList<String>();
	}
	
	public User(String name, String password, Collection<String> permissions) {
		this(name, password);
		this.permissions = permissions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public List<User> getFollowing() {
		return following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public Collection<String> getPermissions() {
		return permissions;
	}
	
	
}
