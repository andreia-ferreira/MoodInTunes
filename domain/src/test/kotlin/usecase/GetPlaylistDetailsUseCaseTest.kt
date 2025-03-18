package usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song
import net.penguin.domain.repository.CollectionRepository
import net.penguin.domain.repository.SearchRepository
import net.penguin.domain.usecase.GetPlaylistDetailUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPlaylistDetailsUseCaseTest {
    private lateinit var getPlaylistDetailUseCase: GetPlaylistDetailUseCase
    private val searchRepository: SearchRepository = mockk()
    private val collectionRepository: CollectionRepository = mockk()
    private val savedPlaylist = PlaylistDetail(
        id = 666,
        name = "Playlist of the Beast",
        isSaved = false,
        description = ">:)",
        trackNumber = 666,
        duration = 666,
        picture = "",
        creator = "Lucifer",
        songList = listOf(
            Song(id = 1, title = "Tribute", artist = "Tenacious D", previewUrl = "")
        )
    )
    private val remotePlaylist = savedPlaylist.copy(isSaved = false)

    @Before
    fun setup() {
        getPlaylistDetailUseCase = GetPlaylistDetailUseCase(searchRepository, collectionRepository)
    }

    @Test
    fun `execute should call collectionRepository when isSaved is true`() = runBlocking {
        val requestParams = GetPlaylistDetailUseCase.RequestParams(savedPlaylist.id, isSaved = true)
        val expectedResult = Result.success(savedPlaylist)
        coEvery { collectionRepository.getPlaylistDetails(savedPlaylist.id) } returns expectedResult

        val result = getPlaylistDetailUseCase.execute(requestParams)

        coVerify { collectionRepository.getPlaylistDetails(savedPlaylist.id) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `execute should call searchRepository when isSaved is false`() = runBlocking {
        val requestParams = GetPlaylistDetailUseCase.RequestParams(remotePlaylist.id, isSaved = false)
        val expectedResult = Result.success(remotePlaylist)
        coEvery { searchRepository.getPlaylistDetails(remotePlaylist.id) } returns expectedResult

        val result = getPlaylistDetailUseCase.execute(requestParams)

        coVerify { searchRepository.getPlaylistDetails(remotePlaylist.id) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `execute should return error when collectionRepository fails`() = runBlocking {
        val requestParams = GetPlaylistDetailUseCase.RequestParams(1L, isSaved = true)
        val expectedError = Result.failure<PlaylistDetail>(Exception("Error"))
        coEvery { collectionRepository.getPlaylistDetails(1L) } returns expectedError

        val result = getPlaylistDetailUseCase.execute(requestParams)

        coVerify { collectionRepository.getPlaylistDetails(1L) }
        assertEquals(expectedError, result)
    }

    @Test
    fun `execute should return error when searchRepository fails`() = runBlocking {
        val requestParams = GetPlaylistDetailUseCase.RequestParams(1L, isSaved = false)
        val expectedError = Result.failure<PlaylistDetail>(Exception("Error"))
        coEvery { searchRepository.getPlaylistDetails(1L) } returns expectedError

        val result = getPlaylistDetailUseCase.execute(requestParams)

        coVerify { searchRepository.getPlaylistDetails(1L) }
        assertEquals(expectedError, result)
    }
}