package net.earthmc.bspawn.manager;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.LongDataField;

public class TownMetadataManager {
    private static final String toggledPublicOnAt = "bspawn_toggledpubliconat";
    private static final String canOutsidersSpawn = "bspawn_canoutsidersspawn";

    public static Long getToggledPublicOnAt(Town town) {
        LongDataField ldf = (LongDataField) town.getMetadata(toggledPublicOnAt);
        if (ldf == null)
            return null;

        return ldf.getValue();
    }

    public static void setToggledPublicOnAt(Town town, Long value) {
        if (!town.hasMeta(toggledPublicOnAt))
            town.addMetaData(new LongDataField(toggledPublicOnAt, null));

        LongDataField ldf = (LongDataField) town.getMetadata(toggledPublicOnAt);
        if (ldf == null)
            return;

        ldf.setValue(value);
        town.addMetaData(ldf);
    }

    public static boolean getCanOutsidersSpawn(Town town) {
        BooleanDataField bdf = (BooleanDataField) town.getMetadata(canOutsidersSpawn);
        if (bdf == null)
            return false;

        return bdf.getValue();
    }

    public static void setCanOutsidersSpawn(Town town, boolean value) {
        if (!town.hasMeta(canOutsidersSpawn))
            town.addMetaData(new BooleanDataField(canOutsidersSpawn, null));

        BooleanDataField bdf = (BooleanDataField) town.getMetadata(canOutsidersSpawn);
        if (bdf == null)
            return;

        bdf.setValue(value);
        town.addMetaData(bdf);
    }
}
