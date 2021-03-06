package tu.dortmund.de.webtec2.entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.tapestry5.beaneditor.Validate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class User {

	@Id
	@Validate("required")
	private String name;
	
	@Validate("required")
	private String password;
	
	@ManyToMany(mappedBy = "followers")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> following;

	@ManyToMany
	@JoinTable(name="Followers")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> followers;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Notification> notes;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> roles;
	
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
		roles = new HashSet<String>();
	}
	
	public User(String name, String password, Set<String> permissions) {
		this(name, password);
		this.roles = permissions;
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

	public Set<String> getRoles() {
		return roles;
	}
	
	
}
