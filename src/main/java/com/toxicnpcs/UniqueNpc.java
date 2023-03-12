package com.toxicnpcs;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Map;

@Getter
public enum UniqueNpc {
    NO_UNIQUE_DIALOG("NO_UNIQUE_DIALOG", -1, new String[] {
            "LMFAOOOOO no way you just died like that bro!!",
            "Uninstall nerd",
            "I hate you and you are an idiot",
            "lol",
            "Have you tried keeping your HP above 0??? lmaoooo",
            "Idiot",
            "Moron",
            "Sit",
            "Sit rat",
            "Ez fight",
            "Gf nerd",
            "Ty for bank kid",
            "Rat",
            "Free tele to Lumbridge",
            "Ez",
            "Loooooooool",
            "Get rekt kid",
            "lmao",
            "Cya",
            "Bye",
            "Nice bond account nerd"}),
    TZTOK_JAD("TzTok-Jad", -1, new String[] {"TzTok-Sit-Rat"}),
    JALTOK_JAD("JalTok-Jad", -1, new String[] {"JalTok-Sit-Rat"}),
    TZKAL_ZUK("TzKal-Zuk", -1, new String[] {"TzKal-Sit-Rat"}),
    PENANCE_QUEEN("Penance Queen", -1, new String[] {"I am the alpha and the omega! The beginning and the end"}),

    ;

    private final String npcName;
    private final int npcID;
    private final String[] uniqueDialog;

    UniqueNpc(final String npcName, final int npcID, String ... uniqueDialog) {
        this.npcName = npcName;
        this.npcID = npcID;
        this.uniqueDialog = uniqueDialog;
    }

    private static final Map<String, UniqueNpc> NAME_MAP;
    static
    {
        ImmutableMap.Builder<String, UniqueNpc> builder = new ImmutableMap.Builder<>();
        for (final UniqueNpc n : values())
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

    public static boolean hasUniqueDialog(final String npcName)
    {
        return NAME_MAP.containsKey(npcName.toUpperCase());
    }

    @Nullable
    public static UniqueNpc getUniqueDialogByNpcName(final String npcName)
    {
        return NAME_MAP.get(npcName.toUpperCase());
    }

    @Nullable
    public static UniqueNpc getUniqueDialogByNpcID(final int npcID) {
        return NAME_MAP.get(Integer.toString(npcID));
    }

    @Nullable
    public String[] getUniqueDialog()
    {
        return this.uniqueDialog.length > 0 ? this.uniqueDialog : null;
    }
}
