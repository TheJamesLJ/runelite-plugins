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
