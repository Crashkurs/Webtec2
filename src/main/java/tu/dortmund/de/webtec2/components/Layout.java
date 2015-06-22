package tu.dortmund.de.webtec2.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.GlobalCtrl;

/**
 * Layout component for pages of application webtec2.
 */
@Import(stylesheet = {"context:layout/layout.css","context:layout/res/bootstrap-3.3.4-dist/js/bootstrap.min.js","context:layout/res/bootstrap-3.3.4-dist/css/bootstrap.min.css"})
public class Layout
{
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;
    
    @Inject
    GlobalCtrl globalCtrl;
    
    @Property
    User user;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "About", "Contact", "Home", "Register"};
    }
    
    public void setupRender() {
    	try{
    		this.user = globalCtrl.getCurrentUser();
    	}catch(Exception e) {
    		
    	}
    }
}
