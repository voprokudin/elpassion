package p.vasylprokudin.elpassion.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.vp_list_item.view.tvRepositoryTitle
import kotlinx.android.synthetic.main.vp_list_item.view.tvUsername
import kotlinx.android.synthetic.main.vp_list_item.view.ivThumb
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.presentation.view.list.listener.VPRepositoryClickedListener
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem

class VPRepositoriesAdapter : ListAdapter<VPRawItem, VPRepositoriesAdapter.VPViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<VPRawItem>() {
            override fun areItemsTheSame(oldItem: VPRawItem, newItem: VPRawItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VPRawItem, newItem: VPRawItem): Boolean =
                oldItem == newItem
        }
    }

    lateinit var repositoryClickedListener: VPRepositoryClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vp_list_item, parent, false)
        return VPViewHolder(itemView, repositoryClickedListener)
    }

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VPViewHolder(
        private val view: View,
        private val itemsClickedListener: VPRepositoryClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: VPRawItem) = with(view) {
            Glide.with(this)
                .load(item.owner.avatar_url)
                .into(ivThumb)
            tvRepositoryTitle.text = item.name
            tvUsername.text = item.description
            setOnClickListener { itemsClickedListener.onRepositoryRowClicked(item) }
        }
    }
}
