package com.github.chal2andev.practice

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand

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
        }
    }
}