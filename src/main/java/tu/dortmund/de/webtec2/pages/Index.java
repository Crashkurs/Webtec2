package tu.dortmund.de.webtec2.pages;

import java.util.Date;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import tu.dortmund.de.webtec2.services.GlobalCtrl;
import tu.dortmund.de.webtec2.services.TestService;

/**
 * Start page of application webtec2.
 */
public class Index
{
    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @InjectComponent
    private Zone zone;

    @Persist
    @Property
    private int clickCount;

    @Inject
    private AlertManager alertManager;
    
    @Inject
    GlobalCtrl globalController;
    
    @Inject
    HibernateSessionManager hibernateSessionManager;
    
    @Inject
    TestService test;

    public Date getCurrentTime()
    {
        return new Date();
    }

    void onActionFromIncrement()
    {
        alertManager.info("Increment clicked");
        
        alertManager.info(globalController.loadCroaks().toString());
        alertManager.info(globalController.getNotifications().toString());
        alertManager.info(globalController.loadFollower().toString());

        clickCount++;
    }

    @CommitAfter
    Object onActionFromIncrementAjax()
    {
        clickCount++;

        alertManager.info("Increment (via Ajax) clicked");

        return zone;
    }
}
