package gazap.site.validation;

import com.iserv2.test.ServiceTest;
import com.iserv2.test.Values;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserEmailRegisteredValidatorTest extends ServiceTest<UserEmailRegisteredValidator> {
    private String testEmail;
    @Mock
    private UserProfileDao profileDao;

    @Override
    public void setUp() {
        super.setUp();
        testEmail = Values.string(10);
        service.userProfileDao = profileDao;
    }

    @Test
    public void shouldBeRegistered_ButNotRegistered() {
        service.initialize(true);
        when(profileDao.findUserByEmail(testEmail)).thenReturn(null);
        assertFalse(service.isValid(testEmail, null));
        verify(profileDao).findUserByEmail(testEmail);
    }

    @Test
    public void shouldBeRegistered_AndItRegistered() {
        service.initialize(true);
        when(profileDao.findUserByEmail(testEmail)).thenReturn(new UserProfile());
        assertTrue(service.isValid(testEmail, null));
        verify(profileDao).findUserByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_AndNotRegistered() {
        service.initialize(false);
        when(profileDao.findUserByEmail(testEmail)).thenReturn(null);
        assertTrue(service.isValid(testEmail, null));
        verify(profileDao).findUserByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_ButItRegistered() {
        service.initialize(false);
        when(profileDao.findUserByEmail(testEmail)).thenReturn(new UserProfile());
        assertFalse(service.isValid(testEmail, null));
        verify(profileDao).findUserByEmail(testEmail);
    }
}