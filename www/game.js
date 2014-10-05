
module.exports = {
   
	setUp: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Game", "setUp", []);
    },
	login: function() {
		var self = this;
		cordova.exec(function (result) {
			self.onLoginSucceeded();
		}, 
		function (error) {
			self.onLoginFailed();
		}, "Game", "login", []);
    },
	logout: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Game", "logout", []);
    },
	submitScore: function(leaderboardId, score) {
		var self = this;
		cordova.exec(function (result) {
			self.onSubmitScoreSucceeded();
		}, 
		function (error) {
			self.onSubmitScoreFailed();
		}, "Game", "submitScore", [leaderboardId, score]);
	},
	showLeaderboard: function(leaderboardId, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Game", "showLeaderboard", [leaderboardId]);
	},
	submitAchievement: function(achievementId, percent, successCallback, errorCallback) {
		var self = this;
		cordova.exec(function (result) {
			self.onSubmitAchievementSucceeded();
		}, 
		function (error) {
			self.onSubmitAchievementFailed();
		}, "Game", "submitAchievement", [achievementId, percent]);
	},
	showAchievements: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Game", "showAchievements", []);
	},
	onLoginSucceeded: null,
	onLoginFailed: null,
	onSubmitScoreSucceeded: null,
	onSubmitScoreFailed: null,
	onSubmitAchievementSucceeded: null,
	onSubmitAchievementFailed: null
};
