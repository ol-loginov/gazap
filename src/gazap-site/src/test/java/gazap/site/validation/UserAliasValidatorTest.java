package gazap.site.validation;

import com.iserv2.test.ServiceTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserAliasValidatorTest extends ServiceTest<UserAliasValidator> {
    @Test
    public void valid() {
        assertTrue(service.isValid("gubka", null));
        assertTrue(service.isValid("gubka_oppa", null));
    }

    @Test
    public void invalid() {
        assertFalse(service.isValid("", null));
        assertFalse(service.isValid(" ", null));
        assertFalse(service.isValid("анатоле!", null));
        assertFalse(service.isValid("анатоле!", null));
        assertFalse(service.isValid(":8080/анатоле!", null));
        assertFalse(service.isValid("<script", null));
        assertFalse(service.isValid("Yapa_RU()ajax", null));
        assertFalse(service.isValid("Yapa_RU.ajax", null));
        assertFalse(service.isValid("Yapa_RU#ajax", null));
        assertFalse(service.isValid("Yapa_RU?ajax", null));
    }
}
