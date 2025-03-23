package com.falon.habit.weight.data.mapper

import com.falon.habit.weight.data.model.WeightHistoryData
import com.falon.habit.weight.domain.model.*
import com.falon.habit.weight.domain.model.WeightHistory
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class WeightMapper {

    fun from(weightHistoryData: WeightHistoryData): WeightHistory =
        with(weightHistoryData) {
            WeightHistory(
                userUid = userUid,
                weightGoal = weightGoal,
                weights = weights.map { weightItemData ->
                    WeightItem(
                        value = weightItemData.value,
                        time = weightItemData.time.toLocalDateTime()
                    )
                },
                fatGoal = fatGoal,
                fats = fats.map { fatItemData ->
                    FatItem(
                        value = fatItemData.value,
                        time = fatItemData.time.toLocalDateTime()
                    )
                },
                muscleGoal = muscleGoal,
                muscles = muscles.map { muscleItemData ->
                    MuscleItem(
                        value = muscleItemData.value,
                        time = muscleItemData.time.toLocalDateTime()
                    )
                },
                waterGoal = waterGoal,
                waters = waters.map { waterItemData ->
                    WaterItem(
                        value = waterItemData.value,
                        time = waterItemData.time.toLocalDateTime()
                    )
                },
                bmiGoal = bmiGoal,
                height = height,
                bonesGoal = bonesGoal,
                bones = bones.map { bonesItemData ->
                    BonesItem(
                        value = bonesItemData.value,
                        time = bonesItemData.time.toLocalDateTime()
                    )
                }
            )
        }

    private fun Long.toLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}
