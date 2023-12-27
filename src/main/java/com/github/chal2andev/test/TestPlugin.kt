package com.github.chal2andev.test

import io.github.monun.kommand.kommand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin

class TestPlugin: JavaPlugin(), Listener{
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        registerKommand()
    }

    @EventHandler
    fun isMovePlayer(event: PlayerMoveEvent){
        event.player.sendMessage("Event Test")
    }

    private fun registerKommand(){
        kommand {
            register("sample"){
                requires { isOp }

                executes {
                    sender.sendMessage("kotlin, kommand plugin is enable")
                }
            }
        }
    }
}