
package com.bedrock.client.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedrock.client.R
import com.bedrock.client.skins.SkinInfo

class SkinAdapter(private val skins: List<SkinInfo>) : RecyclerView.Adapter<SkinAdapter.VH>() {
    class VH(v: View) : RecyclerView.ViewHolder(v)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(LayoutInflater.from(parent.context).inflate(R.layout.item_skin, parent, false))
    override fun getItemCount() = skins.size
    override fun onBindViewHolder(holder: VH, position: Int) {}
}
