package tu.dortmund.de.webtec2.entities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class User {

	@Id
	@Validate("required")
	private String name;
	
	@Validate("required")
	private String password;
	
	@OneToMany
	private List<User> following;
	
	@OneToMany
	private List<User> followers;
	
	@ElementCollection
	private Collection<String> permissions;
	
	//private List<Notification> notifications;
	
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
