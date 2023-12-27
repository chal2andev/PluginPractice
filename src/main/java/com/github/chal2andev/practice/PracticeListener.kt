package com.github.chal2andev.practice

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class PracticeListener: Listener{
    private lateinit var plugin: PracticePlugin

    fun initModule(plugin: PracticePlugin){
        this.plugin = plugin
    }
    var death = false
    @EventHandler
    fun onClickDice(event: PlayerInteractEvent){
        val player = event.player
        val action = event.action
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
            if(player.itemInHand.type == Material.SPAWNER){
                PracticeInv.diceInventory(player)
            }
        }
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
            if(player.itemInHand.type == Material.STICK){
                val itemName = player.itemInHand.itemMeta.displayName
                death = true
                for (players: Player in Bukkit.getOnlinePlayers()){
                    players.sendMessage("${player.name}이(가) ${itemName}에게 초슈퍼울트라 지건을 꽂았습니다.")
                }
                Bukkit.getPlayer(itemName)?.health = 0.0
            }
        }
    }
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent){
        if(death){
            event.deathMessage = ""
            death = false
        }
    }
}