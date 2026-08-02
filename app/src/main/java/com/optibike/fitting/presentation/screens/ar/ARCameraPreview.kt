package com.optibike.fitting.presentation.screens.ar

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import com.google.ar.core.ArCoreApk
import com.google.ar.core.CameraConfig
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem

/**
 * AR Camera Preview
 * Custom view for ARCore camera preview
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class ARCameraPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ArSceneView(context, attrs) {
    
    private var _session: Session? = null
    private var shouldConfigureSession = false
    
    val transformationSystem: TransformationSystem by lazy {
        TransformationSystem(resources.displayMetrics, FootprintSelectionVisualizer())
    }
    
    override fun resume() {
        if (_session == null) {
            try {
                when (ArCoreApk.getInstance().requestInstall(context as android.app.Activity, true)) {
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        // Handle install request
                    }
                    else -> {
                        // ARCore is available, create session
                        val session = Session(context)
                        _session = session
                        configureSession()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
        super.resume()
    }
    
    override fun pause() {
        super.pause()
        _session?.close()
        _session = null
    }
    
    private fun configureSession() {
        val config = Config(_session)
        config.cloudAnchorMode = Config.CloudAnchorMode.DISABLED
        config.augmentedFaceMode = Config.AugmentedFaceMode.DISABLED
        config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
        
        _session?.configure(config)
        shouldConfigureSession = true
    }
    
    override fun getSession(): Session? {
        return _session
    }
    
    fun isSessionAvailable(): Boolean {
        return _session != null
    }
    
    /**
     * Add an anchor at the given pose
     */
    fun addAnchor(anchor: com.google.ar.core.Anchor, modelPath: String) {
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(scene)
        
        ModelRenderable.builder()
            .setSource(context, android.net.Uri.parse(modelPath))
            .build()
            .thenAccept { renderable ->
                val node = TransformableNode(transformationSystem)
                node.renderable = renderable
                node.setParent(anchorNode)
                node.select()
            }
            .exceptionally { throwable ->
                // Handle error
                null
            }
    }
    
    /**
     * Clear all anchors
     */
    fun clearAnchors() {
        scene.children.forEach { child ->
            if (child is Node) {
                scene.removeChild(child)
            }
        }
    }
}

/**
 * Anchor Node for AR
 */
class AnchorNode(val anchor: com.google.ar.core.Anchor) : Node() {
    override fun onActivate() {
        super.onActivate()
        // Anchor is managed by ARCore, we just sync the node position
    }
    
    override fun onUpdate(frameTime: com.google.ar.sceneform.FrameTime) {
        super.onUpdate(frameTime)
        if (anchor.trackingState == com.google.ar.core.TrackingState.TRACKING) {
            worldPosition = com.google.ar.sceneform.math.Vector3(
                anchor.pose.tx(),
                anchor.pose.ty(),
                anchor.pose.tz()
            )
            worldRotation = com.google.ar.sceneform.math.Quaternion(
                anchor.pose.qx(),
                anchor.pose.qy(),
                anchor.pose.qz(),
                anchor.pose.qw()
            )
        }
    }
}
