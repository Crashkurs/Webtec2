package tu.dortmund.de.webtec2.services;


/**
 * Diese Klasse erstellt einen Account mit Namen "admin" und Passwort "admin", falls noch nicht vorhanden.
 * 
 * @author Mats
 *
 */
public class TestService {

	private RegisterCtrl registerCtrl;

	public TestService(RegisterCtrl registerCtrl) {
		this.registerCtrl = registerCtrl;
		initializeAdminAccount();
	}

	private void initializeAdminAccount() {
		try{
			registerCtrl.createNewUser("admin", "admin", "admin");
			registerCtrl.createNewUser("test", "test", "test");
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
