package kr.cosine.home.enums

enum class Announce {
    COUNT_DOWN,
    DETECT_MOVING;

    companion object {
        fun getAnnounce(announceText: String): Announce? {
            return values().find { it.name == announceText.uppercase().replace("-", "_") }
        }
    }
}