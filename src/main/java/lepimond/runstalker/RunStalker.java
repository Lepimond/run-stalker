package lepimond.runstalker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.io.File;
import java.io.IOException;

public class RunStalker implements ModInitializer
{
    private static final String SHADOW_OF_CHERNOBYL = "D:\\Games\\Steam\\steamapps\\common\\STALKER Shadow of Chernobyl\\bin\\XR_3DA.exe";
    private static final String CLEAR_SKY = "D:\\Games\\Steam\\steamapps\\common\\STALKER Clear Sky\\bin\\xrEngine.exe";
    private static final String CALL_OF_PRIPYAT = "D:\\Games\\Steam\\steamapps\\common\\Stalker Call of Pripyat\\bin\\xrEngine.exe";

    @Override
    public void onInitialize()
    {
        System.out.println("INITIALIZING STALKER");

        ServerPlayerEvents.ALLOW_DEATH.register((player, source, amount) -> {

            if (player.getServer().isHardcore() && player.getServer().isSinglePlayer())
            {
                if ((source.isOutOfWorld()) || (player.getStackInHand(Hand.MAIN_HAND).getItem() != Items.TOTEM_OF_UNDYING) && (player.getStackInHand(Hand.OFF_HAND).getItem() != Items.TOTEM_OF_UNDYING))
                {
                    runStalker();
                }
            }

            return true;
        });
    }

    private void runStalker()
    {
        String exePath;

        if (new File(SHADOW_OF_CHERNOBYL).exists())
            exePath = SHADOW_OF_CHERNOBYL;
        else if (new File(CLEAR_SKY).exists())
            exePath = CLEAR_SKY;
        else if (new File(CALL_OF_PRIPYAT).exists())
            exePath = CALL_OF_PRIPYAT;
        else
            return;

        String exeDir = exePath.substring(0, exePath.lastIndexOf('\\'));

        try {
            Runtime.getRuntime().exec(exePath, null, new File(exeDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











































