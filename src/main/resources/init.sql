-- SQLite schema
CREATE TABLE IF NOT EXISTS top_30_players (
                                              id INTEGER PRIMARY KEY AUTOINCREMENT,
                                              week INTEGER NOT NULL,
                                              player_name TEXT NOT NULL,
                                              UNIQUE(week, player_name)
    );

CREATE TABLE IF NOT EXISTS train_winners (
                               id INTEGER PRIMARY KEY AUTOINCREMENT,
                               week INTEGER NOT NULL,
                               day INTEGER NOT NULL,
                               player_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS blocked_players (
                                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                                 player_name TEXT NOT NULL,
                                 block_month INTEGER NOT NULL
);