package com.example.challegeskilllevenling

import android.app.SearchManager.QUERY
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challegeskilllevenling.data.database.service.ApiClient
import com.example.challegeskilllevenling.data.database.service.MercadoApi
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.ApiStatus
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
class ApiClientTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @RelaxedMockK
    private lateinit var api: MercadoApi
    private lateinit var repository: ApiClient


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ApiClient(api)
    }

    @Test
    fun `when getHighlight  is called itemResponse is notEmpty status is success  `() = runTest {
        coEvery {
            repository.getHighlight(QUERY)
        } returns itemResponseList()
        assertEquals(statusSuccess(), repository.status.value)
        assert(itemResponseList().isNotEmpty())
    }

    @Test
    fun `when getHighlight  is called itemResponse  is empty `() = runTest {
        coEvery {
            repository.getHighlight(QUERY)
        } returns itemResponseListError()
        assert(itemResponseListError().isEmpty())
    }


    //Api Status
    private fun statusSuccess(): ApiStatus = ApiStatus.SUCCESS

    private fun itemResponseList(): List<ItemResponse> = listOf(ItemResponse(body))
    private val body: Item = Item("1", "Item", listString(), 100.0, "image")
    private fun listString(): List<String> = listOf("string1", "string2")
    private fun itemResponseListError(): List<ItemResponse> = emptyList()



}