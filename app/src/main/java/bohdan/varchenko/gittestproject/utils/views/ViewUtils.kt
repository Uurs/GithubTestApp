package bohdan.varchenko.gittestproject.utils.views

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.addOnScrollListener(
    crossinline listener: (Int) -> Unit
): RecyclerView.OnScrollListener {
    val scrollListener = object: RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisiblePosition = recyclerView.layoutManager.let {
                when (it) {
                    is LinearLayoutManager -> it.findLastVisibleItemPosition()

                    is GridLayoutManager -> it.findLastVisibleItemPosition()

                    else -> throw IllegalArgumentException("unsupported layout manager")
                }
            }
            listener(lastVisiblePosition)
        }
    }
    addOnScrollListener(scrollListener)
    return scrollListener
}