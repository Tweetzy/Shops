package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.helper.InventorySafeMaterials;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.impl.shop.ServerShop;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public final class PrefillCommand extends Command {

	public PrefillCommand() {
		super(AllowedExecutor.BOTH, "prefill");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (args.length < 1) {
			tellNoPrefix(sender, "&4Please use &c/shops prefill confirm &4to execute");
			tellNoPrefix(sender, "&eBy doing this you acknowledge that any existing shop will either be deleted/cleared and re-populated with template items. The items that go into the shop &4MAY NOT BE &ecorrectly categorized. All prices and minimum quantities are also set to the same values regardless of what it is.");
			return ReturnType.FAIL;
		}


		final boolean confirmed = args[0].equalsIgnoreCase("confirm");

		if (!confirmed) return ReturnType.FAIL;

		if (!Shops.getShopManager().getValues().isEmpty()) {
			// yeet shops
			for (Shop shop : Shops.getShopManager().getValues()) {
				shop.unStore(result -> {
				});
			}
		}

		// create base shops

		// FOOD
		Shop foodShop = new ServerShop("food");
		foodShop.getShopOptions().setDisplayIcon(new ItemStack(Material.CAKE, 1));

		Shops.getShopManager().create(foodShop, created -> {
			if (created) {
				for (Material material : Material.values()) {
					if (material == null || material == Material.AIR || !material.isEdible()) continue;
					foodShop.addContent(new ItemShopContent(UUID.randomUUID(), foodShop.getId(), new ItemStack(material, 1), 1, 1, 0.5));
				}
			}
		});

		// BLOCKS
		Shop blockShop = new ServerShop("blocks");
		blockShop.getShopOptions().setDisplayIcon(new ItemStack(Material.GRASS_BLOCK, 1));

		Shops.getShopManager().create(blockShop, created -> {
			if (created) {
				for (CompMaterial safe : InventorySafeMaterials.get()) {
					if (safe.parseMaterial().isEdible() || !safe.parseMaterial().isBlock()) continue;
					blockShop.addContent(new ItemShopContent(UUID.randomUUID(), blockShop.getId(), safe.parseItem(), 1, 1, 0.5));
				}
			}
		});

		// ARMOR
		Shop armorShop = new ServerShop("armor");
		armorShop.getShopOptions().setDisplayIcon(new ItemStack(Material.DIAMOND_HELMET, 1));

		Shops.getShopManager().create(armorShop, created -> {
			if (created) {
				for (Material material : Material.values()) {
					if (material == null || material == Material.AIR || material.isEdible() || !isArmor(material)) continue;
					armorShop.addContent(new ItemShopContent(UUID.randomUUID(), armorShop.getId(), new ItemStack(material, 1), 1, 1, 0.5));
				}
			}
		});

		Shop weaponsShop = new ServerShop("weapons");
		weaponsShop.getShopOptions().setDisplayIcon(new ItemStack(Material.IRON_SWORD, 1));

		Shops.getShopManager().create(weaponsShop, created -> {
			if (created) {
				for (Material material : Material.values()) {
					if (material == null || material == Material.AIR || !isWeapon(material)) continue;
					weaponsShop.addContent(new ItemShopContent(UUID.randomUUID(), weaponsShop.getId(), new ItemStack(material, 1), 1, 1, 0.5));
				}
			}
		});

		Shop toolShop = new ServerShop("tools");
		toolShop.getShopOptions().setDisplayIcon(new ItemStack(Material.NETHERITE_PICKAXE, 1));

		Shops.getShopManager().create(toolShop, created -> {
			if (created) {
				for (Material material : Material.values()) {

					if (material == null || material == Material.AIR || !isTool(material)) continue;
					toolShop.addContent(new ItemShopContent(UUID.randomUUID(), toolShop.getId(), new ItemStack(material, 1), 1, 1, 0.5));
				}
			}
		});

		Shop potionsShop = new ServerShop("potions");
		potionsShop.getShopOptions().setDisplayIcon(new ItemStack(Material.LINGERING_POTION, 1));

		Shops.getShopManager().create(potionsShop, created -> {
			if (created) {
				potionsShop.addContent(new ItemShopContent(UUID.randomUUID(), toolShop.getId(), QuickItem.of(CompMaterial.POTION).name("&ePotions Are Weird").lore("&7Since there are a bunch of variants, you will", "&7have to add them in manually.").make(), 1, 1, 0.5));
			}
		});

		Shop raidingShop = new ServerShop("raiding");
		raidingShop.getShopOptions().setDisplayIcon(new ItemStack(Material.TNT, 1));

		Shops.getShopManager().create(raidingShop, created -> {
			if (created) {
				raidingShop.addContent(new ItemShopContent(UUID.randomUUID(), raidingShop.getId(), new ItemStack(Material.TNT, 1), 1, 1, 0.5));
				raidingShop.addContent(new ItemShopContent(UUID.randomUUID(), raidingShop.getId(), new ItemStack(Material.TNT_MINECART, 1), 1, 1, 0.5));
			}
		});

		Shop redstoneShop = new ServerShop("redstone");
		redstoneShop.getShopOptions().setDisplayIcon(new ItemStack(Material.REDSTONE, 1));

		Shops.getShopManager().create(redstoneShop, created -> {
			if (created) {
				for (Material material : Material.values()) {
					if (material == null || material == Material.AIR || !isRedstone(material)) continue;
					redstoneShop.addContent(new ItemShopContent(UUID.randomUUID(), redstoneShop.getId(), new ItemStack(material, 1), 1, 1, 0.5));
				}
			}
		});

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.prefill";
	}

	@Override
	public String getSyntax() {
		return "prefill <confirm>";
	}

	@Override
	public String getDescription() {
		return "Used quickly create template categories and populate";
	}

	private boolean isArmor(final Material material) {
		final String typeNameString = material.name();
		return typeNameString.endsWith("_HELMET")
				|| typeNameString.endsWith("_CHESTPLATE")
				|| typeNameString.endsWith("_LEGGINGS")
				|| typeNameString.endsWith("_BOOTS");
	}

	private boolean isWeapon(final Material material) {
		final String typeNameString = material.name();
		return typeNameString.endsWith("_SWORD")
				|| typeNameString.endsWith("_AXE")
				|| material == Material.BOW
				|| material == Material.CROSSBOW
				|| material == Material.TRIDENT
				|| material == Material.ARROW
				|| material == Material.SHIELD;
	}

	private boolean isTool(final Material material) {
		final String typeNameString = material.name();
		return typeNameString.endsWith("_HOE")
				|| typeNameString.endsWith("_SHOVEL")
				|| typeNameString.endsWith("_PICKAXE")
				|| material == Material.FISHING_ROD
				|| material == Material.COMPASS
				|| material == Material.CLOCK
				|| material == Material.LEAD
				|| material == Material.SHEARS
				|| typeNameString.contains("BUCKET");
	}

	private boolean isRedstone(final Material material) {
		final String typeNameString = material.name();
		return material == Material.REDSTONE
				|| typeNameString.endsWith("_PLATE")
				|| typeNameString.endsWith("_BUTTON")
				|| material == Material.REDSTONE_BLOCK
				|| material == Material.REDSTONE_LAMP
				|| material == Material.REDSTONE_TORCH
				|| material == Material.REPEATER
				|| material == Material.COMPARATOR
				|| material == Material.LEVER
				|| material == Material.HOPPER
				|| material == Material.OBSERVER
				|| material == Material.STICKY_PISTON
				|| material == Material.PISTON
				|| material == Material.SLIME_BLOCK
				|| material == Material.HONEY_BLOCK
				|| material == Material.ACTIVATOR_RAIL
				|| material == Material.POWERED_RAIL
				|| material == Material.DETECTOR_RAIL
				|| material == Material.DROPPER
				|| material == Material.DISPENSER
				|| material == Material.SHIELD;
	}
}
