//
//  Game.h
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
 
#import <Foundation/Foundation.h>

#import <Cordova/CDVPlugin.h>
#import <GameKit/GameKit.h>

@interface Game : CDVPlugin <GKLeaderboardViewControllerDelegate,GKAchievementViewControllerDelegate>

- (void)setUp:(CDVInvokedUrlCommand *)command;
- (void)login:(CDVInvokedUrlCommand *)command;
- (void)logout:(CDVInvokedUrlCommand *)command;
- (void)getPlayerImage:(CDVInvokedUrlCommand *)command;
- (void)getPlayerScore:(CDVInvokedUrlCommand *)command;
- (void)submitScore:(CDVInvokedUrlCommand *)command;
- (void)showLeaderboard:(CDVInvokedUrlCommand *)command;
- (void)unlockAchievement:(CDVInvokedUrlCommand *)command;
- (void)showAchievements:(CDVInvokedUrlCommand *)command;
- (void)resetAchievements:(CDVInvokedUrlCommand *)command;

@end
