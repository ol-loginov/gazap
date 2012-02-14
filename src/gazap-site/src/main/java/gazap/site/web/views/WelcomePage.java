package gazap.site.web.views;

import gazap.site.web.views.annotations.GlobalMenu;
import gazap.site.web.views.annotations.GlobalMenuSection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WelcomePage")
@XmlAccessorType(XmlAccessType.NONE)
@GlobalMenu(GlobalMenuSection.MENU)
public class WelcomePage extends GazapPage {
}
