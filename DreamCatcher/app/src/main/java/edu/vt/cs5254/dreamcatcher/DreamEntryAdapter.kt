package edu.vt.cs5254.dreamcatcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.dreamcatcher.databinding.ListItemDreamEntryBinding

private const val CONCEIVED_BUTTON_COLOR = "#adcffa"
private const val DEFERRED_BUTTON_COLOR = "#f4dec5"
private const val FULFILLED_BUTTON_COLOR = "#a4bc10"
private const val REFLECTION_BUTTON_COLOR = "#c9aa39"

class DreamEntryHolder(private val binding : ListItemDreamEntryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var boundEntry: DreamEntry
        private set

    fun bind(dreamEntry: DreamEntry) {
        boundEntry = dreamEntry
        binding.dreamEntryButton.displayEntry(dreamEntry)
    }

    private fun Button.displayEntry(entry: DreamEntry) {
        text = entry.kind.toString()

        when(entry.kind) {
            DreamEntryKind.CONCEIVED -> {
                setBackgroundWithContrastingText(CONCEIVED_BUTTON_COLOR)
            }

            DreamEntryKind.DEFERRED -> {
                setBackgroundWithContrastingText(DEFERRED_BUTTON_COLOR)
            }

            DreamEntryKind.FULFILLED -> {
                setBackgroundWithContrastingText(FULFILLED_BUTTON_COLOR)
            }

            DreamEntryKind.REFLECTION -> {
                isAllCaps = false
                text = entry.text
                setBackgroundWithContrastingText(REFLECTION_BUTTON_COLOR)
            }

        }
    }


    }

class DreamEntryAdapter(private  val entries : List<DreamEntry>) : RecyclerView.Adapter<DreamEntryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamEntryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDreamEntryBinding.inflate(inflater, parent, false)
        return DreamEntryHolder(binding)
    }

    override fun onBindViewHolder(holder: DreamEntryHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}