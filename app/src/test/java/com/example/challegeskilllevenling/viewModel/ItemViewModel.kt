package com.example.challegeskilllevenling.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challegeskilllevenling.MainCoroutinesRule
import com.example.challegeskilllevenling.data.database.service.ApiClient
import com.example.challegeskilllevenling.data.database.service.MercadoApi
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.ApiStatus
import com.example.challegeskilllevenling.ui.viewModels.ItemViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ItemViewModel {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @RelaxedMockK
    private lateinit var api: MercadoApi
    private lateinit var repository: ApiClient
    private lateinit var viewModel: ItemViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ApiClient(api)
        viewModel = ItemViewModel(repository)
    }

    @Test
    fun `when getCategories is called response is SUCCESS and searchItem isn't empty`() = runTest {
        coEvery {
            repository.getCategories(QUERY)
        } returns itemResponseList()
        viewModel.getCategories(QUERY)
        assertEquals(statusSuccess(), viewModel.status.value)
        assertEquals(itemResponseList(), viewModel.searchItem.value)
    }

    @Test
    fun `when getCategories is called response is error  and newsList is empty`() = runTest {
        coEvery {
            repository.getCategories(QUERY)
        } returns itemResponseListError()
        viewModel.getCategories(QUERY)
        assertEquals(statusError(), viewModel.status.value)
        assert(viewModel.searchItem.value.isNullOrEmpty())
    }

    private val QUERY = "libros"

    //Item
    private fun listString(): List<String> = listOf("string1", "string2")
    private fun itemResponseList(): List<ItemResponse> = listOf(ItemResponse(body))
    private fun itemResponseListError(): List<ItemResponse> = emptyList()
    private val body: Item = Item("1", "Item", listString(), 100.0, "image")

    //Api Status
    private fun statusSuccess(): ApiStatus = ApiStatus.SUCCESS
    private fun statusError(): ApiStatus = ApiStatus.FAILURE

}