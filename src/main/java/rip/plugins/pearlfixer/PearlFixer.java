package rip.plugins.pearlfixer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;


public final class PearlFixer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        String requiredSetting = "Paper world setting \"disable-unloaded-chunk-enderpearl-exploit\" must be set tot false\n" +
                "This warning will always show up, even if the setting is set to false";
        getLogger().warning(requiredSetting);
    }

    @EventHandler
    public void onPearl(EntitySpawnEvent e) {

        if (!(e.getEntity() instanceof EnderPearl)) {
            return;
        }
        HashSet<Chunk> chunkHashSet = new HashSet<>();

        long time = System.currentTimeMillis();
        if (e.getEntity() instanceof EnderPearl) {
            Bukkit.getScheduler().runTaskTimer(this, (runnable) -> {


                boolean hadChunk = chunkHashSet.add(e.getEntity().getLocation().getChunk());
                if(hadChunk){
                    e.getEntity().getLocation().getChunk().addPluginChunkTicket(this);
                }

                if (e.getEntity().isDead()) {
                    for (Chunk chunk : chunkHashSet) {
                        chunk.removePluginChunkTicket(this);
                    }
                    runnable.cancel();

                } else if (time + 60000 < System.currentTimeMillis()) {
                    for (Chunk chunk : chunkHashSet) {
                        chunk.removePluginChunkTicket(this);
                    }
                    runnable.cancel();
                }
            }, 0, 0);
        }
    }

}