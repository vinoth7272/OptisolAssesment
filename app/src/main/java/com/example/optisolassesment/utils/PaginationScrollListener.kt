package com.example.optisolassesment.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = linearLayoutManager.getChildCount()
        val totalItemCount: Int = linearLayoutManager.getItemCount()
        val firstVisibleItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
            && firstVisibleItemPosition >= 0
        ) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()
}