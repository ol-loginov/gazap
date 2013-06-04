package waypalm.site.validation;

import com.iserv2.test.ServiceTest;
import com.iserv2.test.Values;
import org.junit.Test;
import org.mockito.Mock;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserEmailRegisteredValidatorTest extends ServiceTest<UserEmailRegisteredValidator> {
    private String testEmail;
    @Mock
    private UserRepository userRepository;

    @Override
    protected UserEmailRegisteredValidator createImpl() throws Exception {
        return new UserEmailRegisteredValidator(userRepository);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testEmail = Values.string(10);
    }

    @Test
    public void shouldBeRegistered_ButNotRegistered() {
        service.setShouldBeRegistered(true);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(null);
        assertFalse(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldBeRegistered_AndItRegistered() {
        service.setShouldBeRegistered(true);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(new Profile());
        assertTrue(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_AndNotRegistered() {
        service.setShouldBeRegistered(false);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(null);
        assertTrue(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_ButItRegistered() {
        service.setShouldBeRegistered(false);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(new Profile());
        assertFalse(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }
}
