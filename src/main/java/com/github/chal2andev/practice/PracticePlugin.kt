package com.github.chal2andev.practice

import org.bukkit.plugin.java.JavaPlugin

class PracticePlugin: JavaPlugin() {
    override fun onEnable() {
        initModule()
    }

    private fun initModule(){
        PracticeKommand.initModule(this)
        PracticeInv.initModule(this)
        PracticeListener().initModule(this)
        registerEvents()
        setupCommands()
    }
    private fun setupCommands(){
        PracticeKommand.register()
    }
    private fun registerEvents(){
        server.pluginManager.registerEvents(PracticeListener(), this)
    }
}