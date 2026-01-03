package net.vlatko.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.vlatko.tutorialmod.Blocks.ModBlocks;
import net.vlatko.tutorialmod.Items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}