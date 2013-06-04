package waypalm.site.exceptions

import java.io.Serializable

public class EntityNotFoundException(
        val target: Class<Any>,
        val id: Serializable,
        message: String = "object not found of type " + target.getSimpleName(),
        cause: Throwable? = null
)
: RecoverableException(message, cause)