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
                                             day TEXT NOT NULL,
                                             player_name TEXT NOT NULL,
                                             UNIQUE(week, day, player_name)
    );

CREATE TABLE IF NOT EXISTS blocked_players (
                                               id INTEGER PRIMARY KEY AUTOINCREMENT,
                                               player_name TEXT NOT NULL,
                                               block_week INTEGER NOT NULL,
                                               UNIQUE(player_name, block_week)
    );

-- New table for win streaks
CREATE TABLE IF NOT EXISTS player_win_streaks (
                                                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                  player_name TEXT NOT NULL,
                                                  win_streak INTEGER NOT NULL DEFAULT 1,
                                                  UNIQUE(player_name)
);