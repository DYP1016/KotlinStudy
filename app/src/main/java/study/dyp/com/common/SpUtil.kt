package study.dyp.com.common

object SpUtil : BaseSpUtil() {
    private val KEY_COUNT = "kc"

    override fun getSharePreferencesName(): String {
        return "save"
    }

    fun setCurrentCount(count: Int?) = setIntValue(KEY_COUNT, count)

    fun getCurrentCount() = getIntValue(KEY_COUNT, 0)
}