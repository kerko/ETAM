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

package nl.fontys.epic.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import nl.fontys.epic.TextAdventure;

/**
 * Simple implementation of {@see CommandHandler}
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class SimpleCommandHandler implements CommandHandler {
    
    private final Map<String, Command> commands;
    
    public SimpleCommandHandler() {
        commands = new HashMap<>();
    }

    @Override
    public void register(String identifier, Command command) {
        commands.put(identifier, command);
    }

    @Override
    public void handle(String commandString, TextAdventure adventure) throws CommandException {
        
        String[] args = commandString.split(" ");
        
        if (args.length == 0) {
            throw new CommandException("Command should not be empty");
        }
        
        Command command = commands.get(args[0]);
        
        if (command != null) {
            command.handle(reduceArgs(args), adventure);
        } else {
            throw new CommandException("Command '" + args[0] + "' not found.");
        }
        
        
    }

    @Override
    public int size() {
        return commands.size();
    }

    @Override
    public boolean isEmpty() {
        return commands.isEmpty();
    }
    
    
    static String[] reduceArgs(String[] args) {
        if (args.length <= 1)
            return new String[0];
        
        return Arrays.copyOfRange(args, 1, args.length);
    }
    
}
