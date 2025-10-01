package org.example.project

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// TaskRepository.kt
class TaskRepository {
    
    private val basicTasks = listOf(
        "رتب غرفتك لمدة 5 دقائق",
        "ساعد أحد أفراد الأسرة",
        "احفظ آية جديدة قصيرة",
        "قدم مجاملة لثلاثة أشخاص",
        "أنهي واجباتك بتركيز"
    )
    
    private val creativeTasks = listOf(
        "ارسم لوحة تعبر عن أحلامك",
        "اخترع قصة خيالية إيجابية",
        "صمم بطاقة شكر لشخص تحبه",
        "ابتكر لعبة جديدة",
        "أنشئ مشروعاً فنياً صغيراً"
    )
    
    private val charityTasks = listOf(
        "ساعد في تنظيف مكان عام",
        "تبرع بجزء من مصروفك",
        "ساعد جارك المسن",
        "انشر فكرة إيجابية",
        "قم بعمل تطوعي بسيط"
    )
    
    @OptIn(ExperimentalUuidApi::class)
    fun generateDailyTasks(): List<GameTask> {
        return listOf(
            GameTask(
                id = Uuid.random().toString(),
                title = "مهمة أساسية",
                description = basicTasks.random(),
                type = TaskType.BASIC
            ),
            GameTask(
                id = Uuid.random().toString(),
                title = "مهمة إبداعية",
                description = creativeTasks.random(),
                type = TaskType.CREATIVE
            ),
            GameTask(
                id = Uuid.random().toString(),
                title = "مهمة خيرية",
                description = charityTasks.random(),
                type = TaskType.CHARITY
            )
        )
    }
}

// PowerRepository.kt
class PowerRepository {
    
    private val superPowers = listOf(
        SuperPower(
            id = "1",
            name = "قوة الإبداع",
            description = "يمكنك ابتكار 3 أفكار جديدة يومياً"
        ),
        SuperPower(
            id = "2",
            name = "قوة السرعة",
            description = "أنجز مهامك بوقت أقل وكفاءة أعلى"
        ),
        SuperPower(
            id = "3",
            name = "قوة التعاون",
            description = "ساعد 5 أشخاص في إكمال مهامهم"
        ),
        SuperPower(
            id = "4",
            name = "قوة الحكمة",
            description = "احصل على نصائح ذكية لأحلامك"
        ),
        SuperPower(
            id = "5",
            name = "قوة الأحلام الكبيرة",
            description = "استطيع تحقيق أحلام أكبر"
        )
    )
    
    fun getAvailablePowers(level: Int): List<SuperPower> {
        return when {
            level <= 1 -> superPowers.take(1)
            level <= 3 -> superPowers.take(2)
            level <= 5 -> superPowers.take(3)
            else -> superPowers.take(4)
        }
    }
}