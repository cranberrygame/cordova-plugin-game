//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.phonegap.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
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
	private String LOG_TAG = "GooglePlay";
	private GameHelper mHelper;
	private CallbackContext loginCC;
	private CallbackContext submitScoreCC;
	private CallbackContext submitAchievementCC;		
		
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
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {	
					
					if (getGameHelper().isSignedIn()) {
						//PluginResult pr = new PluginResult(PluginResult.Status.OK);
						//pr.setKeepCallback(true);
						//delayedCC.sendPluginResult(pr);
						PluginResult pr = new PluginResult(PluginResult.Status.ERROR, "Already logged in");
						//pr.setKeepCallback(true);
						delayedCC.sendPluginResult(pr);
					}
					else {
						_login();
					}
				}
			});
			
			loginCC = callbackContext;
			
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
		else if (action.equals("submitScore")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String leaderboardId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", leaderboardId));							
			final int score = args.getInt(1);				
			Log.d(LOG_TAG, String.format("%d", score));				
			
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
			
			submitScoreCC = callbackContext;
			
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
		else if (action.equals("submitAchievement")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String achievementId = args.getString(0);				
			Log.d(LOG_TAG, String.format("%s", achievementId));
			final int percent = args.getInt(1);				
			Log.d(LOG_TAG, String.format("%d", percent));
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					if (getGameHelper().isSignedIn()) {
						_submitAchievement(achievementId, percent);
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
			
			submitAchievementCC = callbackContext;
			
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
		Games.Leaderboards.submitScoreImmediate(getGameHelper().getApiClient(), leaderboardId, score).setResultCallback(new ResultCallbackSubmitScoreResult());
//*/
	}	
	private void _showLeaderboard(String leaderboardId){
		//show all leaderboards
		//this.cordova.getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getGameHelper().getApiClient()), 0);
		//this.cordova.getActivity().startActivityFor(Games.Leaderboards.getAllLeaderboardsIntent(getGameHelper().getApiClient()));		
		//show a specific leaderboard
		this.cordova.getActivity().startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getGameHelper().getApiClient(), leaderboardId), 0);		
	}
	private void _submitAchievement(String achievementId, int percent){
/*	
		//https://developers.google.com/games/services/android/achievements
		//Games.Achievements.unlock(getGameHelper().getApiClient(), achievementId);
		//
		Games.Achievements.increment(getGameHelper().getApiClient(), achievementId, percent);		
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
					submitAchievementCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//submitAchievementCC.sendPluginResult(pr);
                }
				else{
					//Log.d(LOG_TAG, String.format("%d", result.getStatus().getStatusCode()));
					//Util.alert(cordova.getActivity(), String.format("%d", result.getStatus().getStatusCode()));
					
					//PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					//submitAchievementCC.sendPluginResult(pr);
					PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					submitAchievementCC.sendPluginResult(pr);					
				}
            }
        }		
		Games.Achievements.incrementImmediate(getGameHelper().getApiClient(), achievementId, percent).setResultCallback(new ResultCallbackUpdateAchievementResult());
//*/		
	}	
	private void _showAchievements(){
		this.cordova.getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(getGameHelper().getApiClient()), 0);		
	}

	//GameHelper.GameHelperListener
    @Override
    public void onSignInSucceeded() {
		//Util.alert(cordova.getActivity(), "onSignInSucceeded");	
		
		PluginResult pr = new PluginResult(PluginResult.Status.OK);
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
}