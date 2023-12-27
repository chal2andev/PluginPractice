package com.github.chal2andev.practice

import org.bukkit.entity.Player
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.Random

object PracticeInv {

    private lateinit var plugin: PracticePlugin

    fun initModule(plugin: PracticePlugin){
        this.plugin = plugin
    }
    fun generateInventory(player: Player){
        val invFrame = InvFX.frame(5, Component.text("다이아몬드를 클릭하세요")){
            var clicked = false
            onClose { closeEvent ->
                if (!clicked){
                    closeEvent.player.sendMessage("다이아몬드를 클릭하세요")
                    object : BukkitRunnable(){
                        override fun run() {
                            player.openFrame(this@frame)
                        }
                    }.runTaskLater(plugin, 1)
                }
            }
            slot(4, 2){
                item = ItemStack(Material.DIAMOND)
                onClick { inventoryClickEvent ->
                    inventoryClickEvent.whoClicked.sendMessage("다이아몬드를 클릭하였습니다.")
                    clicked = true
                    inventoryClickEvent.whoClicked.closeInventory()
                }
            }
        }
        player.openFrame(invFrame)
    }
    fun diceInventory(player: Player){
        val invFrame = InvFX.frame(1, Component.text("주사위를 굴리세요")){
            slot(4, 0){
                var itemStack = ItemStack(Material.SPAWNER)
                var meta = itemStack.itemMeta
                meta.setDisplayName("주사위")
                itemStack.setItemMeta(meta)
                item = itemStack
                onClick { clickEvent ->
                    clickEvent.whoClicked.sendMessage("${Random().nextInt(5)+1}")
                    clickEvent.whoClicked.closeInventory()
                }
            }
        }
        player.openFrame(invFrame)
    }
}