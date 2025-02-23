package com.smurzik.videoplayer.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smurzik.videoplayer.databinding.ListItemBinding

class VideoListAdapter(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<VideoListViewHolder>() {

    private val videoList = mutableListOf<VideoItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
    }

    override fun getItemCount() = videoList.size

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    fun update(source: List<VideoItemUi>) {
        val diffUtil = DiffUtilCallback(videoList, source)
        val diff = DiffUtil.calculateDiff(diffUtil)
        videoList.clear()
        videoList.addAll(source)
        diff.dispatchUpdatesTo(this)
    }
}

interface ClickListener {
    fun click(item: VideoItemUi)
}

class VideoListViewHolder(
    private val binding: ListItemBinding,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val image = binding.videoThumbnail
    private val title = binding.videoTitle
    private val duration = binding.videoDuration
    private val mapper = ListItemMapper(image, title, duration)

    fun bind(item: VideoItemUi) {
        item.map(mapper)
        binding.root.setOnClickListener {
            clickListener.click(item)
        }
    }
}

class DiffUtilCallback(
    private val oldList: List<VideoItemUi>,
    private val newList: List<VideoItemUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].matches(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}