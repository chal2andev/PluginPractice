package com.github.chal2andev.practice

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import io.github.monun.tap.fake.FakeSkinParts
import io.github.monun.tap.mojangapi.MojangAPI
import org.bukkit.ChatColor

internal object PracticeKommand {
    private lateinit var plugin: PracticePlugin

    fun initModule(plugin: PracticePlugin){
        this.plugin = plugin
    }

    fun register(){
        plugin.kommand {
            register("practice"){
                requires { isOp }
                executes {
                    sender.sendMessage("연습용 명령어!")
                }
                then("nice"){
                    executes {
                        sender.sendMessage("앙 기모찌")
                    }
                }
                then("loot"){
                    executes {
                        sender.sendMessage("인수를 입력하세요.")
                    }
                    then("myInt" to int()){
                        executes {
                            val myInt: Int by it

                            for (i in 1..myInt){
                                sender.sendMessage("${i}번째 실행")
                            }
                        }
                    }
                }
            }
            register("window"){
                requires { isPlayer }
                executes {
                    PracticeInv.generateInventory(player)
                }

            }
            register("player"){
                requires { isPlayer && isOp }
                executes {
                    sender.sendMessage("${ChatColor.GOLD}플레이어 이름을 입력하세요.")
                }
                then("name" to string()){
                    executes {
                        val name: String by it
                        val uuid = MojangAPI.fetchProfile(name)!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        plugin.fakeServer.spawnPlayer(player.location, "${ChatColor.AQUA}${name}", profiles.toSet(), FakeSkinParts(0b1111111))
                    }
                }
            }
        }
    }
}