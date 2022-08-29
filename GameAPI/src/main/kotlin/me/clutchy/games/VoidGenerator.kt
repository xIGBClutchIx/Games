package me.clutchy.games

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

internal class VoidGenerator: ChunkGenerator() {

    override fun getDefaultPopulators(world: World): List<BlockPopulator> {
        return emptyList()
    }

    override fun canSpawn(world: World, x: Int, z: Int): Boolean {
        return true
    }

    override fun getFixedSpawnLocation(world: World, random: Random): Location? {
        return Location(world, 0.0, 100.0, 0.0)
    }
}
