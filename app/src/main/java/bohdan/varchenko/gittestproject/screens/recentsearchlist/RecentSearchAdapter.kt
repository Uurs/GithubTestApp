package bohdan.varchenko.gittestproject.screens.recentsearchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.gittestproject.R
import kotlinx.android.synthetic.main.item_recent_search.view.*

class RecentSearchAdapter(
    private val itemClickListener: (SearchQuery) -> Unit
) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {
    var recentSearchList: List<SearchQuery> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder =
        RecentSearchViewHolder(
            parent,
            itemClickListener
        )

    override fun getItemCount(): Int = recentSearchList.size

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(recentSearchList[position])
    }

    class RecentSearchViewHolder(
        parent: ViewGroup,
        clickListener: (SearchQuery) -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recent_search, parent, false)
    ) {
        private var query: SearchQuery? = null

        init {
            itemView.setOnClickListener {
                query?.run {
                    itemView.setOnClickListener { clickListener(this) }
                }
            }
        }

        fun bind(searchQuery: SearchQuery) {
            this.query = searchQuery
            itemView.tvRecentSearch.text = searchQuery.text
        }
    }
}