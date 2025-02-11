package com.helloumi.ui.utils.task

object TaskUtils {
    fun String?.isTaskCompleted(): Boolean = this?.startsWith("Done") == true
}
