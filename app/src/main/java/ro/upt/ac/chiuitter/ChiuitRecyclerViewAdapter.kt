package ro.upt.ac.chiuitter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_chiuit.view.*

class ChiuitRecyclerViewAdapter(
        private val chiuitList: MutableList<Chiuit>,
        private val onClick: (Chiuit) -> (Unit))
    : RecyclerView.Adapter<ChiuitRecyclerViewAdapter.ChiuitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChiuitViewHolder {
        val materialCardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chiuit, parent, false) as MaterialCardView
        return ChiuitViewHolder(materialCardView)
    }

    override fun getItemCount(): Int {
        return chiuitList.size
    }

    override fun onBindViewHolder(holder: ChiuitViewHolder, position: Int) {
        holder.bind(chiuitList[position])
    }

    fun addItem(chiuit: Chiuit) {
        chiuitList.add(chiuit)
        notifyItemInserted(chiuitList.size - 1)
    }

    inner class ChiuitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.ibt_share.setOnClickListener { onClick(chiuitList[adapterPosition]) }
        }

        fun bind(chiuit: Chiuit) {
            itemView.txv_content.text = chiuit.description
        }

    }

}