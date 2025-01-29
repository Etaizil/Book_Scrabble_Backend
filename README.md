# 📖 Book Scrabble Backend

## Overview

This Book Scrabble repo is a server-side application developed in Java as part of an advanced programming course. It facilitates a Scrabble-like game where players place tiles on a board to form words, each scoring based on placement. The project includes various components to manage game logic, caching, and dictionary operations.

## Prerequisites

- **JDK**: Version 11 or higher.
- **IDE**: Eclipse, IntelliJ IDEA, or any Java-compatible IDE.
- **Build Tool**: Apache Maven or Gradle for dependency management and building the project.

## Component Overview

- **Tile**: Represents individual game tiles, encapsulating properties like letter and point value.
- **Bag**: Manages the pool of available tiles, ensuring fair distribution to players.
- **Board**: Handles the game board, including tile placement and word formation.
- **Caching Strategies**: Implements LRU (Least Recently Used) and LFU (Least Frequently Used) caching mechanisms for efficient data retrieval.
- **Cache Manager**: Oversees caching operations, ensuring optimal performance and quick data access.
- **Dictionary**: Provides word validation.
- **IOSearcher**: Facilitates input/output operations for words.
- **ClientHandler**: Manages client connections and communication.
- **Server**: Coordinates the overall game flow and client interactions.

## Efficient Cache Searching

  The system incorporates both LRU (Least Recently Used) and LFU (Least Frequently Used) caching strategies to optimize data retrieval:
  - LRU Cache: Evicts the least recently accessed items when the cache reaches its capacity, ensuring that frequently accessed data remains readily available.
  - LFU Cache: Removes the least frequently accessed items, maintaining data that is accessed more often in the cache.

## Design Patterns

The project employs several design patterns to enhance code maintainability and scalability:

- **Singleton Pattern**: Ensures a single instance of the board, providing a global point of access.
- **Factory Pattern**: Simplifies object creation for different tile types, promoting loose coupling.
- **Observer Pattern**: Allows the Board to notify clients of game state changes, facilitating real-time updates.
- **Strategy Pattern**: Enables interchangeable caching strategies (LRU and LFU), allowing dynamic selection based on performance needs.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
