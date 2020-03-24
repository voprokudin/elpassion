package p.vasylprokudin.elpassion.domain.model

data class VPSearchParams(
    val query: String,
    val page: Int,
    val perPage: Int = 30
)