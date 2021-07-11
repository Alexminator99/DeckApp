package com.everything.deckapp.viewModels

import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.everything.deckapp.data.models.UrlInfoRequest
import com.everything.deckapp.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JsonViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var jsonViewModel: JsonViewModel

    @Before
    fun setupViewModel() {
        jsonViewModel = JsonViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun getUrlInfoRequest_returnsNotNullOneString() {
        /** When requesting [UrlInfoRequest] given an URL */
        val URL = "Ans1OneFullDeckNoRepeated\n" +
                "https://api.jsonbin.io/b/60e3412155b7245a20d65cea \n"
        val urlInfoRequest = jsonViewModel.getUrlInfoRequest(URL)

        // Then result is not null, is an instance of UrlInfoRequest and answer should be 1 and url key 60e3412155b7245a20d65cea
        assertThat(urlInfoRequest, `is`(not(nullValue(UrlInfoRequest::class.java))))
        assertEquals(1.toString(), urlInfoRequest?.answer)
        assertEquals("60e3412155b7245a20d65cea", urlInfoRequest?.url)
    }

    @Test
    fun getUrlInfoRequest_returnsNotNull5StringString() {
        /** When requesting [UrlInfoRequest] given an URL */
        val URL = "Ans5 (private)(\"secret-key: \$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG\")\n" +
                "https://api.jsonbin.io/b/60e342b39328b059d7b7801e"
        val urlInfoRequest = jsonViewModel.getUrlInfoRequest(URL)

        // Then result is not null, is an instance of UrlInfoRequest and answer should be 5 and url key 60e342b39328b059d7b7801e
        assertThat(urlInfoRequest, `is`(not(nullValue(UrlInfoRequest::class.java))))
        assertEquals(5.toString(), urlInfoRequest?.answer)
        assertEquals("60e342b39328b059d7b7801e", urlInfoRequest?.url)
        assertEquals("\$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG", urlInfoRequest?.secretKey)
    }

    @Test
    fun getUrlInfoRequest_returnsNull() {
        /** When requesting [UrlInfoRequest] given an URL */
        val URL = "Ans5 (private)(\"secret-key: \$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG\")\n" +
                "https://api.jsonbin.io//60e342b39328b059d7b7801e"
        val urlInfoRequest = jsonViewModel.getUrlInfoRequest(URL)
        print(urlInfoRequest)
        // Then result is not null, is an instance of UrlInfoRequest and answer should be 5 and url key 60e342b39328b059d7b7801e
        assertThat(urlInfoRequest, `is`((nullValue(UrlInfoRequest::class.java))))
    }
}