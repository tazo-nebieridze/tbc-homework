import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homeworkstbc.client.RetrofitClient

class UserPagingSource : PagingSource<Int, User>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 1
        return try {
            val response = RetrofitClient.userService.fetchUsers(currentPage)
            if (response.isSuccessful) {
                val usersDto = response.body()
                Log.d("homeFragment",currentPage.toString())
                LoadResult.Page(
                    data = usersDto?.data ?: emptyList(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage == usersDto?.totalPages) null else currentPage + 1
                )

            } else {
                LoadResult.Error(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            val closestPage = state.closestPageToPosition(position)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}
