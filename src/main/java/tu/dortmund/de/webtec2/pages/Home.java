package tu.dortmund.de.webtec2.pages;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.services.HomeCtrl;

public class Home {

	@Property
	private List<Croak> croaks;

	@Property
	private Croak croak;

	@Property
	private String croakInput;

//	@Component
//	private BeanEditForm errorform;
	
	@Inject
	private HomeCtrl homectrl;

	public void onActionFromRefresh() {
		try {
			croaks = homectrl.loadFollowedCroaks();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	public void onActionFromMyProfile() {
	
	}
	
	public void onSelectedFromPost(String s) {
		if (croaks == null) {
			croaks = new List<Croak>() {

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
		}
		if (!s.equals("") && !s.trim().isEmpty()) {
			System.out.println(croaks.size());
			croaks.add(homectrl.createCroak(s));
		}
	}
}
