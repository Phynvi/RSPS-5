package com.vencillio.rs2.content.dialogue.impl;

import com.vencillio.rs2.content.dialogue.Dialogue;
import com.vencillio.rs2.content.dialogue.DialogueConstants;
import com.vencillio.rs2.content.dialogue.DialogueManager;
import com.vencillio.rs2.content.dialogue.Emotion;
import com.vencillio.rs2.entity.player.Player;
import com.vencillio.rs2.entity.player.net.out.impl.SendEnterString;
import com.vencillio.rs2.entity.player.net.out.impl.SendRemoveInterfaces;

/**
 * Handles Gambler dialogue
 *
 * @author Daniel
 */
public class GamblerDialogue extends Dialogue {

	private int option = 0;

	public GamblerDialogue(Player player) {
		this.player = player;
	}

	@Override
	public boolean clickButton(int id) {

		switch (id) {

			case DialogueConstants.OPTIONS_4_1:
				if(option == 2)
					DoubleLottoGame.playGame(player, "100000");
				else
					player.start(new FlowerGameDialogue(player));
				break;

			case DialogueConstants.OPTIONS_4_2:
				if(option == 2)
					DoubleLottoGame.playGame(player, "1000000");
				else {
					setNext(5);
					execute();
				}
				break;

			case DialogueConstants.OPTIONS_4_3:
				if(option == 2)
					DoubleLottoGame.playGame(player, "10000000");
				else {
					setNext(2);
					execute();
				}
				break;

			case DialogueConstants.OPTIONS_4_4:
				if(option == 2) {
					setNext(4);
					execute();
				}
				else
					player.send(new SendRemoveInterfaces());
				break;
			case 9182:
				player.send(new SendRemoveInterfaces());
				break;
		}

		return false;
	}

	@Override
	public void execute() {

		switch (next) {

			case 0:
				DialogueManager.sendNpcChat(player, 1011, Emotion.HAPPY_TALK, "Hello " + player.getUsername() + "!", "I am in charge of all gambling done in Tannerscape.");
				next++;
				break;

			case 1:
				DialogueManager.sendOption(player, "Flower Game", "Lottery", "Play 55x2", "Nevermind");
				break;

			case 2:
				DialogueManager.sendNpcChat(player, 1011, Emotion.HAPPY_TALK, "How much would you like to bet?");
				next++;
				break;

			case 3:
				option = 2;
				DialogueManager.sendOption(player, "100k", "1m", "10m", "Custom", "Nevermind");
				break;

			case 4:
				player.setEnterXInterfaceId(55559);
				player.getClient().queueOutgoingPacket(new SendEnterString());
				break;

			case 5:
				player.start(new LotteryDialogue(player));
				break;

		}

	}

}