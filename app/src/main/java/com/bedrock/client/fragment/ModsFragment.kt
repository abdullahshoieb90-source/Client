
package com.bedrock.client.fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bedrock.client.R
import com.bedrock.client.adapter.ModuleAdapter
import com.bedrock.client.modules.ModuleManager

class ModsFragment : Fragment(R.layout.fragment_mods) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rv_modules)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = ModuleAdapter(ModuleManager.getInstance().getAllModules())
    }
}
