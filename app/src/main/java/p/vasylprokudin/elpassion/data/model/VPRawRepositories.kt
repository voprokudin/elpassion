package p.vasylprokudin.elpassion.data.model

data class VPRawRepositories(
    val total_count: Int,
    val items: ArrayList<VPRawItem>
) {
    data class VPRawItem(
        val id: Int,
        val name: String,
        val full_name: String,
        val description: String,
        val language: String,
        val watchers: Int,
        val owner: VPRawOwner
    ) {
        data class VPRawOwner(
            val login: String,
            val id: Int,
            val avatar_url: String
        )
    }
}