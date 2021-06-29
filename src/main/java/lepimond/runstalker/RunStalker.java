package lepimond.runstalker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.client.MinecraftClient;
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
        //Код срабатывает только в том случае, если игра регистрирует получение смертельного урона
        ServerPlayerEvents.ALLOW_DEATH.register((player, source, amount) -> {

            if (player.getServer().isHardcore() && player.getServer().isSinglePlayer()) //Проверка на то, находится ли Мэл в своём хардкорном синглплеере (есть проверка на мультиплеер, а то вдруг Мэл зайдёт на какой-нибудь сервер типа "Трёх жизней", который значится, как хардкорный, кто-нибудь рядмо с ним умрёт, и ОПА, сталкер. Но по локалке всё равно можно мир раздать, мод всё равно на хардкорный сингл сработает, я проверял)
            {
                //Проверка на то, должен ли Мэл умереть (есть ли в руке тотем бессмертия / поулчен ли урон от пустоты - на неё тотем не работает в ванилле)
                if ((source.isOutOfWorld()) || (player.getStackInHand(Hand.MAIN_HAND).getItem() != Items.TOTEM_OF_UNDYING) && (player.getStackInHand(Hand.OFF_HAND).getItem() != Items.TOTEM_OF_UNDYING))
                {
                    runStalker(); //Запуск Сталкера
                    MinecraftClient.getInstance().close(); //Закрытие Майнкрафта
                }
            }

            return true;
        });
    }

    private void runStalker()
    {
        String exePath;

        //Наибольший приоритет запуска - у "Тени Чернобыля" (2007). Если она не установлена, то запустится "Чистое небо" (2008), а если не установлено и "Чистое небо" - то запустится "Зов Припяти" (2009)
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











































