package nl.fontys.epic.commands;
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

import nl.fontys.epic.TextAdventure;

/**
 * Handles {@see Command} objects and delegates commands to them.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface CommandHandler {
    
    /**
     * Registers a new command
     * 
     * @param identifier command identifier
     * @param command command to add
     */
    void register(String identifier, Command command);   
    
    /**
     * Handles all commands
     * 
     * @param commandString
     * @param adventure 
     * @return response
     */
    CommandResponse handle(String commandString, TextAdventure adventure);
    
    /**
     * Determines the amount of commands in the handler
     * 
     * @return 
     */
    int size();
    
    /**
     * Determines if the handler is currently empty
     * 
     * @return 
     */
    boolean isEmpty();
}
