package bohdan.varchenko.gittestproject.screens.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import kotlinx.android.synthetic.main.item_repository.view.*

internal class RepositoryListAdapter constructor(
    private val imageLoader: ImageLoader,
    private val itemClickListener: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() {
    var items: List<Repository> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent, itemClickListener, imageLoader)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RepositoryViewHolder(
        parent: ViewGroup,
        itemClickListener: (Repository) -> Unit,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
    ) {
        private var repository: Repository? = null

        init {
            itemView.setOnClickListener {
                repository?.run { itemClickListener(this) }
            }
        }

        fun bind(repository: Repository) {
            this.repository = repository
            itemView.tvRepositoryName.text = repository.name
            itemView.tvRepositoryDescription.text = repository.description ?: "No description"
            imageLoader.loadImage(
                itemView.ivAuthorAvatar,
                repository.ownerAvatarUrl,
                R.mipmap.ic_launcher
            )
            itemView.ivViewedIndicator.alpha = if (repository.isViewed) 1f else 0.3f
            itemView.tvRepositoryStars.text = repository.stars.toString()
        }
    }
}