package ca.tweetzy.shops.commands;

import ca.tweetzy.tweety.command.SimpleSubCommand;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:05 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class AbstractSubCommand extends SimpleSubCommand {

	public AbstractSubCommand(String sublabel) {
		super(ShopsCommand.getInstance(), sublabel);
	}
}
