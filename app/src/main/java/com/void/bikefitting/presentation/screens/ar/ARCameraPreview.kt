package com.void.bikefitting.presentation.screens.ar

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
import com.google.ar.sceneform.ux.TransformableNode

/**
 * AR Camera Preview
 * Custom view for ARCore camera preview
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
class ARCameraPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ArSceneView(context, attrs, defStyleAttr) {
    
    private var session: Session? = null
    private var shouldConfigureSession = false
    
    override fun onResume() {
        super.onResume()
        if (session == null) {
            try {
                when (ArCoreApk.getInstance().requestInstall(context, true)) {
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        // Handle install request
                    }
                    else -> {
                        // ARCore is available, create session
                        session = Session(context)
                        configureSession()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
    
    override fun onPause() {
        super.onPause()
        session?.close()
        session = null
    }
    
    private fun configureSession() {
        val config = Config(session)
        config.cloudAnchorMode = Config.CloudAnchorMode.DISABLED
        config.augmentedFaceMode = Config.AugmentedFaceMode.DISABLED
        config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
        
        session?.configure(config)
        shouldConfigureSession = true
    }
    
    fun getSession(): Session? {
        return session
    }
    
    fun isSessionAvailable(): Boolean {
        return session != null
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
                scene.addChild(anchorNode)
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
class AnchorNode(anchor: com.google.ar.core.Anchor) : Node() {
    override fun onActivate() {
        super.onActivate()
        if (anchor != null) {
            anchor.detach()
        }
    }
    
    override fun onUpdate(frameTime: com.google.ar.sceneform.FrameTime) {
        super.onUpdate(frameTime)
        if (anchor != null) {
            localPosition = anchor.pose.translation
            localRotation = anchor.pose.rotationQuaternion
        }
    }
}
