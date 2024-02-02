package nl.hva.chessclock.data.model

data class Ruleset(
    val label: String,
    var minutes: Long,
    var seconds: Long,
    var extra: Long,
    var custom: Boolean = false
) {
    companion object {
        fun all() = listOf(
            Ruleset("Custom", 0L, 0L, 0L, true),
            Ruleset("Bullet (1+0)", 1L, 0L, 0L),
            Ruleset("Rapid (15+10)", 15L, 0L, 10L),
            Ruleset("Blitz (5+0)", 5L, 0L, 0L),
            Ruleset("Blitz (1+5)", 1L, 0L, 5L)
        )
    }
}