package com.everything.deckapp.data.models
import com.google.gson.annotations.SerializedName


class Card : ArrayList<CardItem>()

data class CardItem(
    @SerializedName("suit")
    val suit: String, // spades
    @SerializedName("value")
    val value: String // K
) {
    override fun toString(): String {
        return "CardItem(suit='$suit', value='$value')"
    }
}
