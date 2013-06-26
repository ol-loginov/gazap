package waypalm.site.services

import waypalm.site.model.view.UserName
import waypalm.domain.entity.Profile
import javax.inject.Named
import waypalm.domain.dao.WorldRepository
import waypalm.domain.dao.UserRepository
import waypalm.common.services.FormatService
import javax.inject.Inject
import org.springframework.transaction.annotation.Transactional
import waypalm.common.util.GravatarHelper

public trait ModelViewer {
    fun createUserName(profile: Profile): UserName
}

public enum class ModelViewerSet {
    ADD_AUTHOR_DETAILS
}

Named
public class ModelViewerImpl [Inject](
        val formatService: FormatService,
        val userRepository: UserRepository,
        val worldRepository: WorldRepository,
        val auth: UserAccess
)
: ModelViewer {
    Transactional
    public override fun createUserName(profile: Profile): UserName {
        return UserName(
                id = profile.getId()!!,
                name = profile.getDisplayName()!!,
                gravatar = GravatarHelper.hashOrDefault(profile.getEmail())!!,
                route = "/u/" + profile.getId(),
                summary = userRepository.getProfileSummary(profile)!!,
                me = profile.isSame(auth.loadCurrentProfile())
        )
    }
}


