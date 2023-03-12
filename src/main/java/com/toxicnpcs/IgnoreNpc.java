package com.toxicnpcs;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.runelite.client.util.Text;

import javax.annotation.Nullable;
import java.util.Map;

@Getter
public enum IgnoreNpc {
    PORTAL("Portal", -1)

    ;

    private final String npcName;
    private final int npcID;

    IgnoreNpc(final String npcName, final int npcID) {
        this.npcName = npcName;
        this.npcID = npcID;
    }

    private static final Map<String, IgnoreNpc> NAME_MAP;
    static
    {
        ImmutableMap.Builder<String, IgnoreNpc> builder = new ImmutableMap.Builder<>();
        for (final IgnoreNpc n : values())
        {
            String d;
            if(n.getNpcID() != -1){
                d = Integer.toString(n.getNpcID());
            }
            else{
                d = n.getNpcName().toUpperCase();
            }
            builder.put(d, n);
        }
        NAME_MAP = builder.build();
    }

    public static boolean ignoreNpc(final String npcName)
    {
        return NAME_MAP.containsKey(npcName.toUpperCase());
    }
}
