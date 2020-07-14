package bohdan.varchenko.domain

fun <T> T.wrap() = DataWrapper.from(this)

data class DataWrapper<T>(
    val data: T?,
    val error: Throwable?
) {

    fun isEmpty(): Boolean = data == null

    fun isNotEmpty(): Boolean = data != null

    companion object {
        fun <T> from(data: T): DataWrapper<T> = DataWrapper(data, null)

        fun <T> error(error: Throwable): DataWrapper<T> = DataWrapper(null, error)
    }
}