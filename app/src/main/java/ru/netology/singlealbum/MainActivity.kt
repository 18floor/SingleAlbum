package ru.netology.singlealbum

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.singlealbum.BuildConfig.BASE_URL
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

        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
//                    setDataSource(url)
//                    prepare()
//                    start()
        }


        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {

                val url = BASE_URL + track.file


                if (mediaPlayer.isPlaying) {
                    mediaPlayer.release()
                } else {
                    mediaPlayer.setDataSource(url)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }





//                mediaObserver.apply {
//                    player?.setDataSource(
//                        BASE_URL + track.file
//                    )
//                }.let {
//
//                    if (it.player?.isPlaying == true) {
//                        it.player?.pause()
//                    } else {
//                        it.play()
//                    }
//
//                }

            }
        })



        binding.albumList.adapter = adapter

        binding.albumList.addItemDecoration(
            DividerItemDecoration(binding.albumList.context, DividerItemDecoration.VERTICAL),
        )


        viewModel.album.observe(this) { state ->

            binding.progressView.isVisible = state.loading

            binding.titleView.text = state.album.title
            binding.artistView.text = state.album.artist
            binding.subtitleView.text = state.album.subtitle
            binding.publishedView.text = state.album.published
            binding.genreView.text = state.album.genre

            adapter.submitList(state.album.tracks)
        }


    }
}