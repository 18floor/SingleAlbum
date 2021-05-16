package ru.netology.singlealbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.singlealbum.databinding.ItemAlbumBinding
import ru.netology.singlealbum.databinding.ItemTrackBinding
import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.dto.AlbumItem
import ru.netology.singlealbum.dto.Track

interface OnInteractionListener {
    fun onPlayPause(track: Track) {}
}

class AlbumAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<AlbumItem, RecyclerView.ViewHolder>(AlbumDiffCallback()) {

    private val typeHeader = 0
    private val typeTrack = 1

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Album -> typeHeader
            is Track -> typeTrack
            null -> throw IllegalArgumentException("unknown item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            typeHeader -> HeaderViewHolder(
                ItemAlbumBinding.inflate(layoutInflater, parent, false)
            )
            typeTrack -> TrackViewHolder(
                ItemTrackBinding.inflate(layoutInflater, parent, false),
                onInteractionListener
            )
            else -> throw IllegalArgumentException("unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (it) {
                is Album -> (holder as? HeaderViewHolder)?.bind(it)
                is Track -> (holder as? TrackViewHolder)?.bind(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    class TrackViewHolder(
        private val binding: ItemTrackBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {

            binding.apply {
                trackName.text = track.file
                playPauseButton.setOnClickListener {
                    onInteractionListener.onPlayPause(track)
                }
            }
        }
    }

    class HeaderViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {

            binding.apply {
                titleView.text = album.title
                artistView.text = album.artist
                subtitleView.text = album.subtitle
                publishedView.text = album.published
                genreView.text = album.genre

            }
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            if (oldItem::class != newItem::class) {
                return false
            }
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem == newItem
        }
    }
}

