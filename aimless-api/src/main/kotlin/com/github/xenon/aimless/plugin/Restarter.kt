package com.github.xenon.aimless.plugin

import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit

class Restarter: Runnable {
    private val time = System.currentTimeMillis()

    override fun run() {
        val elapsedTime = System.currentTimeMillis() - time

        val restartTime = 1000L * 60L * 60L * 2L

        if (elapsedTime >= restartTime) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage("서버가 재시작됩니다.")
                player.kick(text().content("서버 재시작").build())
            }
            Bukkit.reload()
        } else if (elapsedTime >= restartTime - 60000L) {
            Bukkit.broadcastMessage("1분 뒤 서버가 재시작됩니다.")
        }
    }
}