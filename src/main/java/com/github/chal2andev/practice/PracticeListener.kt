package com.github.chal2andev.practice

import io.github.monun.tap.fake.FakeEntity
import io.github.monun.tap.fake.FakeSkinParts
import io.github.monun.tap.mojangapi.MojangAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class PracticeListener(
    private var plugin: PracticePlugin
): Listener{

    var death = false
    @EventHandler
    fun onClickDice(event: PlayerInteractEvent){
        if (event.hand != EquipmentSlot.HAND) return
        val player = event.player
        val action = event.action
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
            if(player.itemInHand.type == Material.SPAWNER){
                PracticeInv.diceInventory(player)
            }
            if(player.itemInHand.type == Material.STICK){
                val itemName = player.itemInHand.itemMeta.displayName
                if(itemName != "") {
                    death = true
                    for (players: Player in Bukkit.getOnlinePlayers()){
                        players.sendMessage("${player.name}이(가) ${itemName}에게 초슈퍼울트라 지건을 꽂았습니다.")
                    }
                    Bukkit.getPlayer(itemName)?.health = 0.0
                }
            }
            if(player.itemInHand.type == Material.FIRE_CHARGE){
                val world: World = player.world
                world.createExplosion(player.location , 30.0f, true)
                for (players: Player in Bukkit.getOnlinePlayers()){
                    players.sendTitle("${ChatColor.RED}${ChatColor.BOLD}이끄스쁘로우전", "", 10, 50, 10)
                }
                plugin.logger.info("이끄스쁘로우전")

            }
            if(player.itemInHand.type == Material.BLAZE_ROD){
                    val uuid = MojangAPI.fetchProfile("abebio")!!.uuid()
                    val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                    plugin.fakeServer.spawnPlayer(event.player.location, "준기모찌", profiles.toSet(), FakeSkinParts(0b1111111))
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
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        plugin.fakeServer.addPlayer(event.player)
    }
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent){
        plugin.fakeServer.removePlayer(event.player)
    }

}