package ru.netology.singlealbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.singlealbum.adapter.AlbumAdapter
import ru.netology.singlealbum.adapter.OnInteractionListener
import ru.netology.singlealbum.databinding.ActivityMainBinding
import ru.netology.singlealbum.dto.Track
import ru.netology.singlealbum.viewmodel.AlbumViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {
                super.onPlayPause(track)
            }
        })



        binding.albumList.adapter = adapter

        binding.albumList.addItemDecoration(
            DividerItemDecoration(binding.albumList.context, DividerItemDecoration.VERTICAL),
        )


        viewModel.album.observe(this) { state ->

            binding.titleView.text = state.album.title
            binding.artistView.text = state.album.artist
            binding.subtitleView.text = state.album.subtitle
            binding.publishedView.text = state.album.published
            binding.genreView.text = state.album.genre

            adapter.submitList(state.album.tracks)
        }


    }
}