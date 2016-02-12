//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.game;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.app.Activity;
import android.util.Log;
//
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.content.Intent;
import com.google.example.games.basegameutils.GameHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.appstate.AppStateManager;
import android.view.Gravity;
import com.google.android.gms.games.Games;//cranberrygame
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.common.api.*;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.Player;
import android.net.Uri;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;

//Util
import android.app.AlertDialog;
import android.content.DialogInterface;

class Util {

	//ex) Util.alert(cordova.getActivity(),"message");
	public static void alert(Activity activity, String message) {
		AlertDialog ad = new AlertDialog.Builder(activity).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(message);  
		ad.setButton("OK", new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show(); 		
	}	
}

public class Game extends CordovaPlugin implements GameHelper.GameHelperListener{
	private String LOG_TAG = "Game";
	private GameHelper mHelper;
	private CallbackContext loginCC;
	private CallbackContext getPlayerImageCC;
	private CallbackContext getPlayerScoreCC;
	private CallbackContext submitScoreCC;
	private CallbackContext unlockAchievementCC;		
	private CallbackContext incrementAchievementCC;
	
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		
    }	
	@Override
	public boolean execute(String action, JSONArray args,CallbackContext callbackContext) throws JSONException {
		PluginResult result = null;
		
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.cordova.getActivity()) != 0) {
            Log.e(LOG_TAG, "Google Play Services are unavailable");
            callbackContext.error("Unavailable");
            return true;
        } else {
        	Log.d(LOG_TAG, "** Google Play Services are available **");
        }
	
		//args.length()
		//args.getString(0)
		//args.getString(1)
		//args.getInt(0)
		//args.getInt(1)
		//args.getBoolean(0)
		//args.getBoolean(1)

		if (action.equals("setUp")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {						
					_setUp();
					
					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					delayedCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//delayedCC.sendPluginResult(pr);					
				}
			});
			
			return true;
		}		
		else if (action.equals("login")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				

			loginCC = callbackContext;
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {	
					
					if (getGameHelper().isSignedIn()) {
/*					
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Already logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
*/
						onSignInSucceeded();						
					}
					else {
						_login();
					}
				}
			});
			
			return true;
		}
		else if (action.equals("logout")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {				
						_logout();
						
						PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
						//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);						
					}
					else {						
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Already logged out");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}
				}
			});
			
			return true;
		}
		else if (action.equals("getPlayerImage")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				

			getPlayerImageCC = callbackContext;			
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {				
						_getPlayerImage();
					}
					else {						
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}
				}
			});
			
			return true;
		}
		else if (action.equals("getPlayerScore")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				
			final String leaderboardId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", leaderboardId));	
			
			getPlayerScoreCC = callbackContext;			
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {				
						_getPlayerScore(leaderboardId);
					}
					else {						
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}
				}
			});
			
			return true;
		}		
		else if (action.equals("submitScore")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String leaderboardId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", leaderboardId));							
			final int score = args.getInt(1);				
			Log.d(LOG_TAG, String.format("%d", score));				

			submitScoreCC = callbackContext;
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {	
					if (getGameHelper().isSignedIn()) {				
						_submitScore(leaderboardId, score);
					}
					else {
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}
				}
			});	
			
			return true;
		}		
		else if (action.equals("showLeaderboard")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String leaderboardId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", leaderboardId));				
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_showLeaderboard(leaderboardId);
						
						PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
						//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);						
					}
					else {						
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}					
				}
			});	
			
			return true;
		}
		else if (action.equals("showLeaderboards")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_showLeaderboards();
						
						PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
						//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);						
					}
					else {						
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);						
					}					
				}
			});	
			
			return true;
		}		
		else if (action.equals("unlockAchievement")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String achievementId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", achievementId));

			unlockAchievementCC = callbackContext;
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_unlockAchievement(achievementId);
					}
					else {
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);	
					}						
				}
			});	
			
			return true;
		}
		else if (action.equals("incrementAchievement")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String achievementId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", achievementId));
			final int stepsOrPercent = args.getInt(1);				
			Log.d(LOG_TAG, String.format("%d", stepsOrPercent));

			incrementAchievementCC = callbackContext;
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_incrementAchievement(achievementId, stepsOrPercent);
					}
					else {
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);	
					}						
				}
			});	
			
			return true;
		}		
		else if (action.equals("showAchievements")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_showAchievements();
						
						PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
						//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);						
					}
					else {
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Not logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);	
					}
				}
			});	
			
			return true;
		}
		else if (action.equals("resetAchievements")) {
			//Activity activity=cordova.getActivity();
			//webView
			//				
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {						
					_resetAchievements();
					
					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					delayedCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//delayedCC.sendPluginResult(pr);					
				}
			});
			
			return true;
		}		
		
		return false; // Returning false results in a "MethodNotFound" error.	
	}
	
	//-------------------------------------
	private void _setUp(){		
		getGameHelper().setup(this);//public void setup(GameHelperListener listener) {
		
		cordova.setActivityResultCallback(this);		
	}	
	private GameHelper getGameHelper(){
        if (mHelper == null) {
			mHelper = new GameHelper(this.cordova.getActivity(), GameHelper.CLIENT_GAMES);//public GameHelper(Activity activity, int clientsToUse) {
			mHelper.enableDebugLog(true);
		}
		return mHelper;		
	}	
	private void _login(){
		//getGameHelper().beginUserInitiatedSignIn();		
		getGameHelper().onStart(this.cordova.getActivity());
	}
	private void _logout(){		
		//getGameHelper().signOut();
		getGameHelper().onStop();
	}

	private void _getPlayerImage() {
		Player player = Games.Players.getCurrentPlayer(getGameHelper().getApiClient());		
		if (player != null)
		{
			boolean hasH = player.hasHiResImage();
			boolean hasI = player.hasIconImage();
			Uri playerImageUrl = null;
			if (hasH) {
				playerImageUrl = player.getHiResImageUri();
			}
			else if (hasI) {
				playerImageUrl = player.getIconImageUri();
			}
			else {
				//PluginResult pr = new PluginResult(PluginResult.Status.OK);
				//pr.setKeepCallback(true);
				//getPlayerImageCC.sendPluginResult(pr);
				PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
				//pr.setKeepCallback(true);
				getPlayerImageCC.sendPluginResult(pr);	
			
				return;
			}

			PluginResult pr = new PluginResult(PluginResult.Status.OK, playerImageUrl.toString());
			//pr.setKeepCallback(true);
			getPlayerImageCC.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//getPlayerImageCC.sendPluginResult(pr);			
		}
		else {
			//PluginResult pr = new PluginResult(PluginResult.Status.OK);
			//pr.setKeepCallback(true);
			//getPlayerImageCC.sendPluginResult(pr);
			PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			getPlayerImageCC.sendPluginResult(pr);					
		}
	}
	
	private void _getPlayerScore(String leaderboardId){
		class ResultCallbackSubmitScoreResult implements ResultCallback<Leaderboards.LoadPlayerScoreResult> {
            @Override
            public void onResult(Leaderboards.LoadPlayerScoreResult result) {
				//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.LoadPlayerScoreResult.html
                if (result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK) {
					//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/LeaderboardScore.html
					LeaderboardScore ls = result.getScore();
					long score = 0;
					if (ls != null)
						score = ls.getRawScore();
					
					PluginResult pr = new PluginResult(PluginResult.Status.OK, score);
					//pr.setKeepCallback(true);
					getPlayerScoreCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//getPlayerScoreCC.sendPluginResult(pr);
                }
				else {
					//PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					//getPlayerScoreCC.sendPluginResult(pr);
					PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					getPlayerScoreCC.sendPluginResult(pr);					
				}
            }
        }
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.html
		//span:	Time span to retrieve data for. Valid values are TIME_SPAN_DAILY, TIME_SPAN_WEEKLY, or TIME_SPAN_ALL_TIME.
		//leaderboardCollection: The leaderboard collection to retrieve scores for. Valid values are either COLLECTION_PUBLIC or COLLECTION_SOCIAL.		
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.html#loadCurrentPlayerLeaderboardScore(com.google.android.gms.common.api.GoogleApiClient, java.lang.String, int, int)
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/LeaderboardVariant.html#TIME_SPAN_DAILY
		//http://stackoverflow.com/questions/23248157/how-to-get-score-from-google-play-game-services-leaderboard-of-current-player
		Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getGameHelper().getApiClient(), leaderboardId, LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallbackSubmitScoreResult());
	}
	
	private void _submitScore(String leaderboardId, int score){
/*	
		//https://developers.google.com/games/services/android/leaderboards
		Games.Leaderboards.submitScore(getGameHelper().getApiClient(), leaderboardId, score);			
*/
///*		
		//http://stackoverflow.com/questions/22896713/listener-for-leaderboard-in-google-game-services
		//https://developer.android.com/reference/gms-packages.html
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/package-summary.html
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.html
		//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.html#submitScoreImmediate(com.google.android.gms.common.api.GoogleApiClient, java.lang.String, long)
		
		class ResultCallbackSubmitScoreResult implements ResultCallback<Leaderboards.SubmitScoreResult> {
            @Override
            public void onResult(Leaderboards.SubmitScoreResult result) {
				//https://developer.android.com/reference/com/google/android/gms/games/leaderboard/Leaderboards.SubmitScoreResult.html
                if (result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK) {
                    // data sent successfully to server.
                    // display toast.

					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					submitScoreCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//submitScoreCC.sendPluginResult(pr);
                }
				else {
					//PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					//submitScoreCC.sendPluginResult(pr);
					PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					submitScoreCC.sendPluginResult(pr);					
				}
            }
        }
		//Games.Leaderboards.submitScoreImmediate(getGameHelper().getApiClient(), leaderboardId, score).setResultCallback(new ResultCallbackSubmitScoreResult());
		try {
			Games.Leaderboards.submitScoreImmediate(getGameHelper().getApiClient(), leaderboardId, score).setResultCallback(new ResultCallbackSubmitScoreResult());
		}
		catch(SecurityException ex) {
			Log.d(LOG_TAG, String.format("%s", ex.getMessage()));	
		}		
//*/
	}	
	
	private void _showLeaderboard(String leaderboardId){
		try {
			//show a specific leaderboard
			this.cordova.getActivity().startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getGameHelper().getApiClient(), leaderboardId), 0);
		}
		catch(SecurityException ex) {
			Log.d(LOG_TAG, String.format("%s", ex.getMessage()));	
		}
	}

	private void _showLeaderboards(){
		try {
			//show all leaderboards
			this.cordova.getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getGameHelper().getApiClient()), 0);
			//this.cordova.getActivity().startActivityFor(Games.Leaderboards.getAllLeaderboardsIntent(getGameHelper().getApiClient()));		
		}
		catch(SecurityException ex) {
			Log.d(LOG_TAG, String.format("%s", ex.getMessage()));	
		}
	}
	
	private void _unlockAchievement(String achievementId){
/*	
		//Unlocking achievements
		//To unlock an achievement, call the unlock() method and and pass in the achievement ID.
		//Games.Achievements.unlock(getApiClient(), "my_achievement_id");
		//If the achievement is of the incremental type (that is, several steps are required to unlock it), call increment() instead.
		//Games.Achievements.increment(getApiClient(), "my_incremental_achievment_id", 10000);
		//You do not need to write additional code to unlock the achievement; Play Games services automatically unlocks the achievement once it reaches its required number of steps.
		//https://developers.google.com/games/services/android/achievements
		Games.Achievements.unlock(getGameHelper().getApiClient(), achievementId);
*/
///*
		//https://developer.android.com/reference/gms-packages.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/package-summary.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.html#incrementImmediate(com.google.android.gms.common.api.GoogleApiClient, java.lang.String, int)
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.html#unlockImmediate(com.google.android.gms.common.api.GoogleApiClient, java.lang.String)
		class ResultCallbackUpdateAchievementResult implements ResultCallback<Achievements.UpdateAchievementResult> {
            @Override
            public void onResult(Achievements.UpdateAchievementResult result) {			
				//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.UpdateAchievementResult.html
                if (result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK) {
                    // data sent successfully to server.
                    // display toast.
					//Log.d(LOG_TAG, String.format("%d", result.getStatus().getStatusCode()));
					//Util.alert(cordova.getActivity(), String.format("%d", result.getStatus().getStatusCode()));					

					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					unlockAchievementCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//unlockAchievementCC.sendPluginResult(pr);
                }
				else{
					//Log.d(LOG_TAG, String.format("%d", result.getStatus().getStatusCode()));
					//Util.alert(cordova.getActivity(), String.format("%d", result.getStatus().getStatusCode()));
					
					//PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					//unlockAchievementCC.sendPluginResult(pr);
					PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					unlockAchievementCC.sendPluginResult(pr);					
				}
            }
        }		
		Games.Achievements.unlockImmediate(getGameHelper().getApiClient(), achievementId).setResultCallback(new ResultCallbackUpdateAchievementResult());
//*/		
	}
	
	private void _incrementAchievement(String achievementId, int stepsOrPercent){
/*	
		//Unlocking achievements
		//To unlock an achievement, call the unlock() method and and pass in the achievement ID.
		//Games.Achievements.unlock(getApiClient(), "my_achievement_id");
		//If the achievement is of the incremental type (that is, several steps are required to unlock it), call increment() instead.
		//Games.Achievements.increment(getApiClient(), "my_incremental_achievment_id", 1);
		//You do not need to write additional code to unlock the achievement; Play Games services automatically unlocks the achievement once it reaches its required number of steps.
		//https://developers.google.com/games/services/android/achievements
		//Games.Achievements.unlock(getGameHelper().getApiClient(), achievementId);
		//
		Games.Achievements.increment(getGameHelper().getApiClient(), achievementId, stepsOrPercent);		
*/
///*
		//https://developer.android.com/reference/gms-packages.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/package-summary.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.html
		//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.html#incrementImmediate(com.google.android.gms.common.api.GoogleApiClient, java.lang.String, int)
		class ResultCallbackUpdateAchievementResult implements ResultCallback<Achievements.UpdateAchievementResult> {
            @Override
            public void onResult(Achievements.UpdateAchievementResult result) {			
				//https://developer.android.com/reference/com/google/android/gms/games/achievement/Achievements.UpdateAchievementResult.html
                if (result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK) {
                    // data sent successfully to server.
                    // display toast.
					//Log.d(LOG_TAG, String.format("%d", result.getStatus().getStatusCode()));
					//Util.alert(cordova.getActivity(), String.format("%d", result.getStatus().getStatusCode()));					

					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					incrementAchievementCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//incrementAchievementCC.sendPluginResult(pr);
                }
				else{
					//Log.d(LOG_TAG, String.format("%d", result.getStatus().getStatusCode()));
					//Util.alert(cordova.getActivity(), String.format("%d", result.getStatus().getStatusCode()));
					
					//PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					//incrementAchievementCC.sendPluginResult(pr);
					PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					incrementAchievementCC.sendPluginResult(pr);					
				}
            }
        }		
		Games.Achievements.incrementImmediate(getGameHelper().getApiClient(), achievementId, stepsOrPercent).setResultCallback(new ResultCallbackUpdateAchievementResult());
//*/		
	}
	
	private void _showAchievements(){
		this.cordova.getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(getGameHelper().getApiClient()), 0);		
	}

	private void _resetAchievements(){///////////////todo

	}
	
	//GameHelper.GameHelperListener
    @Override
    public void onSignInSucceeded() {
		//Util.alert(cordova.getActivity(), "onSignInSucceeded");	
		
		//https://github.com/freshplanet/ANE-Google-Play-Game-Services/blob/master/android/src/com/freshplanet/googleplaygames/functions/AirGooglePlayGamesGetActivePlayerName.java
		//https://developer.android.com/reference/com/google/android/gms/games/Games.html#Players
		//https://developer.android.com/reference/com/google/android/gms/games/Players.html#getCurrentPlayer(com.google.android.gms.common.api.GoogleApiClient)
		//https://developer.android.com/reference/com/google/android/gms/games/Player.html
		Player player = Games.Players.getCurrentPlayer(getGameHelper().getApiClient());
		JSONObject playerDetail = new JSONObject();
		try {
			if (player != null)
			{
				String playerId = player.getPlayerId();
				String displayName = player.getDisplayName();
				//String title = player.getTitle();
								
				playerDetail.put("playerId", playerId);
				playerDetail.put("playerDisplayName", displayName);
			}
		}
		catch(JSONException ex){
		}
		
		PluginResult pr = new PluginResult(PluginResult.Status.OK, playerDetail);
		//pr.setKeepCallback(true);
		loginCC.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//loginCC.sendPluginResult(pr);			
    }	
    @Override
    public void onSignInFailed() {
		//Util.alert(cordova.getActivity(), "onSignInFailed");	

		//PluginResult pr = new PluginResult(PluginResult.Status.OK);
		//pr.setKeepCallback(true);
		//loginCC.sendPluginResult(pr);
		PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		loginCC.sendPluginResult(pr);
    }
	
	//CordovaPlugin
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		getGameHelper().onActivityResult(requestCode, resultCode, intent);
	}

/*
	//javascript
	getPlayerImage: function (cb) {
		var self = this;
		//cordova.exec(function (result) {
		//	var playerImageUrl = result;
		//	if (self.onGetPlayerImageSucceeded)			
		//		self.onGetPlayerImageSucceeded(playerImageUrl);
		//}, 
		//function (error) {
		//	if (self.onGetPlayerImageFailed)			
		//		self.onGetPlayerImageFailed();
		//}, "Game", "getPlayerImage", []);
		
        this.gapi.client.request({
          path : "/games/v1/players/me",
          callback : function(result) {
            var er = result && !result.error ? success(result) : null;
            cb(er, result.error);
          }
        });		
	},
	
	//java
    @SuppressWarnings("unused")
    public void request(CordovaArgs args, final CallbackContext ctx) throws JSONException {

        JSONObject params = args.getJSONObject(0);
        String path = params.getString("path");
        JSONObject requestParams = params.optJSONObject("params");
        String method = params.optString("method");
        if (method == null || method.length() == 0) {
            method = "GET";
        }
        HashMap<String, String> headers = null;
        JSONObject obj = params.optJSONObject("headers");
        if (obj != null) {
            headers = new HashMap<String, String>();
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                headers.put(key, obj.get(key).toString());
            }
        }

        byte[] body = null;
        try
        {
            JSONObject bodyJSON = params.optJSONObject("body");
            if (bodyJSON != null) {
                body = bodyJSON.toString().getBytes("utf-8");
            }
            else {
                String bodyString = params.optString("body");
                if (bodyString != null && bodyString.length() > 0) {
                    body = bodyString.getBytes("utf-8");
                }
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        request_inner(path, method, requestParams, body, headers, new GPGService.RequestCallback() {
            @Override
            public void onComplete(JSONObject responseJSON, GPGService.Error error) {

                JSONObject data = new JSONObject();
                try
                {
                    if (responseJSON != null) {
                        data.put("response", responseJSON);
                    }
                    if (error != null) {
                        data.put("error", new JSONObject(error.toMap()));
                    }

                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
                ctx.sendPluginResult(new PluginResult(PluginResult.Status.OK, data));
            }
        });
    }
	
    public void request_inner(String path,final String method,  JSONObject params, final byte[] body, final Map<String, String> headers, final RequestCallback callback) throws JSONException {

        if (!this.client.isConnected()) {

            if (callback != null) {
                callback.onComplete(null, new Error("User is not logged into Google Play Game Services", 0));
            }
            return;
        }


        if (path.startsWith("/")) {
            path = "https://www.googleapis.com" + path;
        }

        if (params != null) {
            String query = "";
            Iterator<String> it = params.keys();
            while (it.hasNext()) {
                if (query.length() == 0)
                    query+="&";
                String key = it.next();
                query+= key + "=" + params.get(key).toString();
            }
            path+= "?" + query;
        }

        final String absolutePath = path;

        AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>() {

            @Override
            protected Object doInBackground(Void... params) {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(absolutePath);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Authorization", "Bearer " + GPGService.this.authToken);
                    connection.setRequestMethod(method);

                    if (headers != null) {
                        for (String key : headers.keySet())
                        {
                            connection.setRequestProperty(key, headers.get(key));
                        }
                    }

                    if (body != null)
                    {
                        connection.setFixedLengthStreamingMode(body.length);
                        connection.setDoOutput(true);
                        if (connection.getRequestProperty("Content-Type") == null)
                            connection.setRequestProperty("Content-Type",  "text/plain;charset=UTF-8");

                        connection.setRequestProperty("Content-Length", Integer.toString(body.length));
                        OutputStream output = null;
                        try
                        {
                            output = connection.getOutputStream();
                            output.write(body);
                        }
                        finally
                        {
                            if (output != null) {
                                output.close();
                            }
                        }
                    }

                    int statusCode = connection.getResponseCode();
                    InputStream inputStream;
                    if (statusCode >= 200 && statusCode < 300) {
                        inputStream = connection.getInputStream();
                    } else {
                        inputStream = connection.getErrorStream();
                    }

                    String content = convertStreamToString(inputStream);

                    JSONObject result = new JSONObject(content);
                    return result;
                }
                catch (Exception e) {
                    return new Error(e.getLocalizedMessage(), 0);
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(Object info) {
                if (callback == null) {
                    return;
                }
                if (info == null) {
                    callback.onComplete(null, null);
                }
                else if (info instanceof Error) {
                    callback.onComplete(null, (Error)info);
                }
                else {
                    callback.onComplete((JSONObject)info, null);
                }
            }

        };

        if (this.executor != null) {
            task.executeOnExecutor(executor);
        }
        else  {
            task.execute();
        }
    }
	
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }	
*/	
}