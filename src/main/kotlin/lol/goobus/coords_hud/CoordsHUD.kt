package lol.goobus.coords_hud

import it.unimi.dsi.fastutil.floats.Float2ReferenceAVLTreeMap
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW

class CoordsHUD : ModInitializer {
    lateinit var toggleKey: KeyBinding
    var showCoords = true

    override fun onInitialize() {
        toggleKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding("Toggle Coords HUD", GLFW.GLFW_KEY_H, "Coords HUD")
        )

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (toggleKey.wasPressed()) {
                showCoords = !showCoords
            }
        }

        HudRenderCallback.EVENT.register { matrices, _ ->
            if (!showCoords) return@register

            val client = MinecraftClient.getInstance()
            val player = client.player ?: return@register
            val vertexConsumers = client.bufferBuilders.entityVertexConsumers

            val x = "%.1f".format(player.x)
            val y = "%.1f".format(player.y)
            val z = "%.1f".format(player.z)
            val coords = "XYZ:$x / $y / $z"

            val textRenderer = client.textRenderer
            val matrix = matrices.matrices.peek().positionMatrix

            textRenderer.draw(
                coords,         // Text
                5f,                 // x position
                5f,                 // y position
                0xFFFFFF,           // color
                false,              // shadow
                matrix,
                vertexConsumers,    // VertexConsumerProvider
                TextRenderer.TextLayerType.NORMAL,
                0,                  // background color (0 = none)
                15728880            // light value (full brightness = 0xF000F0)
            )

    }
}}