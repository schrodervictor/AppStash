package com.comsysto.shop.ui.panel.base;

import com.comsysto.shop.service.authentication.api.AuthenticationService;
import com.comsysto.shop.service.authentication.model.LoginInfo;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author zutherb
 */
public abstract class AbstractPizzaShopBasePanel extends Panel {
    private static final long serialVersionUID = -2274645877865227328L;

    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;

    public AbstractPizzaShopBasePanel(String id) {
        super(id);
    }

    public AbstractPizzaShopBasePanel(String id, IModel<?> model) {
        super(id, model);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public boolean isAuthorized() {
        return getAuthenticationService().isAuthorized();
    }

    public boolean authenticate(LoginInfo loginInfo) {
        return authenticationService.authenticate(loginInfo);
    }
}
