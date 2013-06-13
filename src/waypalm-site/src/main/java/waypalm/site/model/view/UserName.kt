package waypalm.site.model.view

import waypalm.domain.entity.ProfileSummary

public class UserName(
        val id: Int = 0,
        val name: String,
        val gravatar: String,
        val route: String,
        val summary: ProfileSummary,
        val me: Boolean
)
