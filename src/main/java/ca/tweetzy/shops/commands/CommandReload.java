package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 10:48 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandReload extends AbstractSubCommand{

	public CommandReload() {
		super("reload|rl");
		setDescription("Reload the plugin");
	}

	@Override
	protected void onCommand() {
		Shops.getInstance().reload();
		tell("&aReloaded shops successfully");
	}
}
