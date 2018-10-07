package com.vikram.nowplaying.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikram.nowplaying.R
import com.vikram.nowplaying.adapter.FavoritesAdapter
import com.vikram.nowplaying.adapter.ViewType
import com.vikram.nowplaying.viewmodel.SongsViewModel
import kotlinx.android.synthetic.main.fragment_songs.*

/**
 *   Created by vikramgupta on 10/7/18.
 */

class FavoritesFragment: Fragment() {

    private var favoritesAdapter = FavoritesAdapter { position, viewType ->
        when (viewType) {
            ViewType.ROW ->  onItemClick(position)
        }
    }

    private fun onItemClick(position: Int) {
        // TODO open songs detail page
    }

    private lateinit var viewModel: SongsViewModel

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        viewModel = ViewModelProviders.of(activity!!).get(SongsViewModel::class.java)
        viewModel.getFavorites()?.observe(this, Observer { songs ->  if (songs != null) favoritesAdapter.updateList(songs)})

    }

    private fun setupRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(getDivider())
        recyclerView.adapter = favoritesAdapter
    }

    private fun getDivider(): DividerItemDecoration {
        var divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(recyclerView.context, R.drawable.list_divider)!!)
        return divider
    }
}