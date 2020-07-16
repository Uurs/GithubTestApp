package bohdan.varchenko.gittestproject.screens.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.gittestproject.R
import kotlinx.android.synthetic.main.item_repository.view.*

internal class RepositoryListAdapter constructor(
    private val itemClickListener: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() {
    var items: List<Repository> = emptyList()
        set(value) {
            if(field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RepositoryViewHolder(
        parent: ViewGroup,
        itemClickListener: (Repository) -> Unit
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
        }
    }
}