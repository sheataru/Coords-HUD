package lol.goobus.coords_hud

import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

object ConfigCommands {
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("changecoordhudpos")
                    .then(argument("x", FloatArgumentType.floatArg())
                        .then(argument("y", FloatArgumentType.floatArg())
                            .executes { ctx ->
                                var x = FloatArgumentType.getFloat(ctx, "x")
                                var y = FloatArgumentType.getFloat(ctx, "y")
                                MinecraftClient.getInstance().inGameHud.chatHud.addMessage(
                                    Text.literal("set x pos to $x and y pos to $y !")
                                )
                                CoordsHUD.xpos = x
                                CoordsHUD.ypos = y

                                1
                            }
                        )
                    )
            )
        }
    }
}
