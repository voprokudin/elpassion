package p.vasylprokudin.elpassion.presentation.view.list.listener

import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem

interface VPRepositoryClickedListener {

    fun onRepositoryRowClicked(repository: VPRawItem)
}