package com.github.xenon.aimless.plugin

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

class AimlessPlugin : JavaPlugin() {
    override fun onEnable() {
        for (world in Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true)
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false)
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, false)
            world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true)
        }

        Bukkit.getWorlds().first().apply {
            val border = worldBorder
            border.center = Location(this, 0.0, 0.0, 0.0)
            border.size = 16384.0
            spawnLocation = getHighestBlockAt(0, 0).location
        }

        server.pluginManager.registerEvents(EventListener(), this)
        server.scheduler.runTaskTimer(this, PlayerInfo, 0L , 1L)
        server.scheduler.runTaskTimer(this, Restarter(), 20L * 60L, 20L * 60L)
    }
}