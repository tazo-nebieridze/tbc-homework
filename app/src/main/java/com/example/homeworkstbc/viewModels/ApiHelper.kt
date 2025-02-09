import retrofit2.Response

object ApiHelper {
    suspend fun <T> handleHttpRequest(httpCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = httpCall.invoke()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"
                Resource.Error(errorMessage)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error, please try again")
        }
    }
}
