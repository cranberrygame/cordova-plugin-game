// ECMAScript 5 strict mode
"use strict";

assert2(cr, "cr namespace not created");
assert2(cr.plugins_, "cr.plugins_ not created");

/////////////////////////////////////
// Plugin class
cr.plugins_.C2Ejecta = function(runtime)
{
	this.runtime = runtime;
};

(function ()
{
	/////////////////////////////////////
	var pluginProto = cr.plugins_.C2Ejecta.prototype;
		
	/////////////////////////////////////
	// Object type class
	pluginProto.Type = function(plugin)
	{
		this.plugin = plugin;
		this.runtime = plugin.runtime;
	};

	var typeProto = pluginProto.Type.prototype;

	// called on startup for each object type
	typeProto.onCreate = function()
	{
	};

	/////////////////////////////////////
	// Instance class
	pluginProto.Instance = function(type)
	{
		this.type = type;
		this.runtime = type.runtime;
		
		// any other properties you need, e.g...
		// this.myValue = 0;
	};
	
	var instanceProto = pluginProto.Instance.prototype;

	// called whenever an instance is created
	instanceProto.onCreate = function()
	{
		this.textInput = "";
		this.adBanner = null;
		this.gameCenter = null;
	};
	
	instanceProto.getGameCenter = function ()
	{
		if (!this.runtime.isEjecta)
			return null;
		
		// lazy load
		if (!this.gameCenter)
			this.gameCenter = new Ejecta["GameCenter"]();
		
		return this.gameCenter;
	};
	
	// called whenever an instance is destroyed
	// note the runtime may keep the object after this call for recycling; be sure
	// to release/recycle/reset any references to other objects in this function.
	instanceProto.onDestroy = function ()
	{
	};
	
	// called when saving the full state of the game
	instanceProto.saveToJSON = function ()
	{
		// return a Javascript object containing information about your object's state
		// note you MUST use double-quote syntax (e.g. "property": value) to prevent
		// Closure Compiler renaming and breaking the save format
		return {
			// e.g.
			//"myValue": this.myValue
		};
	};
	
	// called when loading the full state of the game
	instanceProto.loadFromJSON = function (o)
	{
		// load from the state previously saved by saveToJSON
		// 'o' provides the same object that you saved, e.g.
		// this.myValue = o["myValue"];
		// note you MUST use double-quote syntax (e.g. o["property"]) to prevent
		// Closure Compiler renaming and breaking the save format
	};
	
	// The comments around these functions ensure they are removed when exporting, since the
	// debugger code is no longer relevant after publishing.
	/**BEGIN-PREVIEWONLY**/
	instanceProto.getDebuggerValues = function (propsections)
	{
		// Append to propsections any debugger sections you want to appear.
		// Each section is an object with two members: "title" and "properties".
		// "properties" is an array of individual debugger properties to display
		// with their name and value, and some other optional settings.
		/*
		propsections.push({
			"title": "My debugger section",
			"properties": [
				// Each property entry can use the following values:
				// "name" (required): name of the property (must be unique within this section)
				// "value" (required): a boolean, number or string for the value
				// "html" (optional, default false): set to true to interpret the name and value
				//									 as HTML strings rather than simple plain text
				// "readonly" (optional, default false): set to true to disable editing the property
				
				// Example:
				// {"name": "My property", "value": this.myValue}
			]
		});
		*/
	};
	
	instanceProto.onDebugValueEdited = function (header, name, value)
	{
		// Called when a non-readonly property has been edited in the debugger. Usually you only
		// will need 'name' (the property name) and 'value', but you can also use 'header' (the
		// header title for the section) to distinguish properties with the same name.
		//if (name === "My property")
		//	this.myProperty = value;
	};
	/**END-PREVIEWONLY**/

	//////////////////////////////////////
	// Conditions
	function Cnds() {};

	Cnds.prototype.OnTextInput = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnAdLoad = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnAdError = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCAuthed = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCAuthError = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCAchievementSuccess = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCAchievementError = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCScoreSuccess = function ()
	{
		return true;
	};
	
	Cnds.prototype.OnGCScoreError = function ()
	{
		return true;
	};
	
	Cnds.prototype.IsGCAuthed = function ()
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return false;
		
		return gc["authed"];
	};
	
	pluginProto.cnds = new Cnds();
	
	//////////////////////////////////////
	// Actions
	function Acts() {};

	Acts.prototype.PromptForText = function (title, message)
	{
		if (!this.runtime.isEjecta)
			return;
		
		var self = this;
		
		ejecta["getText"](title, message, function (text)
		{
			self.textInput = text || "";
			self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnTextInput, self);
		});
	};
	
	Acts.prototype.ShowAd = function (pos)
	{
		if (!this.runtime.isEjecta)
			return;
		
		var self = this;
		
		// Create banner if none spawned yet
		if (!this.adBanner)
		{
			this.adBanner = new Ejecta["AdBanner"]();
			this.adBanner.onload = function ()
			{
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnAdLoad, self);
			};
			this.adBanner.onerror = function ()
			{
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnAdError, self);
			};
		}
		
		this.adBanner["isAtBottom"] = (pos !== 0);
		this.adBanner["show"]();
	};
	
	Acts.prototype.HideAd = function ()
	{
		if (!this.runtime.isEjecta || !this.adBanner)
			return;
		
		this.adBanner["hide"]();
	};
	
	Acts.prototype.GCSoftAuth = function ()
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		var self = this;
		
		gc["softAuthenticate"](function (error) {
			if (error)
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAuthError, self);
			else
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAuthed, self);
		});
	};
	
	Acts.prototype.GCAuth = function ()
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		var self = this;
		
		gc["authenticate"](function (error) {
			if (error)
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAuthError, self);
			else
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAuthed, self);
		});
	};
	
	Acts.prototype.GCReportAchievement = function (name_, percent_)
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		var self = this;
		
		gc["reportAchievement"](name_, percent_, function (error) {
			if (error)
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAchievementError, self);
			else
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAchievementSuccess, self);
		});
	};
	
	Acts.prototype.GCReportAchievementAdd = function (name_, percent_)
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		var self = this;
		
		gc["reportAchievementAdd"](name_, percent_, function (error) {
			if (error)
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAchievementError, self);
			else
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCAchievementSuccess, self);
		});
	};
	
	Acts.prototype.GCShowAchievements = function ()
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		gc["showAchievements"]();
	};
	
	Acts.prototype.GCReportScore = function (listname_, score_)
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		var self = this;
		
		gc["reportScore"](listname_, score_, function (error) {
			if (error)
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCScoreError, self);
			else
				self.runtime.trigger(cr.plugins_.C2Ejecta.prototype.cnds.OnGCScoreSuccess, self);
		});
	};
	
	Acts.prototype.GCShowLeaderboard = function (listname_)
	{
		var gc = this.getGameCenter();
		
		if (!gc)
			return;
		
		gc["showLeaderboard"](listname_);
	};

	pluginProto.acts = new Acts();
	
	//////////////////////////////////////
	// Expressions
	function Exps() {};
	
	Exps.prototype.TextInput = function (ret)
	{
		ret.set_string(this.textInput);
	};
	
	pluginProto.exps = new Exps();

}());