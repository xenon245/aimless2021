package com.github.xenon.aimless.v1_17_1.protocol

import com.github.xenon.aimless.protocol.PlayerList
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.server.level.ServerPlayer
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_17_R1.CraftOfflinePlayer
import org.bukkit.craftbukkit.v1_17_R1.CraftServer
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class NMSPlayerList : PlayerList {
    override fun updatePlayerList() {
        val list = ArrayList<ServerPlayer>()
        for (offlinePlayer in Bukkit.getOfflinePlayers().asSequence()) {
            list += (Bukkit.getServer() as CraftServer).handle.getPlayer(offlinePlayer.uniqueId)!!
        }

        val packet = ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, list)
        for (player in Bukkit.getOnlinePlayers()) {
            (player as CraftPlayer).handle.connection.send(packet)
        }
    }
}