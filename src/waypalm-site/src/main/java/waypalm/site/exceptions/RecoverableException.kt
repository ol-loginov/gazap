package waypalm.site.exceptions


public open class RecoverableException(
        message: String,
        cause: Throwable? = null
)
: Exception(message, cause)