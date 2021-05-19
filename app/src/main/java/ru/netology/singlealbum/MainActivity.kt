package ru.netology.singlealbum

import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.singlealbum.BuildConfig.BASE_URL
import ru.netology.singlealbum.adapter.AlbumAdapter
import ru.netology.singlealbum.adapter.OnInteractionListener
import ru.netology.singlealbum.databinding.ActivityMainBinding
import ru.netology.singlealbum.dto.Track
import ru.netology.singlealbum.player.MediaLifecycleObserver
import ru.netology.singlealbum.viewmodel.AlbumViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: AlbumViewModel by viewModels()
    private val mediaObserver = MediaLifecycleObserver()
    private var playList: List<Track> = emptyList()
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mediaObserver)

        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {

                playerController(BASE_URL + track.file)

            }
        })



        binding.albumList.adapter = adapter

        binding.albumList.addItemDecoration(
            DividerItemDecoration(binding.albumList.context, DividerItemDecoration.VERTICAL),
        )


        viewModel.album.observe(this) { state ->

            binding.progressView.isVisible = state.loading
            playList = state.album.tracks

            binding.titleView.text = state.album.title
            binding.artistView.text = state.album.artist
            binding.subtitleView.text = state.album.subtitle
            binding.publishedView.text = state.album.published
            binding.genreView.text = state.album.genre

            adapter.submitList(playList)

        }

    }


    fun playerController(url: String) {

        val endListener =
            OnCompletionListener {
                mediaObserver.apply {
                    onPause()
                }
            }

        val nextListener =
            OnCompletionListener {
                mediaObserver.apply {
                    onPause()
                    player?.setOnCompletionListener(endListener)
                    player?.setDataSource(BASE_URL + playList[1].file)
                    onPlay()
                }
            }

        mediaObserver.apply {
            if (player?.isPlaying == true) {
                onPause()
            } else {
                player?.setOnCompletionListener(nextListener)
                player?.setDataSource(url)
                onPlay()
            }
        }


    }


}