package org.openhab.binding.rwe;

import java.util.List;

import org.openhab.binding.rwe.internal.binding.config.RweBindingConfig;
import org.openhab.core.binding.BindingProvider;
import org.openhab.core.items.Item;

public interface RweBindingProvider extends BindingProvider{

	/**
	 * Returns a list of items for the specified bindingConfig.
	 */
	public List<Item> getItemsFor(RweBindingConfig bindingConfig);

	/**
	 * Returns the item object by itemName.
	 */
	public Item getItem(String itemName);

	/**
	 * Returns the bindingConfig by itemName.
	 */
	public RweBindingConfig getBindingFor(String itemName);
}
