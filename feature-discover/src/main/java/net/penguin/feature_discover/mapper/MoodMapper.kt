package net.penguin.feature_discover.mapper

import net.penguin.domain.entity.Mood
import net.penguin.feature_discover.R
import net.penguin.feature_discover.model.DiscoverScreenState

object MoodMapper {
    fun map(mood: Mood): DiscoverScreenState.MoodItem {
        return DiscoverScreenState.MoodItem(
            mood = mood,
            titleRes = when (mood) {
                Mood.CHILL -> R.string.mood_chill
                Mood.HAPPY -> R.string.mood_happy
                Mood.PARTY -> R.string.mood_party
                Mood.FUN -> R.string.mood_fun
                Mood.WORKOUT -> R.string.mood_workout
                Mood.FOCUSED -> R.string.mood_focused
                Mood.EPIC -> R.string.mood_epic
                Mood.ROMANTIC -> R.string.mood_romantic
                Mood.DREAMY -> R.string.mood_dreamy
                Mood.NOSTALGIC -> R.string.mood_nostalgic
                Mood.SAD -> R.string.mood_sad
                Mood.ANGRY -> R.string.mood_angry
                Mood.ROAD_TRIP -> R.string.mood_road_trip
            }
        )
    }
}