package uz.gita.game2048v1.screen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.gita.game2048v1.data.model.RecordData
import uz.gita.game2048v1.databinding.ItemScoreBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NotifyDataSetChanged")
class ScoresAdapter : RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {
    inner class ScoresViewHolder(private val binding: ItemScoreBinding) : ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.tvDate.text = converter(list[pos].date)
            binding.tvRecord.text = list[pos].record.toString()

//            if (pos >= 3) {
//                binding.medal.isVisible = false
//            }
        }
    }

    private var list = ArrayList<RecordData>()
    fun addList(ls: List<RecordData>) {
        list.clear()
        list.addAll(ls/*.filter { return@filter it.record.toInt() != 0 }*/)
        list.sortedBy { it.record }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScoresViewHolder(
        ItemScoreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NewApi")
    fun converter(millis: Long): String {
        val savedInstant = Instant.ofEpochMilli(millis)
        val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        val localDateTime = LocalDateTime.ofInstant(savedInstant, ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }
}