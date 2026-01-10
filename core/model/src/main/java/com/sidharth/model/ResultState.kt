package com.sidharth.model

sealed interface ResultState<out T> {

    object Empty : ResultState<Nothing>
    object Loading : ResultState<Nothing>
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val message: String) : ResultState<Nothing>
}


// ----------------------- Helper Functions -----------------------
val <T> ResultState<T>.isLoading: Boolean get() = this is ResultState.Loading
val <T> ResultState<T>.isSuccess: Boolean get() = this is ResultState.Success
val <T> ResultState<T>.isError: Boolean get() = this is ResultState.Error
val <T> ResultState<T>.isEmpty: Boolean get() = this is ResultState.Empty

fun <T> ResultState<T>.getOrNull(): T? = (this as? ResultState.Success)?.data
fun <T> ResultState<T>.errorMessage(): String? = (this as? ResultState.Error)?.message

inline fun <T, R> ResultState<T>.fold(
    onEmpty: () -> R,
    onLoading: () -> R,
    onSuccess: (T) -> R,
    onError: (String) -> R
): R = when (this) {
    is ResultState.Empty -> onEmpty()
    is ResultState.Loading -> onLoading()
    is ResultState.Success -> onSuccess(data)
    is ResultState.Error -> onError(message)
}

inline fun <T, R> ResultState<T>.map(transform: (T) -> R): ResultState<R> = when(this) {
    is ResultState.Empty -> ResultState.Empty
    is ResultState.Loading -> ResultState.Loading
    is ResultState.Success -> ResultState.Success(transform(data))
    is ResultState.Error -> ResultState.Error(message)
}