# Overview #
show leaderboard and achievements (google play game and game center, SDK)
 
[android, ios] [phonegap cli] [crosswalk]

requires google play developer account https://play.google.com/apps/publish/<br>
requires apple developer account https://developer.apple.com/devcenter/ios/index.action
# Server setting #
```c
[android] [crosswalk]

//add leaderboards and achievements
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get YOUR_GOOGLE_PLAY_GAME_APP_ID
google play developer console - Game services - [specific app] - get YOUR_GOOGLE_PLAY_GAME_APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The YOUR_GOOGLE_PLAY_GAME_APP_ID in this case is 12345678.)

[ios]

itunesconnect - Manage Your Apps - [specific app] - Manage Game Center - Enable for Single Game - Add Leaderboard - Leaderboard ID - don't need to wait for review to test
```
# Install phonegap plugin #

## Crosswalk ##
```c
<!-- caution: copy this to intelxdk.config.additions.xml and replace YOUR_GOOGLE_PLAY_GAME_APP_ID -->
<intelxdk:plugin intelxdk:name="game" intelxdk:value="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game" intelxdk:id="com.cranberrygame.phonegap.plugin.game">
	<intelxdk:param intelxdk:name="APP_ID" intelxdk:value="YOUR_GOOGLE_PLAY_GAME_APP_ID" />
</intelxdk:plugin>
```
![ScreenShot](https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/crosswalk_APP_ID.png)

<a href="https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/intelxdk.config.additions.xml">intelxdk.config.additions.xml</a>
## Phonegap cli ##
```c
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID
cordova plugin add com.cranberrygame.phonegap.plugin.game --variable APP_ID="YOUR_GOOGLE_PLAY_GAME_APP_ID"
```
## Phonegap build service (construct2 automatically add this tag to config.xml) ##
```c
not yet supported
```
# API #
```javascript
//
var leaderboardId = "REPLACE_THIS_WITH_YOUR_LEADERBOARD_ID";
var achievementId1 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID1";
var achievementId2 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID2";
var achievementId3 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID3";
var achievementId4 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID4";

//
document.addEventListener("deviceready", function(){
	window.game.setUp();

	//callback
	window.game.onLoginSucceeded = function(result) {
		var playerDetail = result;
        alert('onLoginSucceeded: ' + playerDetail['playerId'] + ' ' + playerDetail['playerDisplayName']);
    };	
    window.game.onLoginFailed = function() {
        alert('onLoginFailed');
    };
    window.game.onGetPlayerImageSucceeded = function(result) {
		var playerImageUrl = result;
        alert('onGetPlayerImageSucceeded: ' + playerImageUrl);
    };
    window.game.onGetPlayerImageFailed = function() {
        alert('onGetPlayerImageFailed');
    };	
    window.game.onGetPlayerScoreSucceeded = function(result) {
		var playerScore = result;
        alert('onGetPlayerScoreSucceeded: ' + playerScore);
    };
    window.game.onGetPlayerScoreFailed = function() {
        alert('onGetPlayerScoreFailed');
    };
	//	
    window.game.onSubmitScoreSucceeded = function() {
        alert('onSubmitScoreSucceeded');
    };	
    window.game.onSubmitScoreFailed = function() {
        alert('onSubmitScoreFailed');
    };	
	//	
    window.game.onUnlockAchievementSucceeded = function() {
        alert('onUnlockAchievementSucceeded');
    };  
    window.game.onUnlockAchievementFailed = function() {
        alert('onUnlockAchievementFailed');
    };
    window.game.onIncrementAchievementSucceeded = function() {
        alert('onIncrementAchievementSucceeded');
    };  
    window.game.onIncrementAchievementFailed = function() {
        alert('onIncrementAchievementFailed');
    };
    window.game.onResetAchievementsSucceeded = function() {
        alert('onResetAchievementsSucceeded');
    };	
    window.game.onResetAchievementsFailed = function() {
        alert('onResetAchievementsFailed');
    };
}, false);

//
window.game.login();
window.game.logout();
alert(window.game.isLoggedIn());
window.game.getPlayerImage();
window.game.getPlayerScore(leaderboardId);

//
window.game.submitScore(leaderboardId, 5);//leaderboardId, score
window.game.showLeaderboard(leaderboardId);

//
window.game.unlockAchievement(achievementId1);
window.game.unlockAchievement(achievementId2);
window.game.unlockAchievement(achievementId3);
window.game.unlockAchievement(achievementId4);
window.game.incrementAchievement(achievementId1, 2); //achievementId, incrementalStepOrCurrentPercent: Incremental step (android) or current percent (ios) for incremental achievement.
window.game.incrementAchievement(achievementId2, 2);
window.game.incrementAchievement(achievementId3, 2);
window.game.incrementAchievement(achievementId4, 2);
window.game.showAchievements();
window.game.resetAchievements();//only supported on ios
```
# Examples #
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/example/index.html">example/index.html</a><br>
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/crosswalk/mygame">crosswalk project example</a>
# Test #
[android] [crosswalk]

upload a signed (caution: signed) release APK to either alpha (recommended) or beta.

//test user<br>
google play developer console - Game services - [specific app] - test - add tester

//publish your app<br>
send email with https://play.google.com/apps/testing/YOUR_PACKAGE url to test user

wait until the url is available (take hours)

install app on a device from url or local signed apk with test account.

[ios]

just run
# How to build crosswalk and fix build error #

See https://dl.dropboxusercontent.com/u/186681453/howto/how_to_build_crosswalk_and_fix_build_error/index.html
# Free advertising for games/apps made with crosswalk related c2 plugins (+Phonegap) #

Reply this post, then I'll add your games/apps to the list: https://www.scirra.com/forum/viewtopic.php?t=115517
# Useful links #
This is the Google Play Game SDK screen on android. (like Game center on ios)<br>
Leaderboard screen capture<br>
https://developers.google.com/games/services/android/images/Leaderboard_Android.png<br>
Achievements screen capture<br>
https://developers.google.com/games/services/android/images/Achievements_Android.png

Bombardment - Battleship Duell (SDK version Example)<br>
https://play.google.com/store/apps/details?id=com.burpssgames.bombardment_free

Google Play Games - Leaderboard & Achievements<br>
https://www.scirra.com/tutorials/1010/google-play-games-leaderboard-achievements

Play Games Services Managment Tools<br>
https://github.com/playgameservices/management-tools

Crosswalk related c2 plugins (+Phonegap)<br>
https://www.scirra.com/forum/viewtopic.php?t=109586
# Credits #
