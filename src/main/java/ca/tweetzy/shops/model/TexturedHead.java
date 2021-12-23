package ca.tweetzy.shops.model;

import lombok.experimental.UtilityClass;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:52 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class TexturedHead {

	public boolean isMinecraftTextureString(String value) {
		return value.startsWith("https://textures.minecraft.net/texture/");
	}

	public boolean isSkullsTexture(String value) {
		return value.toLowerCase().startsWith("skulls:");
	}

	public boolean isHeadDatabaseTexture(String value) {
		return value.toLowerCase().startsWith("hdb:");
	}
}
