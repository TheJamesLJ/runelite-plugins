package com.toxicnpcs;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("toxicnpcs")
public interface ToxicNpcsConfig extends Config
{
	@ConfigItem(
		keyName = "toxicNPCDebug",
		name = "Toxic NPC Debug",
		description = "Debug testing for toxic npcs"
	)
	default boolean toxicNPCDebug()
	{
		return false;
	}
}
