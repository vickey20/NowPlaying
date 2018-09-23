package com.vikram.nowplaying.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikram.nowplaying.R
import com.vikram.nowplaying.adapter.SongsAdapter
import com.vikram.nowplaying.viewmodel.SongsViewModel
import kotlinx.android.synthetic.main.fragment_songs.*

class SongsFragment: Fragment() {

    private var songsAdapter = SongsAdapter()
    private lateinit var viewModel: SongsViewModel

    companion object {
        fun newInstance(): SongsFragment {
            return SongsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        viewModel = ViewModelProviders.of(activity!!).get(SongsViewModel::class.java)
        viewModel.getAllSongs()?.observe(this, Observer { songs ->  if (songs != null) songsAdapter.updateList(songs)})

    }

    private fun setupRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = songsAdapter
    }
}