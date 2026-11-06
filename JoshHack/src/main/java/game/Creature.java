package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Creature {
	private World world;
	
	public int x;
	public int y;
	public int z;
	
	private char glyph;
	/**
	 * Returns the character symbol representing this creature.
	 * @return The glyph character of the creature.
	 */
	public char glyph() { return glyph; }
	
	private Color color;
	/**
	 * Returns the color used to display this creature.
	 * @return The Color of the creature.
	 */
	public Color color() { return color; }

	private CreatureAi ai;
	/**
	 * Assigns a new AI controller to this creature.
	 * @param ai The CreatureAi instance to control this creature.
	 */
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	private int maxHp;
	/**
	 * Gets the maximum health points this creature can have.
	 * @return The maximum hit points of the creature.
	 */
	public int maxHp() { return maxHp; }
	/**
	 * Changes the maximum health points by the given amount.
	 * @param amount The amount to add or subtract from maxHp.
	 */
	public void modifyMaxHp(int amount) { maxHp += amount; }
	
	private int hp;
	/**
	 * Gets the current health points of this creature.
	 * @return The current hit points of the creature.
	 */
	public int hp() { return hp; }
	
	private int attackValue;
	/**
	 * Adjusts the base attack value by the specified amount.
	 * @param value The value to add to the attack stat.
	 */
	public void modifyAttackValue(int value) { attackValue += value; }
	/**
	 * Calculates the total attack value including equipment bonuses.
	 * @return The combined attack value from base stats and equipped weapon and armor.
	 */
	public int attackValue() { 
		return attackValue
			+ (weapon == null ? 0 : weapon.attackValue())
			+ (armor == null ? 0 : armor.attackValue());
	}

	private int defenseValue;
	/**
	 * Adjusts the base defense value by the specified amount.
	 * @param value The value to add to the defense stat.
	 */
	public void modifyDefenseValue(int value) { defenseValue += value; }
	/**
	 * Calculates the total defense value including equipment bonuses.
	 * @return The combined defense value from base stats and equipped weapon and armor.
	 */
	public int defenseValue() { 
		return defenseValue
			+ (weapon == null ? 0 : weapon.defenseValue())
			+ (armor == null ? 0 : armor.defenseValue());
	}

	private int visionRadius;
	/**
	 * Changes the vision radius by the specified amount.
	 * @param value The amount to add or subtract to visionRadius.
	 */
	public void modifyVisionRadius(int value) { visionRadius += value; }
	/**
	 * Returns the current vision radius for sight calculations.
	 * @return The vision radius distance of the creature.
	 */
	public int visionRadius() { return visionRadius; }

	private String name;
	/**
	 * Returns the name of this creature.
	 * @return The creature's name as a String.
	 */
	public String name() { return name; }

	private Inventory inventory;
	/**
	 * Returns the inventory object holding this creature's items.
	 * @return The Inventory managing the creature's carried items.
	 */
	public Inventory inventory() { return inventory; }

	private int maxFood;
	/**
	 * Returns the maximum food level this creature can have.
	 * @return The maximum food capacity.
	 */
	public int maxFood() { return maxFood; }
	
	private int food;
	/**
	 * Returns the current food level of this creature.
	 * @return The current amount of food the creature has.
	 */
	public int food() { return food; }
	
	private Item weapon;
	/**
	 * Returns the currently wielded weapon item, if any.
	 * @return The Item used as the weapon or null if none.
	 */
	public Item weapon() { return weapon; }
	
	private Item armor;
	/**
	 * Returns the currently worn armor item, if any.
	 * @return The Item used as armor or null if none.
	 */
	public Item armor() { return armor; }
	
	private int xp;
	/**
	 * Returns the experience points of this creature.
	 * @return The experience points accumulated by the creature.
	 */
	public int xp() { return xp; }
	/**
	 * Increases or decreases the creature's experience points and handles level-ups.
	 * @param amount The amount of experience points to add or remove.
	 */
	public void modifyXp(int amount) { 
		xp += amount;
		
		notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);
		
		while (xp > (int)(Math.pow(level, 1.75) * 25)) {
			level++;
			doAction("advance to level %d", level);
			ai.onGainLevel();
			modifyHp(level * 2, "Died from having a negative level?");
		}
	}
	
	private int level;
	/**
	 * Returns the current level of this creature.
	 * @return The creature's current level.
	 */
	public int level() { return level; }
	
	private int regenHpCooldown;
	private int regenHpPer1000;
	/**
	 * Modifies the health regeneration rate by the given amount per 1000 ticks.
	 * @param amount Amount to adjust HP regeneration per 1000 units.
	 */
	public void modifyRegenHpPer1000(int amount) { regenHpPer1000 += amount; }
	
	private List<Effect> effects;
	/**
	 * Returns the list of active effects currently on this creature.
	 * @return A List of active Effect instances affecting the creature.
	 */
	public List<Effect> effects(){ return effects; }
	
	private int maxMana;
	/**
	 * Returns the maximum mana points this creature can have.
	 * @return The maximum mana capacity.
	 */
	public int maxMana() { return maxMana; }
	/**
	 * Changes the maximum mana points by the given amount.
	 * @param amount Amount to add or subtract from maxMana.
	 */
	public void modifyMaxMana(int amount) { maxMana += amount; }
	
	private int mana;
	/**
	 * Returns the current mana points of this creature.
	 * @return The creature's current mana value.
	 */
	public int mana() { return mana; }
	/**
	 * Executes modifyMana.
	 * @param maxMana) the input value
	 */
	public void modifyMana(int amount) { mana = Math.max(0, Math.min(mana+amount, maxMana)); }
	
	private int regenManaCooldown;
	private int regenManaPer1000;
	/**
	 * Modifies the mana regeneration rate by the given amount per 1000 ticks.
	 * @param amount Amount to adjust mana regeneration per 1000 units.
	 */
	public void modifyRegenManaPer1000(int amount) { regenManaPer1000 += amount; }
	
	private String causeOfDeath;
	/**
	 * Returns the cause of death for this creature, if dead.
	 * @return A String describing the cause of death.
	 */
	public String causeOfDeath() { return causeOfDeath; }
	
	/**
	 * Constructs a new Creature with specified properties at creation.
	 * @param world The world instance this creature belongs to.
	 * @param glyph The character representing the creature visually.
	 * @param color The color used to render the creature.
	 * @param name The name assigned to the creature.
	 * @param maxHp The maximum health points for the creature.
	 * @param attack The base attack value.
	 * @param defense The base defense value.
	 */
	public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.inventory = new Inventory(20);
		this.maxFood = 1000;
		this.food = maxFood / 3 * 2;
		this.level = 1;
		this.regenHpPer1000 = 10;
		this.effects = new ArrayList<Effect>();
		this.maxMana = 5;
		this.mana = maxMana;
		this.regenManaPer1000 = 20;
	}
	
	/**
	 * Moves the creature by the specified offsets including vertical changes and handles interactions.
	 * @param mx The amount to move in the x-direction.
	 * @param my The amount to move in the y-direction.
	 * @param mz The amount to move in the z-direction (vertical level).
	 */
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0)
			return;
		
		Tile tile = world.tile(x+mx, y+my, z+mz);
		
		if (mz == -1){
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}
		
		Creature other = world.creature(x+mx, y+my, z+mz);
		
		modifyFood(-1);
		
		if (other == null)
			ai.onEnter(x+mx, y+my, z+mz, tile);
		else
			meleeAttack(other);
	}

	/**
	 * Performs a melee attack on another creature.
	 * @param other The target creature to attack.
	 */
	public void meleeAttack(Creature other){
		commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
	}

	/**
	 * Performs a thrown attack on a target creature using an item.
	 * @param item The item being thrown.
	 * @param other The target creature being attacked.
	 */
	private void throwAttack(Item item, Creature other) {
		commonAttack(other, attackValue / 2 + item.thrownAttackValue(), "throw a %s at the %s for %d damage", nameOf(item), other.name);
		other.addEffect(item.quaffEffect());
	}
	
	/**
	 * Uses a ranged weapon to attack the specified creature.
	 * @param other The target creature to attack with ranged weaponry.
	 */
	public void rangedWeaponAttack(Creature other){
		commonAttack(other, attackValue / 2 + weapon.rangedAttackValue(), "fire a %s at the %s for %d damage", nameOf(weapon), other.name);
	}
	
	/**
	 * Executes a general attack on another creature with specified damage and action message.
	 * @param other The target creature to be attacked.
	 * @param attack The calculated attack damage value.
	 * @param action The action message format string describing the attack.
	 * @param params Additional parameters to format into the action message.
	 */
	private void commonAttack(Creature other, int attack, String action, Object ... params) {
		modifyFood(-2);
		
		int amount = Math.max(0, attack - other.defenseValue());
		
		amount = (int)(Math.random() * amount) + 1;
		
		Object[] params2 = new Object[params.length+1];
		for (int i = 0; i < params.length; i++){
			params2[i] = params[i];
		}
		params2[params2.length - 1] = amount;
		
		doAction(action, params2);
		
		other.modifyHp(-amount, "Killed by a " + name);
		
		if (other.hp < 1)
			gainXp(other);
	}
	
	/**
	 * Gains experience points from defeating another creature.
	 * @param other The creature from which to gain experience.
	 */
	public void gainXp(Creature other){
		int amount = other.maxHp 
			+ other.attackValue() 
			+ other.defenseValue()
			- level;
		
		if (amount > 0)
			modifyXp(amount);
	}

	/**
	 * Modifies the creature's health by the given amount and handles death logic with cause.
	 * @param amount The amount to change the creature's HP by.
	 * @param causeOfDeath A cause of death description if the creature dies from this modification.
	 */
	public void modifyHp(int amount, String causeOfDeath) {
		hp = CombatRules.calculateNewHp(hp, maxHp, amount);
		this.causeOfDeath = causeOfDeath;

		if (CombatRules.isDead(hp)) {
			doAction("die");
			leaveCorpse();
			world.remove(this);
		}
	}

	
	/**
	 * Creates a corpse item at the creature's location and drops all carried items.
	 */
	private void leaveCorpse(){
		Item corpse = new Item('%', color, name + " corpse", null);
		corpse.modifyFoodValue(maxHp * 5);
		world.addAtEmptySpace(corpse, x, y, z);
		for (Item item : inventory.getItems()){
			if (item != null)
				drop(item);
		}
	}
	
	/**
	 * Causes the creature to dig at the specified world coordinates, consuming food.
	 * @param wx The x-coordinate in the world to dig.
	 * @param wy The y-coordinate in the world to dig.
	 * @param wz The z-coordinate (level) in the world to dig.
	 */
	public void dig(int wx, int wy, int wz) {
		modifyFood(-10);
		world.dig(wx, wy, wz);
		doAction("dig");
	}
	
	/**
	 * Advances the creature's state including food consumption, regeneration, effects, and AI updates.
	 */
	public void update(){
		modifyFood(-1);
		regenerateHealth();
		regenerateMana();
		updateEffects();
		ai.onUpdate();
	}
	
	/**
	 * Updates all active effects on the creature and removes those that have ended.
	 */
	private void updateEffects(){
		List<Effect> done = new ArrayList<Effect>();
		
		for (Effect effect : effects){
			effect.update(this);
			if (effect.isDone()) {
				effect.end(this);
				done.add(effect);
			}
		}
		
		effects.removeAll(done);
	}
	
	/**
	 * Regenerates health over time if below max HP and applies corresponding food cost.
	 */
	private void regenerateHealth(){
		regenHpCooldown -= regenHpPer1000;
		if (regenHpCooldown < 0){
			if (hp < maxHp){
				modifyHp(1, "Died from regenerating health?");
				modifyFood(-1);
			}
			regenHpCooldown += 1000;
		}
	}

	/**
	 * Regenerates mana over time if below max mana and applies food cost.
	 */
	private void regenerateMana(){
		regenManaCooldown -= regenManaPer1000;
		if (regenManaCooldown < 0){
			if (mana < maxMana) {
				modifyMana(1);
				modifyFood(-1);
			}
			regenManaCooldown += 1000;
		}
	}
	
	/**
	 * Tests if the creature can enter a given tile based on ground and occupancy.
	 * @param wx X-coordinate of the tile to check.
	 * @param wy Y-coordinate of the tile to check.
	 * @param wz Z-level of the tile to check.
	 * @return True if ground and unoccupied, otherwise false.
	 */
	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	/**
	 * Sends a formatted notification message to the creature's AI.
	 * @param message The message format string.
	 * @param params Arguments referenced by the format specifiers in the message.
	 */
	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	/**
	 * Broadcasts a formatted action message to all creatures that can see this creature.
	 * @param message The action message format string.
	 * @param params Arguments for the format placeholders in the message.
	 */
	public void doAction(String message, Object ... params){
		for (Creature other : getCreaturesWhoSeeMe()){
			if (other == this){
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
		}
	}
	
	/**
	 * Broadcasts an action message involving an item to all creatures seeing this creature and teaches item name.
	 * @param item The item involved in the action.
	 * @param message The format string describing the action.
	 * @param params Arguments for the format string.
	 */
	public void doAction(Item item, String message, Object ... params){
		if (hp < 1)
			return;
		
		for (Creature other : getCreaturesWhoSeeMe()){
			if (other == this){
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
			other.learnName(item);
		}
	}
	
	/**
	 * Returns a list of creatures that can currently see this creature within vision radius.
	 * @return A List of Creature instances that have line of sight to this creature.
	 */
	private List<Creature> getCreaturesWhoSeeMe(){
		List<Creature> others = new ArrayList<Creature>();
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(x+ox, y+oy, z);
				
				if (other == null)
					continue;
				
				others.add(other);
			}
		}
		return others;
	}
	
	/**
	 * Converts an action description string to third person singular form for others.
	 * @param text The original action text in first person.
	 * @return The second-person conjugated string suitable for other creatures.
	 */
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
	
	/**
	 * Determines if this creature can see a specific location or creature in the world.
	 * @param wx World x-coordinate of the position to check.
	 * @param wy World y-coordinate of the position to check.
	 * @param wz World z-level of the position to check.
	 * @return True if this creature can see the specified tile or creature, false otherwise.
	 */
	public boolean canSee(int wx, int wy, int wz){
		return (detectCreatures > 0 && world.creature(wx, wy, wz) != null
				|| ai.canSee(wx, wy, wz));
	}

	/**
	 * Returns the actual tile at the specified coordinates ignoring visibility restrictions.
	 * @param wx World x-coordinate of the tile.
	 * @param wy World y-coordinate of the tile.
	 * @param wz World z-level of the tile.
	 * @return The Tile object at the given position in the world.
	 */
	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}
	
	/**
	 * Returns the tile at the position if visible, otherwise returns the remembered tile.
	 * @param wx World x-coordinate of the tile.
	 * @param wy World y-coordinate of the tile.
	 * @param wz World z-level of the tile.
	 * @return The Tile visible to the creature or remembered tile if unseen.
	 */
	public Tile tile(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.tile(wx, wy, wz);
		else
			return ai.rememberedTile(wx, wy, wz);
	}

	/**
	 * Returns the creature at the position if visible, otherwise null.
	 * @param wx World x-coordinate to check for a creature.
	 * @param wy World y-coordinate to check for a creature.
	 * @param wz World z-level to check for a creature.
	 * @return The Creature at the specified position if visible, else null.
	 */
	public Creature creature(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.creature(wx, wy, wz);
		else
			return null;
	}
	
	/**
	 * Attempts to pick up an item at the creature's current location if inventory space allows.
	 */
	public void pickup(){
		Item item = world.item(x, y, z);
		
		if (inventory.isFull() || item == null){
			doAction("grab at the ground");
		} else {
			doAction("pickup a %s", nameOf(item));
			world.remove(x, y, z);
			inventory.add(item);
		}
	}
	
	/**
	 * Drops the specified item into the world near the creature if possible.
	 * @param item The Item to drop from inventory.
	 */
	public void drop(Item item){
		if (world.addAtEmptySpace(item, x, y, z)){
			doAction("drop a " + nameOf(item));
			inventory.remove(item);
			unequip(item);
		} else {
			notify("There's nowhere to drop the %s.", nameOf(item));
		}
	}
	
	/**
	 * Adjusts the creature's food level by the specified amount and handles overeating or starvation.
	 * @param amount The amount to modify the food level by.
	 */
	public void modifyFood(int amount) { 
		food += amount;
		
		if (food > maxFood) {
			maxFood = (maxFood + food) / 2;
			food = maxFood;
			notify("You can't belive your stomach can hold that much!");
			modifyHp(-1, "Killed by overeating.");
		} else if (food < 1 && isPlayer()) {
			modifyHp(-1000, "Starved to death.");
		}
	}
	
	/**
	 * Determines if this creature is the player character based on its glyph.
	 * @return True if this creature represents the player, false otherwise.
	 */
	public boolean isPlayer(){
		return glyph == '@';
	}
	
	/**
	 * Consumes a food item to increase food and apply effects, notifying all players.
	 * @param item The food item to be eaten.
	 */
	public void eat(Item item){
		doAction("eat a " + nameOf(item));
		consume(item);
	}
	
	/**
	 * Consumes a drinkable potion item to apply effects, notifying all players.
	 * @param item The potion item to be quaffed.
	 */
	public void quaff(Item item){
		doAction("quaff a " + nameOf(item));
		consume(item);
	}
	
	/**
	 * Processes consumption of an item applying its effects and modifying food levels, removing it afterward.
	 * @param item The item to consume.
	 */
	private void consume(Item item){
		if (item.foodValue() < 0)
			notify("Gross!");
		
		addEffect(item.quaffEffect());
		
		modifyFood(item.foodValue());
		getRidOf(item);
	}
	
	/**
	 * Adds and activates a new effect on this creature if not null.
	 * @param effect The Effect instance to add.
	 */
	private void addEffect(Effect effect){
		if (effect == null)
			return;
		
		effect.start(this);
		effects.add(effect);
	}
	
	/**
	 * Removes the specified item from inventory and unequips it if equipped.
	 * @param item The item to remove and unequip.
	 */
	private void getRidOf(Item item){
		inventory.remove(item);
		unequip(item);
	}
	
	/**
	 * Places an item into the world at specified coordinates and removes it from inventory and equipment.
	 * @param item The item to place in the world.
	 * @param wx TODO
	 * @param wy TODO
	 * @param wz TODO
	 */
	private void putAt(Item item, int wx, int wy, int wz){
		inventory.remove(item);
		unequip(item);
		world.addAtEmptySpace(item, wx, wy, wz);
	}
	
	/**
	 * Removes the given item from equipped weapon or armor slots and notifies if alive.
	 * @param item The item to unequip.
	 */
	public void unequip(Item item){
		if (item == null)
			return;
		
		if (item == armor){
			if (hp > 0)
				doAction("remove a " + nameOf(item));
			armor = null;
		} else if (item == weapon) {
			if (hp > 0) 
				doAction("put away a " + nameOf(item));
			weapon = null;
		}
	}
	
	/**
	 * Equips an item as weapon or armor depending on stats, adding to inventory if needed.
	 * @param item The item to equip.
	 */
	public void equip(Item item){
		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify("Can't equip %s since you're holding too much stuff.", nameOf(item));
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}
		
		if (item.attackValue() == 0 && item.rangedAttackValue() == 0 && item.defenseValue() == 0)
			return;
		
		if (item.attackValue() + item.rangedAttackValue() >= item.defenseValue()){
			unequip(weapon);
			doAction("wield a " + nameOf(item));
			weapon = item;
		} else {
			unequip(armor);
			doAction("put on a " + nameOf(item));
			armor = item;
		}
	}
	
	/**
	 * Returns the item at the specified coordinates if visible.
	 * @param wx World x-coordinate to check.
	 * @param wy World y-coordinate to check.
	 * @param wz World z-level to check.
	 * @return The Item at the position if visible, else null.
	 */
	public Item item(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.item(wx, wy, wz);
		else
			return null;
	}
	
	/**
	 * Returns a formatted string with the creature’s level, attack, defense, and current HP.
	 * @return A string summarizing key stats of this creature.
	 */
	public String details() {
		return String.format("  level:%d  attack:%d  defense:%d  hp:%d", level, attackValue(), defenseValue(), hp);
	}
	
	/**
	 * Throws an item towards specified world coordinates, handles hits and effects, and places item accordingly.
	 * @param item The item being thrown.
	 * @param wx Target x-coordinate to throw the item toward.
	 * @param wy Target y-coordinate to throw the item toward.
	 * @param wz Target z-level to throw the item toward.
	 */
	public void throwItem(Item item, int wx, int wy, int wz) {
		Point end = new Point(x, y, 0);
		
		for (Point p : new Line(x, y, wx, wy)){
			if (!realTile(p.x, p.y, z).isGround())
				break;
			end = p;
		}
		
		wx = end.x;
		wy = end.y;
		
		Creature c = creature(wx, wy, wz);
		
		if (c != null)
			throwAttack(item, c);				
		else
			doAction("throw a %s", nameOf(item));
		
		if (item.quaffEffect() != null && c != null)
			getRidOf(item);
		else
			putAt(item, wx, wy, wz);
	}
	
	/**
	 * Adds another creature into the world controlled by this creature.
	 * @param other The creature to summon into the world.
	 */
	public void summon(Creature other) {
		world.add(other);
	}
	
	private int detectCreatures;
	/**
	 * Adjusts the creature's ability to detect other creatures by the given amount.
	 * @param amount Value to add or subtract from detection ability.
	 */
	public void modifyDetectCreatures(int amount) { detectCreatures += amount; }
	
	/**
	 * Attempts to cast a spell at given coordinates, applying its effect if the spell can be cast and mana is sufficient.
	 * @param spell The spell to be cast.
	 * @param x2 Target x-coordinate for the spell.
	 * @param y2 Target y-coordinate for the spell.
	 */
	public void castSpell(Spell spell, int x2, int y2) {
		Creature other = creature(x2, y2, z);
		
		if (spell.manaCost() > mana){
			doAction("point and mumble but nothing happens");
			return;
		} else if (other == null) {
			doAction("point and mumble at nothing");
			return;
		}
		
		other.addEffect(spell.effect());
		modifyMana(-spell.manaCost());
	}
	
	/**
	 * Retrieves the known or learned name of an item from the creature's AI perspective.
	 * @param item The item whose name is requested.
	 * @return A string representing the recognized name of the item.
	 */
	public String nameOf(Item item){
		return ai.getName(item);
	}
	
	/**
	 * Learns and stores the true name of an item, notifying the creature.
	 * @param item The item whose name is to be learned.
	 */
	public void learnName(Item item){
		notify("The " + item.appearance() + " is a " + item.name() + "!");
		ai.setName(item, item.name());
	}
}
