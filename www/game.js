
module.exports = {
	_loggedin: false,
	tag: '',
	setUp: function() {
		cordova.exec(
		function (result) {
		}, 
		function (error) {
		}, "Game", "setUp", []);
    },
	login: function() {
		var self = this;
		cordova.exec(function (result) {
			var playerDetail = result;
			self._loggedin = true;
			if (self.onLoginSucceeded)
				self.onLoginSucceeded(playerDetail);
		}, 
		function (error) {
			if (self.onLoginFailed)			
				self.onLoginFailed();
		}, "Game", "login", []);
    },
	logout: function() {
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
			if (self.onGetPlayerImageSucceeded)			
				self.onGetPlayerImageSucceeded(playerImageUrl);
		}, 
		function (error) {
			if (self.onGetPlayerImageFailed)			
				self.onGetPlayerImageFailed();
		}, "Game", "getPlayerImage", []);
	},	
	getPlayerScore: function(leaderboardId, tag) {
		var self = this;
		cordova.exec(function (result) {
			var playerScore = result;
			self.tag = tag;
			if (self.onGetPlayerScoreSucceeded)
				self.onGetPlayerScoreSucceeded(playerScore);
		}, 
		function (error) {
			self.tag = tag;
			if (self.onGetPlayerScoreFailed)			
				self.onGetPlayerScoreFailed();
		}, "Game", "getPlayerScore", [leaderboardId]);
	},
	submitScore: function(leaderboardId, score, tag) {
		var self = this;
		cordova.exec(function (result) {
			self.tag = tag;
			if (self.onSubmitScoreSucceeded)			
				self.onSubmitScoreSucceeded();
		}, 
		function (error) {
			self.tag = tag;
			if (self.onSubmitScoreFailed)			
				self.onSubmitScoreFailed();
		}, "Game", "submitScore", [leaderboardId, score]);
	},
	showLeaderboard: function(leaderboardId) {
		cordova.exec(
		function (result) {
		}, 
		function (error) {
		}, "Game", "showLeaderboard", [leaderboardId]);
	},
	unlockAchievement: function(achievementId, tag) {
		var self = this;
		cordova.exec(function (result) {
			self.tag = tag;
			if (self.onUnlockAchievementSucceeded)			
				self.onUnlockAchievementSucceeded();
		}, 
		function (error) {
			self.tag = tag;
			if (self.onUnlockAchievementFailed)			
				self.onUnlockAchievementFailed();
		}, "Game", "unlockAchievement", [achievementId]);
	},
	showAchievements: function() {
		cordova.exec(
		function (result) {
		}, 
		function (error) {
		}, "Game", "showAchievements", []);
	},
	resetAchievements: function() {
		var self = this;
		cordova.exec(function (result) {
			if (self.onResetAchievementsSucceeded)			
				self.onResetAchievementsSucceeded();
		}, 
		function (error) {
			if (self.onResetAchievementsFailed)			
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
	onUnlockAchievementSucceeded: null,
	onUnlockAchievementFailed: null,
	onResetAchievementsSucceeded: null,
	onResetAchievementsFailed: null
};
