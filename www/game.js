
module.exports = {
	_loggedin: false,
	setUp: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Game", "setUp", []);
    },
	login: function() {
		var self = this;
		cordova.exec(function (result) {
			var playerDetail = result;
			self._loggedin = true;
			self.onLoginSucceeded(playerDetail);
		}, 
		function (error) {
			self.onLoginFailed();
		}, "Game", "login", []);
    },
	logout: function(successCallback, errorCallback) {
		var self = this;
		cordova.exec(function (result) {
			self._loggedin = false;
		}, 
		function (error) {
		}, "Game", "logout", []);
    },
	isLoggedIn: function() {
		return this._loggedin;
	},
	getPlayerImage: function() {
		var self = this;
		cordova.exec(function (result) {
			var playerImageUrl = result;
			self.onGetPlayerImageSucceeded(playerImageUrl);
		}, 
		function (error) {
			self.onGetPlayerImageFailed();
		}, "Game", "getPlayerImage", []);
	},	
	getPlayerScore: function(leaderboardId) {
		var self = this;
		cordova.exec(function (result) {
			var playerScore = result;
			self.onGetPlayerScoreSucceeded(playerScore);
		}, 
		function (error) {
			self.onGetPlayerScoreFailed();
		}, "Game", "getPlayerScore", [leaderboardId]);
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
	resetAchievements: function() {
		var self = this;
		cordova.exec(function (result) {
			self.onResetAchievementsSucceeded();
		}, 
		function (error) {
			self.onResetAchievementsFailed();
		}, "Game", "resetAchievements", []);
	},	
	onLoginSucceeded: null,
	onLoginFailed: null,	
	onGetPlayerImageSucceeded: null,
	onGetPlayerImageFailed: null,	
    onGetPlayerScoreSucceeded: null,
    onGetPlayerScoreFailed: null,	
	onSubmitScoreSucceeded: null,
	onSubmitScoreFailed: null,
	onSubmitAchievementSucceeded: null,
	onSubmitAchievementFailed: null,
	onResetAchievementsSucceeded: null,
	onResetAchievementsFailed: null
};
