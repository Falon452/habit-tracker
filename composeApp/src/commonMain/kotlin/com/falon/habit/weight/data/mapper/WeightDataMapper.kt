package com.falon.habit.weight.data.mapper


import com.falon.habit.weight.data.model.BonesItemData
import com.falon.habit.weight.data.model.FatItemData
import com.falon.habit.weight.data.model.MuscleItemData
import com.falon.habit.weight.data.model.WaterItemData
import com.falon.habit.weight.data.model.WeightHistoryData
import com.falon.habit.weight.data.model.WeightItemData
import com.falon.habit.weight.domain.model.WeightHistory
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class WeightDataMapper {

    fun from(weightHistory: WeightHistory): WeightHistoryData =
        with(weightHistory) {
            WeightHistoryData(
                userUid = userUid,
                weightGoal = weightGoal,
                weights = weights.map { weightItem ->
                    WeightItemData(
                        value = weightItem.value,
                        time = weightItem.time.toTimestampUTC()
                    )
                },
                fatGoal = fatGoal,
                fats = fats.map { fatItem ->
                    FatItemData(
                        value = fatItem.value,
                        time = fatItem.time.toTimestampUTC()
                    )
                },
                muscleGoal = muscleGoal,
                muscles = muscles.map { muscleItem ->
                    MuscleItemData(
                        value = muscleItem.value,
                        time = muscleItem.time.toTimestampUTC()
                    )
                },
                waterGoal = waterGoal,
                waters = waters.map { waterItem ->
                    WaterItemData(
                        value = waterItem.value,
                        time = waterItem.time.toTimestampUTC()
                    )
                },
                bmiGoal = bmiGoal,
                bonesGoal = bonesGoal,
                bones = bones.map { bonesItem ->
                    BonesItemData(
                        value = bonesItem.value,
                        time = bonesItem.time.toTimestampUTC()
                    )
                },
                height = height,
            )
        }

    private fun LocalDateTime.toTimestampUTC(): Long = toInstant(TimeZone.UTC).toEpochMilliseconds()
}
