package tu.dortmund.de.webtec2.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.tapestry5.hibernate.HibernateSessionManager;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.User;

public class HomeCtrl {

	private HibernateSessionManager hibernateSessionManager;
	private GlobalCtrl globalctrl;

	public HomeCtrl(HibernateSessionManager hibernateSessionManager,
			GlobalCtrl globalctrl) {
		this.globalctrl = globalctrl;
		this.hibernateSessionManager = hibernateSessionManager;
	}

	/**
	 * Returns a new croak containing the given text
	 * 
	 * @param text the text from the croak
	 * @return a new croak with the given text
	 */
	public Croak createCroak(String text) {
		return new Croak(globalctrl.getCurrentUser(), text);
	}

	/**
	 * Returns a list of all croaks by followed users
	 * 
	 * @return list of croaks by followed users
	 */
	public List<Croak> loadFollowedCroaks() {
		List<User> user = new List<User>(){

			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public Iterator<User> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}

			public <T> T[] toArray(T[] a) {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean add(User e) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean containsAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(Collection<? extends User> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(int index, Collection<? extends User> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean removeAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean retainAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public void clear() {
				// TODO Auto-generated method stub
				
			}

			public User get(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public User set(int index, User element) {
				// TODO Auto-generated method stub
				return null;
			}

			public void add(int index, User element) {
				// TODO Auto-generated method stub
				
			}

			public User remove(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public int indexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public int lastIndexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public ListIterator<User> listIterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public ListIterator<User> listIterator(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public List<User> subList(int fromIndex, int toIndex) {
				// TODO Auto-generated method stub
				return null;
			}};
		List<Croak> result = new List<Croak>() {

			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public Iterator<Croak> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}

			public <T> T[] toArray(T[] a) {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean add(Croak e) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean containsAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(Collection<? extends Croak> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(int index, Collection<? extends Croak> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean removeAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean retainAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public void clear() {
				// TODO Auto-generated method stub

			}

			public Croak get(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public Croak set(int index, Croak element) {
				// TODO Auto-generated method stub
				return null;
			}

			public void add(int index, Croak element) {
				// TODO Auto-generated method stub

			}

			public Croak remove(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public int indexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public int lastIndexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public ListIterator<Croak> listIterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public ListIterator<Croak> listIterator(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public List<Croak> subList(int fromIndex, int toIndex) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		user.addAll(globalctrl.loadFollower());
		try {
			for (int i = 0; i < user.size(); i++) 
				result.addAll(globalctrl.loadCroaks(user.get(i).getName()));		
		} catch (Exception ex) {
			
		}
		
		return result;
	}
	
	public List<Croak> loadOnwCroaks() {
		List<Croak> result = new List<Croak>() {

			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public Iterator<Croak> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}

			public <T> T[] toArray(T[] a) {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean add(Croak e) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean containsAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(Collection<? extends Croak> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean addAll(int index, Collection<? extends Croak> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean removeAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean retainAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}

			public void clear() {
				// TODO Auto-generated method stub

			}

			public Croak get(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public Croak set(int index, Croak element) {
				// TODO Auto-generated method stub
				return null;
			}

			public void add(int index, Croak element) {
				// TODO Auto-generated method stub

			}

			public Croak remove(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public int indexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public int lastIndexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			public ListIterator<Croak> listIterator() {
				// TODO Auto-generated method stub
				return null;
			}

			public ListIterator<Croak> listIterator(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public List<Croak> subList(int fromIndex, int toIndex) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		try {
			result.addAll(globalctrl.loadCroaks());	
		} catch (Exception ex) {
			
		}
		
		return result;
	}
}
