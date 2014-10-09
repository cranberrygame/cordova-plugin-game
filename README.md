## Prerequisite ##

<pre>
</pre>

## How to Install ##

<pre>
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID
cordova plugin add https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game --variable APP_ID="YOUR_GOOGLE_PLAY_GAME_APP_ID"

[android]

//add leaderboards and achievements
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect-Manage Your Apps-[specific app]-Manage Game Center-Enable for Single Game-Add Leaderboard-Leaderboard ID-don't need to wait for review
</pre>

## How to install (crosswalk) ##

![ScreenShot](https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/crosswalk_APP_ID.png)

<a href="https://raw.githubusercontent.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/master/example/intelxdk.config.additions.xml">intelxdk.config.additions.xml</a>

<pre>
[crosswalk for android, android]

//add leaderboards and achievements
google play developer console - Game services - Add a new game - Enter the name of your game, choose its category, and click Continue. - ... - add leaderboards and achievements

//get APP_ID
google play developer console - Game services - [specific app] - get APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "My Super Game - 12345678",. The APP_ID in this case is 12345678.)

[ios]

itunesconnect-Manage Your Apps-[specific app]-Manage Game Center-Enable for Single Game-Add Leaderboard-Leaderboard ID-don't need to wait for review
</pre>

## Change log ##

<pre>
</pre>

## To-Do ##

<pre>
</pre>	

## How to Use ##

<pre>
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
</pre>

## Example ##

<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/example/index.html">example/index.html</a>
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/crosswalk/mygame">crosswalk project example</a>

## How to test ##

<pre>
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
</pre>

## Download construct2 plugin ##

<a href="https://www.scirra.com/forum/viewtopic.php?t=109586">Phonegap related plugins (+Crosswalk)</a>

## Support ##

This is 100% Free.<br>
<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=F9MJ2UY9EKXRN&lc=KR&item_name=Phonegap%20game%20plugin%20donation&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted">You can support maintenance by donation</a>

