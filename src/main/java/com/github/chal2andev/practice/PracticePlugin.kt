package com.github.chal2andev.practice

import io.github.monun.tap.fake.FakeEntityServer
import org.bukkit.plugin.java.JavaPlugin

class PracticePlugin: JavaPlugin() {
    lateinit var fakeServer: FakeEntityServer
    override fun onEnable() {
        initModule()
        fakeServer()
    }

    private fun initModule(){
        PracticeKommand.initModule(this)
        PracticeInv.initModule(this)
        registerEvents()
        setupCommands()
    }
    private fun setupCommands(){
        PracticeKommand.register()
    }
    private fun registerEvents(){
        server.pluginManager.registerEvents(PracticeListener(this), this)
    }
    private fun fakeServer(){
        fakeServer = FakeEntityServer.create(this)
        server.scheduler.runTaskTimer(this, fakeServer::update, 0L, 1L)
    }
}