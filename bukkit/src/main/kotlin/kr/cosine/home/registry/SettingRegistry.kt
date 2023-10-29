package kr.cosine.home.registry

import kr.hqservice.framework.global.core.component.Bean

@Bean
class SettingRegistry {

    internal companion object {
        var prefix = ""
    }

    var isCountDownTeleportEnabled = true
        private set

    var countDownTime: Int = 0
        private set

    fun setCountDownTeleportEnabled(isCountDownTeleportEnabled: Boolean) {
        this.isCountDownTeleportEnabled = isCountDownTeleportEnabled
    }

    fun setCountDownTime(countDownTime: Int) {
        this.countDownTime = countDownTime
    }
}