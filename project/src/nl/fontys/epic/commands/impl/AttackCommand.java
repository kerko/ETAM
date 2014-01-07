/* The MIT License (MIT)
 * 
 * Copyright (c) 2013 Jan Kerkenhoff, Miguel Gonzalez
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package nl.fontys.epic.commands.impl;

import nl.fontys.epic.commands.Command;
import nl.fontys.epic.commands.CommandException;
import nl.fontys.epic.commands.Event;
import nl.fontys.epic.impl.SimpleTextAdventure;
import nl.fontys.epic.core.Creature;
import nl.fontys.epic.core.Inventory;
import nl.fontys.epic.core.Item;
import nl.fontys.epic.core.Player;
import nl.fontys.epic.core.Room;
import nl.fontys.epic.commands.Event.EventType;
import nl.fontys.epic.util.GameObjectPool;
import nl.fontys.epic.util.SharedGameObjectPool;

/**
 * {@see Command} implementation for attacking enemies.
 * 
 * @author Jan Kerkenhoff <j.kerkenhoff@student.fontys.nl>
 * @since 1.0
 * @version 1.0
 */
public class AttackCommand implements Command {

    @Override
    public Event handle(String[] args, SimpleTextAdventure adventure) throws CommandException {
        GameObjectPool manager = SharedGameObjectPool.getInstance(adventure.getName());
        if (args.length == 0) {
            throw new CommandException("You have to select a Target");
        }
        Creature creature = manager.get(args[0], Creature.class);
        if (creature == null) {
            throw new CommandException("You have to select a Creature");
        }
        Player player = adventure.getPlayer();
        int creatureHealth = creature.getLife();
        int playerHealth = creature.getLife();

        if (attack(player, creature)) {
            return this.processDeath(creature, adventure);           
        }
        if (attack(creature, player)) {
            return this.processDeath(player, adventure);
        }

        return new SimpleEvent("You hit " + creature.getName() + "for " + (creatureHealth - creature.getLife())
                + ". You took" + (playerHealth - player.getLife()) + 
                "Damage from" + creature.getName() + ".", EventType.INFO);

    }

    private boolean attack(Creature cr1, Creature cr2) {
        int damage = cr1.getStats().getPower();
        cr2.damage(damage);
        return cr2.isDead();
    }

    private SimpleEvent processDeath(Creature cr, SimpleTextAdventure adventure) {
        SimpleEvent response = new SimpleEvent(cr.getName() + " died,", EventType.INFO);
        response = drop(cr, adventure, response);
        cr.kill();
        return response;
    }

    private SimpleEvent drop(Creature cr, SimpleTextAdventure adventure, SimpleEvent response) {
        Room room = adventure.getCurrentRoom();

        Inventory items = cr.getInventory();
        if (!items.isEmpty()) {

            Inventory roomInventory = room.getItems(cr.getPosition().x, cr.getPosition().y);
            response.addEntry(cr.getName() + "dropped:");
            for (Item item : items.getItems()) {
                roomInventory.add(item);
                response.addEntry(item.getName());

            }

        }
        return response;

    }

}
