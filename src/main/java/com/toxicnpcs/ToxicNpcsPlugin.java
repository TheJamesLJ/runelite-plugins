package com.toxicnpcs;

import com.google.common.base.MoreObjects;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ActorDeath;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import java.util.*;

@Slf4j
@PluginDescriptor(
	name = "Toxic NPCs"
)
public class ToxicNpcsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ToxicNpcsConfig config;

	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if(event.getKey().equals("toxicNPCDebug")) {
			if(config.toxicNPCDebug()) {
				makeNPCsToxic();
			}
		}
	}

	@Subscribe
	public void onActorDeath(ActorDeath e) {
		Actor actor = e.getActor();
		if(actor instanceof Player)
		{
			if(config.toxicNPCShowDeathNotification()){
				addPlayerDeathMessage(actor);
			}
			makeNPCsToxic();
		}
	}

	private void addPlayerDeathMessage(Actor actor) {
		if(actor.equals(client.getLocalPlayer())) {
			//Do nothing
		} else if(isActorFriend(actor)) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Your friend " + actor.getName() + " has died", null);
		} else {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Player " + actor.getName() + " has died", null);
		}
	}

	private boolean isActorFriend(Actor a) {
		boolean isFriend = false;

		Friend friends[] = client.getFriendContainer().getMembers();

		for (int x = 0; x != friends.length; x++) {
			if(a.getName() == friends[x].getName()) {
				isFriend = true;
			}
		}

		return isFriend;
	}

	private void makeNPCsToxic() {
		int iterator = 0;
		for(final NPC npc : client.getNpcs()) {
			if(IgnoreNpc.ignoreNpc(Text.escapeJagex(MoreObjects.firstNonNull(npc.getName(), "")))) {
				continue;
			}

			UniqueNpc uniqueNpc = UniqueNpc.getUniqueDialogByNpcID((npc.getId()));
			if (uniqueNpc == null) {
				uniqueNpc = UniqueNpc.getUniqueDialogByNpcName(Text.escapeJagex(MoreObjects.firstNonNull(npc.getName(), "")));
			}
			if(uniqueNpc == null) {
				uniqueNpc = UniqueNpc.getUniqueDialogByNpcName(Text.escapeJagex(MoreObjects.firstNonNull("NO_UNIQUE_DIALOG", "")));
			}

			if (uniqueNpc != null) {
				final String[] dialogues = uniqueNpc.getUniqueDialog();
				if (dialogues != null) {
					int roll = (int) Math.floor(Math.random() * dialogues.length);
					npc.setOverheadText(dialogues[roll]);
					npc.setOverheadCycle(config.toxicNPCOverheadTextDuration());
				}
			}

			iterator++;
			if(iterator >= config.toxicNPCMaximum()) {
				break;
			}
		}
	}

	@Provides
	ToxicNpcsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ToxicNpcsConfig.class);
	}
}
