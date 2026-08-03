
package com.bedrock.client.fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bedrock.client.R
import com.bedrock.client.launcher.Launcher
import com.bedrock.client.launcher.LauncherManager
import com.bedrock.client.minecraft.version.VersionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<android.widget.Button>(R.id.btn_launch)?.setOnClickListener {
            val launcher = LauncherManager.getInstance().getLauncher()
            launcher.launch(Launcher.LaunchOptions()) { result ->
                activity?.runOnUiThread {
                    when(result) {
                        is Launcher.Result.Success -> android.widget.Toast.makeText(context, "Launched PID ${result.pid}", android.widget.Toast.LENGTH_SHORT).show()
                        is Launcher.Result.Failure -> android.widget.Toast.makeText(context, "Failed: ${result.reason}", android.widget.Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
