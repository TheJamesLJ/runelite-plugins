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
package com.toxicnpcs;

import com.toxicnpcs.dialog.*;

import com.google.common.base.MoreObjects;
import com.google.inject.Provides;
import javax.inject.Inject;

import java.awt.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ActorDeath;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;


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

	@Inject
	private ChatMessageManager chatMessageManager;

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

			if(config.toxicNPCDeadPlayerPleaeChance() >= 0) {
				showDeadPlayerMessage(actor);
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
				final String dialogue = uniqueNpc.pickRandomDialog();
				if (dialogue != null) {
					setOverheadText(dialogue, npc);
				}
			}

			iterator++;
			if(iterator >= config.toxicNPCMaximum()) {
				break;
			}
		}
	}

	private void showDeadPlayerMessage(final Actor actor) {
		int roll = (int) Math.floor(Math.random() * config.toxicNPCDeadPlayerPleaeChance()) + 1;
		if(roll == config.toxicNPCDeadPlayerPleaeChance()) {
			UniqueNpc playerDialogue = UniqueNpc.getUniqueDialogByNpcName(Text.escapeJagex(MoreObjects.firstNonNull("DEAD_PLAYER", "")));
			if (playerDialogue != null) {
				final String dialogue = playerDialogue.pickRandomDialog();
				if (dialogue != null) {
					setOverheadText(dialogue, actor);
				}
			}
		}
	}

	private void setOverheadText(final String dialogue, final Actor actor) {
		actor.setOverheadText(dialogue);
		actor.setOverheadCycle(config.toxicNPCOverheadTextDuration());

		if(config.toxicNPCShowNPCMessageInChat()) {
			final ChatMessageBuilder message = new ChatMessageBuilder()
					.append("[")
					.append(Color.BLUE, "Toxic NPCs")
					.append("] ")
					.append(actor.getName())
					.append(": ")
					.append(Color.BLUE, dialogue);

			chatMessageManager.queue(QueuedMessage.builder()
					.type(ChatMessageType.GAMEMESSAGE)
					.runeLiteFormattedMessage(message.build())
					.build());
		}
	}

	@Provides
	ToxicNpcsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ToxicNpcsConfig.class);
	}
}
