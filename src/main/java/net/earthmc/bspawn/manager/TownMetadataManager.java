package net.earthmc.bspawn.manager;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.LongDataField;

public class TownMetadataManager {
    private static final String toggledPublicOnAt = "bspawn_toggledpubliconat";

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
}
