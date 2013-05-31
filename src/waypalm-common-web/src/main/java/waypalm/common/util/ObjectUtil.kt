package waypalm.common.util

import org.springframework.util.StringUtils

fun String?.ifEmpty(default: String): String {
    return if (StringUtils.hasLength(this)) this!! else default
}
fun String?.ifNull(default: String): String {
    return if (this != null) this else default
}

fun <T, O>  T.select(selector: (it: T)->O): O {
    return selector(this)
}

fun <T> List<T>.firstSure(fallback: T): T {
    return if(this.isEmpty()) fallback  else this.get(0)
}

fun <T> Array<T>.firstSure(fallback: T): T {
    return if(this.size == 0) fallback else this[0]
}