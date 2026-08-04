package com.bedrock.client.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bedrock.client.R
import com.bedrock.client.launcher.Launcher
import com.bedrock.client.launcher.LauncherManager
import com.bedrock.client.minecraft.version.VersionManager
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var versionManager: VersionManager
    private lateinit var launchButton: MaterialButton
    private lateinit var versionText: TextView
    private lateinit var statusText: TextView
    private var launchInProgress = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        versionManager = VersionManager.getInstance(requireContext())
        launchButton = view.findViewById(R.id.btn_launch)
        versionText = view.findViewById(R.id.tv_version)
        statusText = view.findViewById(R.id.tv_status)

        renderInstalledVersion()
        launchButton.setOnClickListener { launchMinecraft() }
    }

    override fun onResume() {
        super.onResume()
        if (this::versionManager.isInitialized && !launchInProgress) {
            renderInstalledVersion()
        }
    }

    private fun launchMinecraft() {
        if (launchInProgress) return

        launchInProgress = true
        launchButton.isEnabled = false
        statusText.setText(R.string.status_launching)
        statusText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))

        val launcher = LauncherManager.getInstance().getLauncher()
        launcher.launch(Launcher.LaunchOptions()) { result ->
            activity?.runOnUiThread {
                if (!isAdded) return@runOnUiThread
                launchInProgress = false

                when (result) {
                    is Launcher.Result.Success -> {
                        val successMessage = if (result.environmentSynchronized) {
                            getString(
                                R.string.launch_success_private_env,
                                result.version,
                                result.instanceId
                            )
                        } else {
                            getString(
                                R.string.launch_success_local_env_only,
                                result.version,
                                result.instanceId
                            )
                        }

                        versionText.text = getString(
                            R.string.minecraft_version_detected,
                            result.version
                        )
                        statusText.text = successMessage
                        statusText.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.success)
                        )
                        Toast.makeText(
                            requireContext(),
                            successMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        launchButton.isEnabled = true
                    }

                    is Launcher.Result.Failure -> {
                        statusText.text = getString(R.string.launch_failed, result.reason)
                        statusText.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.error)
                        )
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.launch_failed, result.reason),
                            Toast.LENGTH_LONG
                        ).show()
                        launchButton.isEnabled = versionManager.getActiveVersion()?.isSupported == true
                    }
                }
            }
        }
    }

    private fun renderInstalledVersion() {
        val version = versionManager.getActiveVersion()
        if (version == null) {
            versionText.setText(R.string.minecraft_not_installed)
            statusText.setText(R.string.status_install_required)
            statusText.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.text_secondary)
            )
            launchButton.isEnabled = false
            return
        }

        versionText.text = getString(R.string.minecraft_version_detected, version.code)
        if (version.isSupported) {
            statusText.setText(R.string.status_ready)
            statusText.setTextColor(ContextCompat.getColor(requireContext(), R.color.success))
            launchButton.isEnabled = true
        } else {
            statusText.text = getString(R.string.minecraft_disabled, version.code)
            statusText.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
            launchButton.isEnabled = false
        }
    }
}
