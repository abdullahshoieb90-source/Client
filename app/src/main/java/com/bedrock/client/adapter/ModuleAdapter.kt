
package com.bedrock.client.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedrock.client.R
import com.bedrock.client.modules.GameModule

class ModuleAdapter(private val modules: List<GameModule>) : RecyclerView.Adapter<ModuleAdapter.VH>() {
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.module_name)
        val desc: TextView = v.findViewById(R.id.module_desc)
        val toggle: Switch = v.findViewById(R.id.module_switch)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_module, parent, false)
        return VH(v)
    }
    override fun getItemCount() = modules.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = modules[position]
        holder.name.text = m.name
        holder.desc.text = m.description
        holder.toggle.isChecked = m.isEnabled
        holder.toggle.setOnCheckedChangeListener { _, checked -> m.setEnabled(checked) }
    }
}
