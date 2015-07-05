package tu.dortmund.de.webtec2.entities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.tapestry5.beaneditor.Validate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class User {

	@Id
	@Validate("required")
	private String name;
	
	@Validate("required")
	private String password;
	
	@ManyToMany
	@Fetch(value = FetchMode.JOIN)
	@Column(name = "Following")
	private List<User> following;
	
	@ManyToMany(mappedBy = "following")
	@Fetch(value = FetchMode.JOIN)
	@Column(name = "Followers")
	private List<User> followers;
	
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@Column(name = "Notifications")
	private List<Notification> notes;
	
	@ElementCollection
	private Collection<String> permissions;
	
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
		this.notes = new LinkedList<Notification>();
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

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getFollowing() {
		return following;
	}

	public List<User> getFollowers() {
		return followers;
	}
	
	public void addFollower(User user){
		followers.add(user);
	}
	
	public List<Notification> getNotifications() {
		return notes;
	}

	public Collection<String> getPermissions() {
		return permissions;
	}
	
	
}
