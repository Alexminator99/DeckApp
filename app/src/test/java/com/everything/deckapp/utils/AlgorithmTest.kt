package com.everything.deckapp.utils

import com.everything.deckapp.data.models.Card
import com.everything.deckapp.data.models.CardItem
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

// NOTE: All the json are valid, algorithm does not validate inputs, cause they are already validated
class AlgorithmTest {

    @Test
    fun runAlgorithm_returnsZero() {
        // Given a json of data
        val json =
            "[{\"suit\": \"spades\", \"value\": \"6\"}, {\"suit\": \"diamonds\", \"value\": \"7\"}, {\"suit\": \"hearts\", \"value\": \"7\"}, {\"suit\": \"spades\", \"value\": \"3\"}, {\"suit\": \"diamonds\", \"value\": \"J\"}, {\"suit\": \"hearts\", \"value\": \"A\"}, {\"suit\": \"spades\", \"value\": \"K\"}, {\"suit\": \"spades\", \"value\": \"4\"}, {\"suit\": \"diamonds\", \"value\": \"K\"}, {\"suit\": \"clubs\", \"value\": \"10\"}, {\"suit\": \"diamonds\", \"value\": \"9\"}, {\"suit\": \"clubs\", \"value\": \"6\"}, {\"suit\": \"spades\", \"value\": \"J\"}, {\"suit\": \"hearts\", \"value\": \"8\"}, {\"suit\": \"clubs\", \"value\": \"3\"}, {\"suit\": \"hearts\", \"value\": \"10\"}, {\"suit\": \"spades\", \"value\": \"A\"}, {\"suit\": \"diamonds\", \"value\": \"A\"}, {\"suit\": \"diamonds\", \"value\": \"2\"}, {\"suit\": \"hearts\", \"value\": \"K\"}, {\"suit\": \"clubs\", \"value\": \"4\"}, {\"suit\": \"hearts\", \"value\": \"Q\"}, {\"suit\": \"spades\", \"value\": \"Q\"}, {\"suit\": \"clubs\", \"value\": \"K\"}, {\"suit\": \"diamonds\", \"value\": \"3\"}, {\"suit\": \"diamonds\", \"value\": \"5\"}, {\"suit\": \"hearts\", \"value\": \"5\"}, {\"suit\": \"hearts\", \"value\": \"3\"}, {\"suit\": \"spades\", \"value\": \"9\"}, {\"suit\": \"diamonds\", \"value\": \"10\"}, {\"suit\": \"clubs\", \"value\": \"2\"}]"

        // When algorithm is computed
        val listOfCads = ArrayList<CardItem>()
        listOfCads.addAll(Gson().fromJson(json, Array<CardItem>::class.java))

        val answer = Algorithm.getAllDecksWithCompleteSize(listOfCads)

        // Then the result for the given json is 0
        assertEquals(0, answer)
    }

    @Test
    fun runAlgorithm_returnsOne() {
        // Given a json of data
        val json =
            "[\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"A\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"2\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"3\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"4\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"5\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"6\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"7\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"8\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"9\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"10\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"J\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"Q\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"clubs\",\n" +
                    "    \"value\": \"K\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"A\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"2\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"3\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"4\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"5\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"6\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"7\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"8\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"9\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"10\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"J\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"Q\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"diamonds\",\n" +
                    "    \"value\": \"K\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"A\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"2\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"3\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"4\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"5\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"6\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"7\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"8\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"9\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"10\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"J\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"Q\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"hearts\",\n" +
                    "    \"value\": \"K\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"A\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"2\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"3\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"4\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"5\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"6\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"7\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"8\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"9\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"10\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"J\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"Q\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"suit\": \"spades\",\n" +
                    "    \"value\": \"K\"\n" +
                    "  }\n" +
                    "]"

        // When algorithm is computed
        val listOfCads = ArrayList<CardItem>()
        listOfCads.addAll(Gson().fromJson(json, Array<CardItem>::class.java))

        val answer = Algorithm.getAllDecksWithCompleteSize(listOfCads)

        // Then the result for the given json is 0
        assertEquals(1, answer)
    }
}