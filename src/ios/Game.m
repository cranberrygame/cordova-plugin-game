//
//  Game.m
//  Detonate
//
//  Created by Marco Piccardo on 04/02/11.
//  Copyright 2011 Eurotraining Engineering. All rights reserved.
//
/*
 *  Modified and Updated
 *
 *  Copyright 2014 Wizcorp Inc. http://www.wizcorp.jp
 *  Author Ally Ogilvie
 */
//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)

#import "Game.h"
#import <Cordova/CDVViewController.h>

#define SYSTEM_VERSION_LESS_THAN(v) ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedAscending)

@interface Game ()
@property (nonatomic, retain) GKLeaderboardViewController *leaderboardController;
@property (nonatomic, retain) GKAchievementViewController *achievementsController;
@end

@implementation Game

- (void)setUp:(CDVInvokedUrlCommand *)command {

}

- (void)login:(CDVInvokedUrlCommand *)command {
    
    //[self.commandDelegate runInBackground:^{//cranberrygame
        [[GKLocalPlayer localPlayer] setAuthenticateHandler: ^(UIViewController *viewcontroller, NSError *error) {
            CDVPluginResult *pluginResult;
            
            //already logined
            if ([GKLocalPlayer localPlayer].authenticated) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            }
            else if (viewcontroller) {
                CDVViewController *vc = (CDVViewController *)[super viewController];
                [vc presentViewController:viewcontroller animated:YES completion:^{
                }];
            }
            else {
                // Called the second time with result
                if (error == nil) {
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                }
                else {
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
                }
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        }];
    //}];//cranberrygame
}

- (void)logout:(CDVInvokedUrlCommand *)command {
    //Unfortunately, this takes the user outside your app.
    //http://stackoverflow.com/questions/9995576/how-to-show-game-centers-player-profile-view
    //[[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"gamecenter:/me/account"]];
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"gamecenter:/me/signout"]];
}

- (void)submitScore:(CDVInvokedUrlCommand *)command {

    //[self.commandDelegate runInBackground:^{//cranberrygame
		NSString *leaderboardId = (NSString *) [command.arguments objectAtIndex:0];
        int64_t score = [[command.arguments objectAtIndex:1] integerValue];
		
		__block CDVPluginResult* pluginResult = nil;
		
		GKScore *s = [[GKScore alloc] initWithLeaderboardIdentifier: leaderboardId];
        s.value = score;
        s.context = 0;
			
		[GKScore reportScores:@[s] withCompletionHandler: ^(NSError *error) {
            if (error)
            {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
            }
            else
            {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
		}];
    //}];//cranberrygame
}

- (void)showLeaderboard:(CDVInvokedUrlCommand *)command {
    //[self.commandDelegate runInBackground:^{//cranberrygame
        if ( self.leaderboardController == nil ) {
            self.leaderboardController = [[GKLeaderboardViewController alloc] init];
            self.leaderboardController.leaderboardDelegate = self;//
        }
        self.leaderboardController.category = (NSString *) [command.arguments objectAtIndex:0];
        CDVViewController *vc = (CDVViewController *)[super viewController];
        [vc presentViewController:self.leaderboardController animated:YES completion: ^{
        }];
    //}];//cranberrygame
}

- (void)submitAchievement:(CDVInvokedUrlCommand *)command {
    //[self.commandDelegate runInBackground:^{//cranberrygame
		NSString *achievementId = (NSString *) [command.arguments objectAtIndex:0];
		float percentFloat = [[command.arguments objectAtIndex:1] floatValue];
		
		__block CDVPluginResult* pluginResult = nil;
		
		GKAchievement *achievement = [[GKAchievement alloc] initWithIdentifier: achievementId];
		if (achievement)
		{
			achievement.percentComplete = percentFloat;
			achievement.showsCompletionBanner = YES;//????
			
			[achievement reportAchievementWithCompletionHandler: ^(NSError *error)
			{
				 if (error != nil)
				 {
					 pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[error localizedDescription]];
				 } else {
/*					 
					 // Achievement notification banners are broken on IOS7 so we do it manually here:
					 if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0) {
						 
						 [GKNotificationBanner showBannerWithTitle:@"Achievement" message:@"Completed!" completionHandler:^{}];
					 }
*/					 
					 pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
				 }
				 [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
				 
			}];
		}
    //}];//cranberrygame
}

- (void)showAchievements:(CDVInvokedUrlCommand *)command {//cranberrygame
    //[self.commandDelegate runInBackground:^{
        if ( self.achievementsController == nil ) {
            self.achievementsController = [[GKAchievementViewController alloc] init];
            self.achievementsController.achievementDelegate = self;//
        }
        CDVViewController *vc = (CDVViewController *)[super viewController];
        [vc presentViewController:self.achievementsController animated:YES completion: ^{
        }];
    //}];//cranberrygame
}

//GKLeaderboardViewControllerDelegate
- (void)leaderboardViewControllerDidFinish:(GKLeaderboardViewController *)viewController {
/*
    CDVViewController *vc = (CDVViewController *)[super viewController];
    [vc dismissModalViewControllerAnimated:YES];
*/
    [viewController dismissModalViewControllerAnimated:YES];
}

//GKAchievementViewControllerDelegate
- (void)achievementViewControllerDidFinish:(GKAchievementViewController *)viewController {
/*
    CDVViewController* vc = (CDVViewController *)[super viewController];
    [vc dismissModalViewControllerAnimated:YES];
*/
    [viewController dismissModalViewControllerAnimated:YES];
}

- (void)dealloc {
    self.leaderboardController = nil;
    self.achievementsController = nil;
    
    [super dealloc];
}

@end
