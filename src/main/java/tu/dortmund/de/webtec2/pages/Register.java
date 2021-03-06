package tu.dortmund.de.webtec2.pages;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import tu.dortmund.de.webtec2.services.RegisterCtrl;

@RequiresGuest
public class Register {

	@Property
	@Validate("required")
	String userName = "";
	
	@Property
	@Validate("required")
	String password = "";
	
	@Property
	@Validate("required")
	String password2 = "";
	
	@Inject
	RegisterCtrl registerCtrl;
    @Inject
    private AlertManager alertManager;
    
    @InjectComponent
    Zone registerForm;
    
    @InjectComponent
    private Form registerUser;
    
	@Inject
	PageRenderLinkSource pageRender;
	
    Object onActivate() {
		if(SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered()) {
			Link link = pageRender.createPageRenderLink(Home.class);
			return link;
		}
		return null;
	}
    
    @CommitAfter
	Object onActionFromRegisterUser() {
		try{
			registerCtrl.createNewUser(userName, password, password2);
		} catch(IllegalArgumentException ex) {
			registerUser.recordError(ex.getMessage());
			return registerForm;
		}
		return Login.class;
	}
}
