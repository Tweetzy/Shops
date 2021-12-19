package ca.tweetzy.shops.commands;

import ca.tweetzy.tweety.annotation.AutoRegister;
import ca.tweetzy.tweety.command.SimpleCommandGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:03 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PluginTemplateCommand extends SimpleCommandGroup {

	@Getter
	private final static PluginTemplateCommand instance = new PluginTemplateCommand();

	@Override
	protected void registerSubcommands() {

	}

	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.YELLOW + ChatColor.BOLD;
	}

	@Override
	protected String getCredits() {
		return null;
	}
}
