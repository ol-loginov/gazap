package waypalm.site.services

import waypalm.domain.entity.Profile
import waypalm.domain.entity.SocialLink
import waypalm.common.web.model.SocialProfile
import org.springframework.social.connect.ConnectionKey
import org.springframework.security.authentication.encoding.PasswordEncoder
import javax.inject.Named
import waypalm.common.web.security.PasswordSalter
import waypalm.domain.dao.UserRepository
import javax.inject.Inject
import waypalm.common.web.security.AppSecurity
import com.iserv2.commons.lang.IservHashUtil
import org.springframework.transaction.annotation.Transactional
import waypalm.domain.entity.ProfileSummary
import org.springframework.util.StringUtils

public trait UserService{
    fun createSocialConnection(profile: Profile?, key: ConnectionKey, socialProfile: SocialProfile): SocialLink
    fun createUser(email: String, password: String): Profile
    fun findUserByAliasOrId(aliasOrId: String): Profile?
}

Named
public class UserServiceImpl [Inject](
        var userRepository: UserRepository,
        AppSecurity var passwordEncoder: PasswordEncoder,
        AppSecurity var passwordSalter: PasswordSalter
)
: UserService
{
    fun createUserWithRandomPassword(email: String?): Profile {
        return createUser(IservHashUtil.isNull(email, "")!!, IservHashUtil.newUuid()!!)
    }

    Transactional
    public override fun createUser(email: String, password: String): Profile {
        val user = Profile()
        user.setPasswordSalt(IservHashUtil.md5("" + user.getCreatedAt()!!.getTime() + System.nanoTime(), true))
        user.setPassword(passwordEncoder.encodePassword(password, passwordSalter.getSalt(user.getPasswordSalt())))
        user.setEmail(email)
        user.setDisplayName("")
        userRepository.create(user)

        val userSummary = ProfileSummary(user)
        userRepository.create(userSummary)

        return user;
    }

    Transactional
    public override fun createSocialConnection(user: Profile?, key: ConnectionKey, social: SocialProfile): SocialLink {
        val socialLink = SocialLink()
        socialLink.provider = key.getProviderId()!!
        socialLink.providerUser = key.getProviderUserId()!!
        socialLink.userUrl = social.url
        socialLink.userEmail = social.email
        socialLink.profile = if(user != null) user else createUserWithRandomPassword(social.email)

        userRepository.create(socialLink);
        return socialLink;
    }

    public override fun findUserByAliasOrId(aliasOrId: String): Profile? {
        if (aliasOrId.length() == 0) {
            return null;
        }
        if (Character.isDigit(aliasOrId.charAt(0)))
            return userRepository.getProfile(Integer.parseInt(aliasOrId))
        return userRepository.findProfileByAlias(aliasOrId);
    }

}