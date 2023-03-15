/*
 * Copyright (c) 2023, TheJamesLJ <https://github.com/TheJamesLJ>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.toxicnpcs.dialog;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Map;

@Getter
public enum UniqueNpc {
    NO_UNIQUE_DIALOG("NO_UNIQUE_DIALOG", -1, new String[]{
            "LMFAOOOOO no way you just died like that bro!!",
            "LUL",
            "Uninstall nerd",
            "Damn you suck",
            "Smited???",
            "lol",
            "Have you tried keeping your HP above 0??? lmaoooo",
            "Idiot",
            "Moron",
            "Nerd",
            "Sit",
            "Sit rat",
            "Ez fight",
            "Gf nerd",
            "Ty for bank kid",
            "Rat",
            "Free tele to Lumbridge",
            "Ez",
            "Ez fight",
            "Loooooooool",
            "Get rekt kid",
            "lmao",
            "Eat when?",
            "Cya",
            "Bye",
            "Nice bond account nerd",
            "RIP",
            "Use code 'Sit Rat' at checkout",
            "F",
            "F in chat",
            "Gf",
            "GG",
            "GG WP",
            "Skill issue",
            "Nice death nerd",
            "Nice pid",
            "Smited for spade?"}),
    DEAD_PLAYER("DEAD_PLAYER", -1, new String[]{"Pleae"}),
    TZTOK_JAD("TzTok-Jad", -1, new String[]{"TzTok-Sit-Rat"}),
    JALTOK_JAD("JalTok-Jad", -1, new String[]{"JalTok-Sit-Rat"}),
    TZKAL_ZUK("TzKal-Zuk", -1, new String[]{"TzKal-Sit-Rat"}),
    PENANCE_QUEEN("Penance Queen", -1, new String[]{"I am the alpha and the omega! The beginning and the end"}),
    GIANT_MOLE("Giant Mole", -1, new String[]{"Holy Mole-y!"}),
    GENERAL_GRAARDOR("General Graardor", -1, new String[]{"All glory to Bandos! Meat's back on the menu boys!"}),
    KREE_ARRA("Kree'arra", -1, new String[]{"Skreeeekt kid!"}),
    COMMANDER_ZILYANA("Commander Zilyana", -1, new String[]{"All praise Saradomin! Our enemy has been smited!"}),
    K_RIL_TSUTSAROTH("K'ril Tsutsaroth", -1, new String[]{"The Dark One has claimed their souls!"}),
    THE_MIMIC("The Mimic", -1, new String[]{"Go buy some more Treasure Hunter keys nerd!"})

    ;

    private final String npcName;
    private final int npcID;
    private final String[] uniqueDialog;

    UniqueNpc(final String npcName, final int npcID, String... uniqueDialog) {
        this.npcName = npcName;
        this.npcID = npcID;
        this.uniqueDialog = uniqueDialog;
    }

    private static final Map<String, UniqueNpc> NAME_MAP;

    static {
        ImmutableMap.Builder<String, UniqueNpc> builder = new ImmutableMap.Builder<>();
        for (final UniqueNpc n : values()) {
            String d;
            if (n.getNpcID() != -1) {
                d = Integer.toString(n.getNpcID());
            } else {
                d = n.getNpcName().toUpperCase();
            }
            builder.put(d, n);
        }
        NAME_MAP = builder.build();
    }

    public static boolean hasUniqueDialog(final String npcName) {
        return NAME_MAP.containsKey(npcName.toUpperCase());
    }

    @Nullable
    public static UniqueNpc getUniqueDialogByNpcName(final String npcName) {
        return NAME_MAP.get(npcName.toUpperCase());
    }

    @Nullable
    public static UniqueNpc getUniqueDialogByNpcID(final int npcID) {
        return NAME_MAP.get(Integer.toString(npcID));
    }

    @Nullable
    public String[] getUniqueDialog() {
        return this.uniqueDialog.length > 0 ? this.uniqueDialog : null;
    }

    @Nullable
    public String pickRandomDialog() {
        String dialogue = null;
        final String[] dialogues = this.getUniqueDialog();
        if (dialogues != null) {
            int roll = (int) Math.floor(Math.random() * dialogues.length);
            dialogue = dialogues[roll];
        }
        return dialogue;
    }
}
