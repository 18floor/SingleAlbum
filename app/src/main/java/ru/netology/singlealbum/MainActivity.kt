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
    private var currentTrack = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Уберу привязку к жизненному циклу - мне больше нравится когда музыка играет в бэкграунде
//        lifecycle.addObserver(mediaObserver)

        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {

                viewModel.isPlayed(track.id)
                currentTrack = track.id
                playerController(BASE_URL + track.file)

            }
        })

        binding.albumList.adapter = adapter

        binding.albumList.addItemDecoration(
            DividerItemDecoration(binding.albumList.context, DividerItemDecoration.VERTICAL),
        )

        viewModel.album.observe(this) { state ->

            binding.progressView.isVisible = state.loading

            binding.apply {
                titleView.text = state.album.title
                artistView.text = state.album.artist
                subtitleView.text = state.album.subtitle
                publishedView.text = state.album.published
                genreView.text = state.album.genre
            }
        }

        viewModel.data.observe(this) { state ->

            playList = state.tracks
            adapter.submitList(playList)

            playList.forEachIndexed { index, track ->
                if (track.id == currentTrack) currentIndex = index
            }
        }
    }

    fun playerController(url: String) {

        val nextListener =
            OnCompletionListener {
                mediaObserver.apply {
                    onStop()
                    if (currentIndex <= playList.size) {
                        currentIndex++
                        currentTrack++
                        player?.setDataSource(BASE_URL + playList[currentIndex].file).let {
                            viewModel.isPlayed(currentTrack - 1)
                            viewModel.isPlayed(currentTrack)
                        }
                        onPlay()
                    } else {
                        player?.setDataSource(BASE_URL + playList[0].file)
                        onPlay()
                    }
                }
            }

        mediaObserver.apply {
            if (player != null && player?.isPlaying == true) {
                if (currentTrack != currentIndex + 1) {
                    onStop()
                    viewModel.isPlayed(currentIndex + 1)
                    player?.setDataSource(url)
                    onPlay()
                } else {
                    onStop()
                }
            } else if (player != null) {
                player?.setOnCompletionListener(nextListener)
                player?.setDataSource(url)
                onPlay()
            }
        }
    }
}