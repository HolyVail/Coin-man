🪙 CoinMan

CoinMan is a simple and addictive 2D arcade-style game built using LibGDX, where the player controls a running character who collects coins and avoids bombs. The game showcases basic game development principles such as sprite animation, collision detection, scoring, and persistent high scores.

🎮 Gameplay Overview

Tap the screen to make the character jump.

Collect coins to increase your score.

Avoid bombs — hitting one ends the game.

The game keeps track of your high score using LibGDX's Preferences system.

🛠️ Tech Stack

Language: Java

Framework: LibGDX

Audio: .mp3 files played using LibGDX's audio API

Graphics: PNG textures for characters, bombs, coins, background

🧠 Features

🧍‍♂️ Sprite animation for the player

💥 Collision detection using rectangles and Intersector

📈 Score and high score tracking using Preferences

🔊 Integrated sound effects

💨 Physics-based jump system using gravity and velocity

💾 Persistent high score between sessions

🚀 How to Run

Install LibGDX setup tools (via gdx-setup.jar).

Import the project into your preferred Java IDE (like IntelliJ or Eclipse).

Ensure assets are placed in the correct android/assets/ or equivalent directory.

Run the application from the desktop launcher or Android emulator/device.

📱 Controls

Tap screen / Click mouse → Make the character jump

📝 Notes

Sound is played each frame — you might want to optimize by only playing on events.

Asset loading is currently done in create() method — for larger projects, consider using AssetManager.

🔓 License

This project is open-source and free to use for educational purposes. Attribution appreciated!
