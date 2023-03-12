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
		description = "Debug testing for toxic npcs, check to trigger toxic npc message"
	)
	default boolean toxicNPCDebug()
	{
		return false;
	}

	@ConfigItem(
		keyName = "toxicNPCOverheadTextDuration",
		name = "Overhead Text Duration",
		description = "The duration in game ticks which the overhead text will display for"
	)
	default int toxicNPCOverheadTextDuration() { return 400; }

	@ConfigItem(
		keyName = "toxicNPCMaximum",
		name = "Maximum Toxic NPCs",
		description = "The maximum number of NPCs that will display an overhead text when a player dies"
	)
	default int toxicNPCMaximum() { return 50; }

	@ConfigItem(
			keyName = "toxicNPCShowDeathNotification",
			name = "Show Player Death Notification",
			description = "Displays a chat message to notify you if a player dies"
	)
	default boolean toxicNPCShowDeathNotification()
	{
		return true;
	}
}
