# Overview #
show leaderboard and achievements (google play game and game center, SDK)

[android, ios] [phonegap cli] [crosswalk]

requires google play developer account https://play.google.com/apps/publish/<br>
requires apple developer account https://developer.apple.com/devcenter/ios/index.action
# Install phonegap plugin #

## Phonegap build service (config.xml) ##
not yet supported
## Phonegap cli ##
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID<br>
cordova plugin add https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game --variable APP_ID="YOUR_GOOGLE_PLAY_GAME_APP_ID"

[android]

//add leaderboards and achievements<br>
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID<br>
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect - Manage Your Apps - [specific app] - Manage Game Center - Enable for Single Game - Add Leaderboard - Leaderboard ID - don't need to wait for review
## Crosswalk ##
```c
<!-- caution: copy this to intelxdk.config.additions.xml and replace YOUR_GOOGLE_PLAY_GAME_APP_ID -->
<intelxdk:plugin intelxdk:name="game" intelxdk:value="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game#3550fc6472ba3657cbf83a8cc744a3071dfbb479" intelxdk:id="com.cranberrygame.phonegap.plugin.game">
	<intelxdk:param intelxdk:name="APP_ID" intelxdk:value="YOUR_GOOGLE_PLAY_GAME_APP_ID" />
</intelxdk:plugin>
```
![ScreenShot](https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/crosswalk_APP_ID.png)

<a href="https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/intelxdk.config.additions.xml">intelxdk.config.additions.xml</a>

[android] [crosswalk]

//add leaderboards and achievements<br>
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID<br>
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect - Manage Your Apps - [specific app] - Manage Game Center - Enable for Single Game - Add Leaderboard - Leaderboard ID - don't need to wait for review

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
    window.game.onSubmitAchievementSucceeded = function() {
        alert('onSubmitAchievementSucceeded');
    };  
    window.game.onSubmitAchievementFailed = function() {
        alert('onSubmitAchievementFailed');
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
window.game.submitAchievement(achievementId1, 100);//achievementId, percent
window.game.submitAchievement(achievementId2, 100);//achievementId, percent
window.game.submitAchievement(achievementId3, 100);//achievementId, percent
window.game.submitAchievement(achievementId4, 100);//achievementId, percent
window.game.showAchievements();
window.game.resetAchievements();//only supported on ios
```
# Examples #
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/example/index.html">example/index.html</a>
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
# Useful links #
Construct 2: a games/apps maker<br>
https://www.scirra.com/

Phonegap related c2 plugins (+Crosswalk)<br>
https://www.scirra.com/forum/viewtopic.php?t=109586

Games/Apps with phonegap related c2 plugins (+Crosswalk)<br>
https://www.scirra.com/forum/viewtopic.php?f=148&t=115517

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
```
