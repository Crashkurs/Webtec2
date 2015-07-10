package tu.dortmund.de.webtec2.components;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.PageRenderLinkSource;

import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.pages.Login;
import tu.dortmund.de.webtec2.pages.Profile;
import tu.dortmund.de.webtec2.services.GlobalCtrl;

/**
 * Layout component for pages of application webtec2.
 */
@Import(stylesheet = {	"context:layout/res/bootstrap-3.3.4-dist/css/bootstrap.min.css", 
						"context:layout/custom.css"},
		library ={		"context:layout/res/jquery.min.js",
						"context:layout/res/bootstrap-3.3.4-dist/js/bootstrap.min.js"})
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
    private List<User> users;
    
    @Property
    private String searchFieldText;

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
    
    @Inject
    PageRenderLinkSource pageRenderLink;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }
    
    public void setupRender() {
    	if(SecurityUtils.getSubject().isAuthenticated())
    		this.user = globalCtrl.getCurrentUser();
    }
    
    @RequiresAuthentication
    Object onActionFromLogout() {
    	globalCtrl.logout();
    	return Login.class;
    }
    
    Object onSubmitFromSearchForm(@RequestParameter(value = "searchField") String searchForm) {
    	Link profile = pageRenderLink.createPageRenderLinkWithContext(Profile.class, searchForm);
    	return profile;
    }
    
    String[] onProvideCompletionsFromSearchField(String partial) {
    	List<User> users = globalCtrl.findUserByRegex(partial);
    	String[] names = new String[users.size()];
    	for(int x = 0; x < users.size(); x++) {
    		names[x] = users.get(x).getName();
    	}
    	return names;
    }
    
    
}
