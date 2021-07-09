package com.everything.deckapp.viewModels

import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.everything.deckapp.data.models.UrlInfoRequest
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
    fun getUrlInfoRequest() {
        /** When requesting [UrlInfoRequest] given an URL */
        val URL = "Ans1OneFullDeckNoRepeated\n" +
                "https://api.jsonbin.io/b/60e3412155b7245a20d65cea \n"
        val urlInfoRequest = jsonViewModel.getUrlInfoRequest(URL)

        // Then result is not null, is an instance of UrlInfoRequest and answer should be 1 and url key 60e3412155b7245a20d65cea
        assertThat(urlInfoRequest, `is`(not(nullValue(UrlInfoRequest::class.java))))
        assertEquals(1.toString(), urlInfoRequest?.answer)
        assertEquals("60e3412155b7245a20d65cea", urlInfoRequest?.url)
    }

}