package de.bergwerklabs.bewerbung.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;

/**
 * ItemBuilder for
 * @see ItemStack
 * by SpigotException
 *
 * version 1.0
 */
public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    /**
     * Constructor with params:
     *
     * @param itemStack -> ItemStack for item
     */
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Constructor with params:
     *
     * @param material -> Material for item
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * set the DisplayName for the item with params:
     *
     * @param name -> name which will be displayed
     * @return this class
     */
    public ItemBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    /**
     * set the subID/metaID for the item (if needed) with params:
     *
     * @param metaID -> sudID/metaID for item
     * @return this class
     */
    public ItemBuilder setMetaID(byte metaID) {
        itemStack.getData().setData(metaID);
        return this;
    }

    /**
     * set the amount of items with params:
     *
     * @param amount -> amount of items
     * @return this class
     */
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * set the durability of the item with params:
     *
     * @param durability -> durability to set
     * @return this class
     */
    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     * add enchantment to item with params:
     *
     * @param enchantment -> enchantment for the item
     * @param lvl         -> level of enchantment
     * @return this class
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int lvl) {
        this.itemMeta.addEnchant(enchantment, lvl, false);
        return this;
    }

    /**
     * remove all enchantments from the item
     *
     * @return this class
     */
    public ItemBuilder clearEnchantments() {
        this.itemMeta.getEnchants().forEach((enchantment, integer) -> this.itemMeta.removeEnchant(enchantment));
        return this;
    }

    /**
     * remove an enchantment from the item with params:
     *
     * @param enchantment -> enchantment which will be removed
     * @return this class
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        if (this.itemMeta.getEnchants().containsKey(enchantment))
            this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    /**
     * set the lore with an list and params:
     *
     * @param lines -> lines for lore
     * @return this class
     */
    public ItemBuilder setLore(List<String> lines) {
        this.itemMeta.setLore(lines);
        return this;
    }

    /**
     * set the lore with an array and params:
     *
     * @param lines -> lines for lore
     * @return this class
     */
    public ItemBuilder setLore(String... lines) {
        this.itemMeta.setLore(Arrays.asList(lines));
        return this;
    }

    /**
     * clears the lore of the item
     *
     * @return this class
     */
    public ItemBuilder resetLore() {
        this.itemMeta.getLore().clear();
        return this;
    }

    /**
     * @return the final ItemStack
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     *
     * @param unbreakable -> is item unbreakable
     * @return This class: @see me.luca.bowduell.utils.ItemBuilder
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemStack.getItemMeta().spigot().setUnbreakable(unbreakable);
        return this;
    }

    /**
     *
     * @param color -> color of the leather
     * @return This class: @see me.luca.bowduell.utils.ItemBuilder
     */
    public ItemBuilder setLeatherColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(color);
        itemStack.setItemMeta(meta);
        return this;
    }


}
