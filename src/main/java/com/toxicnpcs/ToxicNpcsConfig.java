package com.toxicnpcs;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("toxicnpcs")
public interface ToxicNpcsConfig extends Config
{
	@ConfigItem(
			keyName = "toxicNPCMaximum",
			name = "Maximum Toxic NPCs",
			description = "The maximum number of NPCs that will display an overhead text when a player dies",
			position = 0
	)
	default int toxicNPCMaximum() { return 50; }

	@ConfigItem(
			keyName = "toxicNPCOverheadTextDuration",
			name = "Overhead Text Duration",
			description = "The duration in game ticks which the overhead text will display for",
			position = 1
	)
	default int toxicNPCOverheadTextDuration() { return 400; }

	@ConfigItem(
			keyName = "toxicNPCShowDeathNotification",
			name = "Show Player Death Notification",
			description = "Displays a chat message to notify you if a player dies",
			position = 2
	)
	default boolean toxicNPCShowDeathNotification()
	{
		return true;
	}

	@ConfigItem(
			keyName = "toxicNPCShowNPCMessageInChat",
			name = "Show Messages in Chat",
			description = "Displays a chat message for each toxic NPC that speaks",
			position = 3
	)
	default boolean toxicNPCShowNPCMessageInChat()
	{
		return true;
	}

	@ConfigItem(
			keyName = "toxicNPCDeadPlayerPleaeChance",
			name = "Dead Player \"Pleae\" Chance (1 in x)",
			description = "The chance the player that dies has \"Pleae\" display above them, set to 0 to disable",
			position = 4
	)
	default int toxicNPCDeadPlayerPleaeChance()
	{
		return 10;
	}

}
