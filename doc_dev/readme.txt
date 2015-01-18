1.

1)android

https://developers.google.com/games/services/android/quickstart
https://developers.google.com/games/services/console/enabling#application_id
https://developers.google.com/games/services/
https://developers.google.com/games/services/downloads/#samples

https://github.com/playgameservices/android-basic-samples
https://github.com/playgameservices/android-basic-samples/tree/master/BasicSamples/TypeANumber/src/main
https://github.com/playgameservices/android-basic-samples/tree/master/BasicSamples/libraries/BaseGameUtils/src/main

업버전 V
https://github.com/MobileChromeApps/google-play-services/tree/6fe0d977f12948099605a723c954cecfbbfa3742
https://github.com/MobileChromeApps/google-play-services#6fe0d977f12948099605a723c954cecfbbfa3742
Since the Google Play services update to version 4.3, GamesClient is no longer available. You should switch to GoogleApiClient instead.
https://github.com/hyperfiction/HypPlay_services/issues/7
com.google.android.gms.games.GamesClient cannot be resolved
http://stackoverflow.com/questions/22825940/com-google-android-gms-games-gamesclient-cannot-be-resolved

Cordova plugin for Google Game Services
https://github.com/aogilvie/phonegap-plugin-googleGameServices

package android.support.v4.util does not exist
Make sure you have downloaded the Android Support Library using the SDK Manager.
Create a libs/ directory in the root of your application project.
Copy the JAR file from your Android SDK installation directory (e.g., <sdk>/extras/android/support/v4/android-support-v4.jar) into your application's project libs/ directory.
C:\Users\win\adt-bundle-windows-x86_64-20140321\sdk\extras\android\support\v4 V
http://stackoverflow.com/questions/16621476/package-android-support-v4-util-does-not-exist

2)ios

Wizcorp/phonegap-plugin-gameCenter
https://github.com/Wizcorp/phonegap-plugin-gameCenter
https://github.com/Wizcorp/phonegap-plugin-gameCenter/tree/master/platforms/ios/HelloCordova

2.code

<gap:plugin name="com.cranberrygame.phonegap.plugin.game" version="1.0.0">
    <param name="APP_ID" value="1064334934918" />
</gap:plugin>

cordova plugin add https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game --variable APP_ID="1064334934918"

cordova plugin add D:\helper\data\phonegapcli_cordova_plugin_add\com.cranberrygame.phonegap.plugin.game --variable APP_ID="1064334934918"

    <preference name="APP_ID" />
	
http://stackoverflow.com/questions/22576209/phonegap-build-facebook-connect-plugin-invalid-android-key-parameter-error

3.

https://github.com/TheInvader360/libgdx-gameservices-tutorial

4.

<dependency id="org.apache.cordova.device" url="https://github.com/apache/cordova-plugin-device.git"  />
http://stackoverflow.com/questions/20971027/phonegap-plugin-development-how-to-add-phonegap-plugin-dependency-in-plugin-xml

<dependency id="com.plugin.id" url="https://github.com/myuser/someplugin" commit="428931ada3891801" subdir="some/path/here" />
http://docs.phonegap.com/en/3.5.0/plugin_ref_spec.md.html#Plugin%20Specification

5.

http://plugins.cordova.io/#/package/com.a42.cordova.googleplaygame

6.기타

1)android

https://github.com/ar2rsawseen/GiderosGooglePlay

2)ios

https://build.phonegap.com/plugins/1078
https://github.com/leecrossley/cordova-plugin-game-center/tree/3ad5c7f

https://github.com/VCNinc/cordova-plugin-game-center#db2ccf7

http://code.tutsplus.com/tutorials/ios-sdk-game-center-achievements-and-leaderboards-part-2--mobile-5801

7.

		mHelper.setConnectOnStart(false);//
		
8.

/*
- (void) getPlayerImage:(CDVInvokedUrlCommand*)command;
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *path = [documentsDirectory stringByAppendingPathComponent: @"user.jpg" ];

    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:path];    
    if(fileExists){
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:path];
		//[pr setKeepCallbackAsBool:YES];
		[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
    }
    else{
		GKLocalPlayer *localPlayer = [GKLocalPlayer localPlayer];
        [localPlayer loadPhotoForSize:GKPhotoSizeSmall withCompletionHandler: ^(UIImage *photo, NSError *error) {            
            if (photo != nil)
            {
                NSData* data = UIImageJPEGRepresentation(photo, 0.8);
                [data writeToFile:path atomically:YES];

				CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:path];
				//[pr setKeepCallbackAsBool:YES];
				[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
				//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
				//[pr setKeepCallbackAsBool:YES];
				//[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];	
            }
            else if (error != nil)
            {
				//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
				//[pr setKeepCallbackAsBool:YES];
				//[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
				CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
				//[pr setKeepCallbackAsBool:YES];
				[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
            }
        }];
    }
}
*/

	Acts.prototype.RequestPlayerImage = function ()
	{
		if (!isSupported || !hadSuccessfulAuth)
			return;
		
		var self = this;
		
		window["gamecenter"]["getPlayerImage"](function (path)
		{
			// success
			self.playerImageURL = path || "";
			self.runtime.trigger(cr.plugins_.gamecenter.prototype.cnds.OnPlayerImageSuccess, self);
			
		}, function ()
		{
			// error
			self.runtime.trigger(cr.plugins_.gamecenter.prototype.cnds.OnPlayerImageError, self);
		});
	};
	
9.

	Acts.prototype.Auth = function ()
	{
		if (!isSupported)
			return;
		
		var self = this;
		
		window["gamecenter"]["auth"](function (user)
		{
			// success
			self.userAlias = user["alias"] || "";
			self.playerId = user["playerID"] || "";
			self.displayName = user["displayName"] || "";
			hadSuccessfulAuth = true;
			self.runtime.trigger(cr.plugins_.gamecenter.prototype.cnds.OnAuthSuccess, self);
			
		}, function ()
		{
			// error
			self.runtime.trigger(cr.plugins_.gamecenter.prototype.cnds.OnAuthFail, self);
		});
	};

	Exps.prototype.UserAlias = function (ret)
	{
		ret.set_string(this.userAlias);
	};
	
	Exps.prototype.PlayerID = function (ret)
	{
		ret.set_string(this.playerId);
	};
	
	Exps.prototype.UserDisplayName = function (ret)
	{
		ret.set_string(this.displayName);
	};
	
	Exps.prototype.PlayerImageURL = function (ret)
	{
		ret.set_string(this.playerImageURL);
	};
	
	Exps.prototype.AchievementCount = function (ret)
	{
		ret.set_int(this.achievementList.length);
	};
	
------------


AddExpression(0, ef_return_string, "", "Authentication", "UserAlias", "After auth success, the user alias.");
AddExpression(1, ef_return_string, "", "Authentication", "PlayerID", "After auth success, the user's player ID.");
AddExpression(2, ef_return_string, "", "Authentication", "UserDisplayName", "After auth success, the name to display for the user.");
AddExpression(3, ef_return_string, "", "Authentication", "PlayerImageURL", "After 'On player image received', the URL to the image.");

AddExpression(4, ef_return_number, "", "Achievements", "AchievementCount", "After 'On achievement list received', the number of achievements.");

AddNumberParam("Index", "Index of achievement");
AddExpression(5, ef_return_string, "", "Achievements", "AchievementAt", "After 'On achievement list received', the achievement at an index.");

---------------

- (void) auth:(CDVInvokedUrlCommand*)command;
{
    // __weak to avoid retain cycle
    __weak GKLocalPlayer *localPlayer = [GKLocalPlayer localPlayer];

    localPlayer.authenticateHandler = ^(UIViewController *viewController, NSError *error) {
        CDVPluginResult* pluginResult = nil;
        if (viewController != nil)
        {
            // Login required
            [self.viewController presentViewController:viewController animated:YES completion:nil];
        }
        else
        {
            if (localPlayer.isAuthenticated)
            {
                NSDictionary* user = @{
                                       @"alias":localPlayer.alias,
                                       @"displayName":localPlayer.displayName,
                                       @"playerID":localPlayer.playerID
                                       };
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:user];
            }
            else if (error != nil)
            {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
            }
            else
            {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
            }
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    };
}

10.

Tutorial: Achievements and Leaderboards with Google Play Services
http://www.hunterdavis.com/2013/09/24/tutorial-achievements-and-leaderboards-with-google-play-services/

Google Leaderboard
https://github.com/altrobot/WeHEBs/wiki/Google-Leaderboard

11.

android에서 필요
private void _resetAchievements(){

}

ios에서 필요
- (void)getPlayerScore:(CDVInvokedUrlCommand *)command {///////////////todo

}

12.Saved Games in Android

Saved Games in Android
https://developers.google.com/games/services/android/savedgames?hl=ko

Collect All the Stars 2 (This sample demonstrates how to use the Saved Games API.)
https://developers.google.com/games/services/downloads/?hl=ko#samples
https://github.com/playgameservices/android-basic-samples/tree/master/BasicSamples/CollectAllTheStars2
