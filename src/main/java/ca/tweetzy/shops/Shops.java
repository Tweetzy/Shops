package ca.tweetzy.shops;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.core.PluginID;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 7:09 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Shops extends TweetyPlugin {

    private static Shops instance;

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        TweetyCore.registerPlugin(this, (int) PluginID.SHOPS.getTweetzyID(), "CHEST");

    }

    @Override
    public void onPluginDisable() {
        instance = null;
    }

    @Override
    public void onConfigReload() {

    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    public static Shops getInstance() {
        return instance;
    }
}
