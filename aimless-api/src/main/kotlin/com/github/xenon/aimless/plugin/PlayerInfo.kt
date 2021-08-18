package com.github.xenon.aimless.plugin

import com.github.xenon.aimless.plugin.loader.LibraryLoader
import com.github.xenon.aimless.protocol.PlayerList


object PlayerInfo: Runnable {

    private var update = false

    fun update() {
        update = true
    }

    override fun run() {
        if (update) {
            update = false
            val playerList = requireNotNull(LibraryLoader.load(PlayerList::class.java)) { "Player List is null!" }
            playerList.updatePlayerList()
        }
    }
}