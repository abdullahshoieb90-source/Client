
package com.bedrock.client.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedrock.client.R
import com.bedrock.client.worlds.WorldInfo

class WorldAdapter(private val worlds: List<WorldInfo>) : RecyclerView.Adapter<WorldAdapter.VH>() {
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.world_name)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(LayoutInflater.from(parent.context).inflate(R.layout.item_world, parent, false))
    override fun getItemCount() = worlds.size
    override fun onBindViewHolder(holder: VH, position: Int) { holder.name.text = worlds[position].name }
}
