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
    public void setUp() {
        super.setUp();
        testEmail = Values.string(10);
        service.userRepository = userRepository;
    }

    @Test
    public void shouldBeRegistered_ButNotRegistered() {
        service.initialize(true);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(null);
        assertFalse(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldBeRegistered_AndItRegistered() {
        service.initialize(true);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(new Profile());
        assertTrue(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_AndNotRegistered() {
        service.initialize(false);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(null);
        assertTrue(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }

    @Test
    public void shouldNotBeRegistered_ButItRegistered() {
        service.initialize(false);
        when(userRepository.findProfileByEmail(testEmail)).thenReturn(new Profile());
        assertFalse(service.isValid(testEmail, null));
        verify(userRepository).findProfileByEmail(testEmail);
    }
}
