package ru.netology.singlealbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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

        viewModel.album.observe(this) { album ->
            binding.titleView.text = album.album.title
            binding.artistView.text = album.album.artist
            binding.subtitleView.text = album.album.subtitle
            binding.publishedView.text = album.album.published
            binding.genreView.text = album.album.genre


            adapter.submitList(album.album.tracks)
        }


    }

}