package waypalm.domain.entity

import waypalm.domain.entity.base.IntegerIdentityCU
import javax.persistence.Entity
import javax.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

Entity
Table(name = "SocialLink")
DynamicUpdate
public class SocialLink() : IntegerIdentityCU() {
    Column(length = 32, nullable = false)
    public var provider: String = ""
    Column(length = 50, nullable = false)
    public var providerUser: String = ""
    ManyToOne JoinColumn(nullable = false)
    public var profile: Profile = Profile()
    Column(length = 512, nullable = false)
    public var userUrl: String = ""
    Column(length = 128)
    public var userEmail: String? = null
    Column(length = 512)
    public var accessSecret: String? = null
    Column(length = 512)
    public var accessToken: String? = null
}