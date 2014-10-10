# Overview #
```c
show leaderboard and achievements (google play game and game center, SDK)

[android, ios] [phonegap cli] [crosswalk for android]

requires google play developer account https://play.google.com/apps/publish/
requires apple developer account https://developer.apple.com/devcenter/ios/index.action
```
# Install phonegap plugin #

## Phonegap build service (config.xml) ##
```c

```
## Phonegap cli ##
```c
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID
cordova plugin add https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game#3550fc6472ba3657cbf83a8cc744a3071dfbb479 --variable APP_ID="YOUR_GOOGLE_PLAY_GAME_APP_ID"

[android]

//add leaderboards and achievements
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect - Manage Your Apps - [specific app] - Manage Game Center - Enable for Single Game - Add Leaderboard - Leaderboard ID - don't need to wait for review
```
## Crosswalk ##
```c
<!-- caution: copy this to intelxdk.config.additions.xml and replace YOUR_GOOGLE_PLAY_GAME_APP_ID -->
<intelxdk:plugin intelxdk:name="game" intelxdk:value="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game#3550fc6472ba3657cbf83a8cc744a3071dfbb479" intelxdk:id="com.cranberrygame.phonegap.plugin.game">
	<intelxdk:param intelxdk:name="APP_ID" intelxdk:value="YOUR_GOOGLE_PLAY_GAME_APP_ID" />
</intelxdk:plugin>

See crosswalk_APP_ID.png

[android] [crosswalk]

//add leaderboards and achievements
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect - Manage Your Apps - [specific app] - Manage Game Center - Enable for Single Game - Add Leaderboard - Leaderboard ID - don't need to wait for review
```
# API #
```javascript
//actions
Login
Logout
Submit score
Show leaderboard
Get player score
Submit achievement
Show achievements
Reset achievements (only supported on ios)

//events
On login succeeded
On login failed
On submit score succeeded
On submit score failed
On get player score succeeded
On get player score failed
On submit achievement succeeded
On submit achievement failed
On reset achievements succeeded
On reset achievements failed

//conditions
Is logged in
```
# Examples #
```c
example capx are included in doc folder
```
# Test #
```c
[android] [crosswalk for android]

upload a signed (caution: signed) release APK to either alpha (recommended) or beta.

//test user
google play developer console - Game services - [specific app] - test - add tester

//publish your app
send email with https://play.google.com/apps/testing/YOUR_PACKAGE url to test user

wait until the url is available (take hours)

install app on a device from url or local signed apk with test account.

[ios]

just run
```
# Useful links #
```c
Construct 2: a games/apps maker
https://www.scirra.com/

Phonegap related c2 plugins (+Crosswalk)
https://www.scirra.com/forum/viewtopic.php?t=109586

Games/Apps with phonegap related c2 plugins (+Crosswalk)
https://www.scirra.com/forum/viewtopic.php?f=148&t=115517

This is the Google Play Game SDK screen on android. (like Game center on ios)
Leaderboard screen capture
https://developers.google.com/games/services/android/images/Leaderboard_Android.png
Achievements screen capture
https://developers.google.com/games/services/android/images/Achievements_Android.png

Bombardment - Battleship Duell (SDK version Example)
https://play.google.com/store/apps/details?id=com.burpssgames.bombardment_free

Google Play Games - Leaderboard & Achievements
https://www.scirra.com/tutorials/1010/google-play-games-leaderboard-achievements

Play Games Services Managment Tools
https://github.com/playgameservices/management-tools
```
