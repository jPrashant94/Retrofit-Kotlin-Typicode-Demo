package dj.song.mixer.demoapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dj.song.mixer.demoapp.model.PostDTO

class PostsPagingSource(private val repository: UsersListRepository,private  val userId : Int) : PagingSource<Int, PostDTO>() { // <Key type (Page number), Data type>
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDTO> {
        val currentPage = params.key ?: 1
        return try {
            // params.loadSize is determined by the PagingConfig we will set up next
            val posts = repository.getUserPostPaged(userId, currentPage, params.loadSize)

            LoadResult.Page(
                data = posts,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                // If the API returns an empty list, we've reached the end, so nextKey is null
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            // Paging 3 catches errors here and pushes them to the UI automatically!
            LoadResult.Error(e)
        }
    }
    // This is required boilerplate for Paging 3 to handle list invalidation/refreshing
    override fun getRefreshKey(state: PagingState<Int, PostDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}