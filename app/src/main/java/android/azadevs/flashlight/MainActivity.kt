package android.azadevs.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {

    private var camId: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

            camId = cameraManager.cameraIdList[0]

            FlashLight(cameraManager = cameraManager, camId = camId)

        }
    }

    @Composable
    fun FlashLight(
        cameraManager: CameraManager,
        camId: String
    ) {
        val torchState = remember {
            mutableStateOf(false)
        }

        val image = if (torchState.value) {
            R.drawable.flashlight_on
        } else {
            R.drawable.flashlight_off
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (!torchState.value) {
                        torchState.value = true
                        cameraManager.setTorchMode(camId, torchState.value)
                    } else {
                        torchState.value = false
                        cameraManager.setTorchMode(camId, torchState.value)
                    }
                }
            )
        }
    }
}

