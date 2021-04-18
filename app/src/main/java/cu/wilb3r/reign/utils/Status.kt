package cu.wilb3r.reign.utils

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    fun isSuccessful() = this == SUCCESS
    fun isError() = this == ERROR
    fun isLoading() = this == LOADING

}