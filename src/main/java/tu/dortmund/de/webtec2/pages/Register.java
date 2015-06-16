package tu.dortmund.de.webtec2.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import tu.dortmund.de.webtec2.services.RegisterCtrl;

public class Register {

	@Property
	String userName = "";
	
	@Property
	String password = "";
	
	@Property
	String password2 = "";
	
	@Inject
	RegisterCtrl registerCtrl;
    @Inject
    private AlertManager alertManager;
	
	Object onActionFromRegisterUser() {
		try{
			registerCtrl.createNewUser(userName, password, password2);
		} catch(IllegalArgumentException ex) {
			alertManager.error("User already exists or passwords do not match");
			return Register.class;
		}
		return Index.class;
	}
}
