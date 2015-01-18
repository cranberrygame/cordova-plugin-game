function GetPluginSettings()
{
	return {
		"name":			"Ejecta",				// as appears in 'insert object' dialog, can be changed as long as "id" stays the same
		"id":			"C2Ejecta",				// this is used to identify this plugin and is saved to the project; never change it
		"version":		"1.0",					// (float in x.y format) Plugin version - C2 shows compatibility warnings based on this
		"description":	"Access iOS platform features when using the Ejecta exporter.",
		"author":		"Scirra",
		"help url":		"http://www.scirra.com/tutorials/992/how-to-export-to-ios-with-ejecta",
		"category":		"Platform specific",				// Prefer to re-use existing categories, but you can set anything here
		"type":			"object",				// either "world" (appears in layout and is drawn), else "object"
		"rotatable":	false,					// only used when "type" is "world".  Enables an angle property on the object.
		"flags":		0						// uncomment lines to enable flags...
						| pf_singleglobal		// exists project-wide, e.g. mouse, keyboard.  "type" must be "object".
					//	| pf_texture			// object has a single texture (e.g. tiled background)
					//	| pf_position_aces		// compare/set/get x, y...
					//	| pf_size_aces			// compare/set/get width, height...
					//	| pf_angle_aces			// compare/set/get angle (recommended that "rotatable" be set to true)
					//	| pf_appearance_aces	// compare/set/get visible, opacity...
					//	| pf_tiling				// adjusts image editor features to better suit tiled images (e.g. tiled background)
					//	| pf_animations			// enables the animations system.  See 'Sprite' for usage
					//	| pf_zorder_aces		// move to top, bottom, layer...
					//  | pf_nosize				// prevent resizing in the editor
					//	| pf_effects			// allow WebGL shader effects to be added
					//  | pf_predraw			// set for any plugin which draws and is not a sprite (i.e. does not simply draw
												// a single non-tiling image the size of the object) - required for effects to work properly
	};
};

////////////////////////////////////////
// Parameter types:
// AddNumberParam(label, description [, initial_string = "0"])			// a number
// AddStringParam(label, description [, initial_string = "\"\""])		// a string
// AddAnyTypeParam(label, description [, initial_string = "0"])			// accepts either a number or string
// AddCmpParam(label, description)										// combo with equal, not equal, less, etc.
// AddComboParamOption(text)											// (repeat before "AddComboParam" to add combo items)
// AddComboParam(label, description [, initial_selection = 0])			// a dropdown list parameter
// AddObjectParam(label, description)									// a button to click and pick an object type
// AddLayerParam(label, description)									// accepts either a layer number or name (string)
// AddLayoutParam(label, description)									// a dropdown list with all project layouts
// AddKeybParam(label, description)										// a button to click and press a key (returns a VK)
// AddAnimationParam(label, description)								// a string intended to specify an animation name
// AddAudioFileParam(label, description)								// a dropdown list with all imported project audio files

////////////////////////////////////////
// Conditions

// AddCondition(id,					// any positive integer to uniquely identify this condition
//				flags,				// (see docs) cf_none, cf_trigger, cf_fake_trigger, cf_static, cf_not_invertible,
//									// cf_deprecated, cf_incompatible_with_triggers, cf_looping
//				list_name,			// appears in event wizard list
//				category,			// category in event wizard list
//				display_str,		// as appears in event sheet - use {0}, {1} for parameters and also <b></b>, <i></i>
//				description,		// appears in event wizard dialog when selected
//				script_name);		// corresponding runtime function name
				
AddCondition(0, cf_trigger, "On text input", "Ejecta", "On text input", "Triggered after 'Prompt for text input' when completed.", "OnTextInput");

AddCondition(1, cf_trigger, "On ad loaded", "iAd", "On ad loaded", "Triggered when an iAd was loaded successfully.", "OnAdLoad");

AddCondition(2, cf_trigger, "On ad error", "iAd", "On ad error", "Triggered when there was an error loading an iAd.", "OnAdError");

AddCondition(3, cf_trigger, "On authenticated", "Game Center", "On game center authenticated", "Triggered after successfully logging in to Game Center.", "OnGCAuthed");

AddCondition(4, cf_trigger, "On authentication error", "Game Center", "On game center authentication error", "Triggered if an error occurs authenticating the Game Center login.", "OnGCAuthError");

AddCondition(5, cf_none, "Is authenticated", "Game Center", "Is Game Center authenticated", "True if the user is currently logged in to Game Center.", "IsGCAuthed");

AddCondition(6, cf_trigger, "On achievement report success", "Game Center", "On game center achievement report success", "Triggered after successfully reporting an achievement to Game Center.", "OnGCAchievementSuccess");

AddCondition(7, cf_trigger, "On achievement report error", "Game Center", "On game center achievement report error", "Triggered if an error occurs reporting an achievement to Game Center.", "OnGCAchievementError");

AddCondition(8, cf_trigger, "On score report success", "Game Center", "On game center score report success", "Triggered after successfully reporting a score to Game Center.", "OnGCScoreSuccess");

AddCondition(9, cf_trigger, "On score report error", "Game Center", "On game center score report error", "Triggered if an error occurs reporting a score to Game Center.", "OnGCScoreError");

////////////////////////////////////////
// Actions

// AddAction(id,				// any positive integer to uniquely identify this action
//			 flags,				// (see docs) af_none, af_deprecated
//			 list_name,			// appears in event wizard list
//			 category,			// category in event wizard list
//			 display_str,		// as appears in event sheet - use {0}, {1} for parameters and also <b></b>, <i></i>
//			 description,		// appears in event wizard dialog when selected
//			 script_name);		// corresponding runtime function name

AddStringParam("Title", "The text prompt title.");
AddStringParam("Message", "The text prompt message.");
AddAction(0, af_none, "Prompt for text input", "Ejecta", "Prompt for text input with title <i>{0}</i> message <i>{1}</i>", "Open a dialog where the user can enter some text with an on-screen keyboard.", "PromptForText");

AddComboParamOption("top");
AddComboParamOption("bottom");
AddComboParam("Position", "Where to display the ad.");
AddAction(1, af_none, "Show ad", "iAd", "Show iAd at {0}", "Show an ad banner.", "ShowAd");

AddAction(2, af_none, "Hide ad", "iAd", "Hide ad", "Hide an ad banner that is showing.", "HideAd");

AddAction(3, af_none, "Soft authenticate", "Game Center", "Soft authenticate Game Center", "Attempt an automatic login if a previous login was successful.", "GCSoftAuth");

AddAction(4, af_none, "Authenticate", "Game Center", "Authenticate Game Center", "Explicitly log in to Game Center with a login form.", "GCAuth");

AddStringParam("Name", "Name of the achievement to report.");
AddNumberParam("Percentage", "Percentage completion of the achievement, from 0-100.", "100");
AddAction(5, af_none, "Report achievement", "Game Center", "Report achievement <i>{0}</i> at <i>{1}</i> percent", "Report an achievement to Game Center with an absolute completion percentage.", "GCReportAchievement");

AddStringParam("Name", "Name of the achievement to report.");
AddNumberParam("Add percentage", "Percentage completion of the achievement to add to the current completion.", "10");
AddAction(6, af_none, "Report achievement progress", "Game Center", "Report achievement <i>{0}</i> progress of <i>{1}</i> percent", "Report an achievement to Game Center and add to the current completion percentage.", "GCReportAchievementAdd");

AddAction(7, af_none, "Show achievements", "Game Center", "Show Game Center achievements", "Display the achievements in Game Center.", "GCShowAchievements");

AddStringParam("List name", "Name of the list to report the score to.");
AddNumberParam("Score", "The score to report.");
AddAction(8, af_none, "Report score", "Game Center", "Report score of <b>{1}</b> to list <i>{0}</i>", "Report a score to a Game Center leaderboard list.", "GCReportScore");

AddStringParam("List name", "Name of the list to display the leaderboard for.");
AddAction(9, af_none, "Show leaderboard", "Game Center", "Show Game Center leaderboard for list <i>{0}</i>", "Display a leaderboard in Game Center.", "GCShowLeaderboard");

////////////////////////////////////////
// Expressions

// AddExpression(id,			// any positive integer to uniquely identify this expression
//				 flags,			// (see docs) ef_none, ef_deprecated, ef_return_number, ef_return_string,
//								// ef_return_any, ef_variadic_parameters (one return flag must be specified)
//				 list_name,		// currently ignored, but set as if appeared in event wizard
//				 category,		// category in expressions panel
//				 exp_name,		// the expression name after the dot, e.g. "foo" for "myobject.foo" - also the runtime function name
//				 description);	// description in expressions panel

AddExpression(0, ef_return_string, "", "Ejecta", "TextInput", "Return the text entered after 'On text input'.");

////////////////////////////////////////
ACESDone();

////////////////////////////////////////
// Array of property grid properties for this plugin
// new cr.Property(ept_integer,		name,	initial_value,	description)		// an integer value
// new cr.Property(ept_float,		name,	initial_value,	description)		// a float value
// new cr.Property(ept_text,		name,	initial_value,	description)		// a string
// new cr.Property(ept_color,		name,	initial_value,	description)		// a color dropdown
// new cr.Property(ept_font,		name,	"Arial,-16", 	description)		// a font with the given face name and size
// new cr.Property(ept_combo,		name,	"Item 1",		description, "Item 1|Item 2|Item 3")	// a dropdown list (initial_value is string of initially selected item)
// new cr.Property(ept_link,		name,	link_text,		description, "firstonly")		// has no associated value; simply calls "OnPropertyChanged" on click

var property_list = [
	];
	
// Called by IDE when a new object type is to be created
function CreateIDEObjectType()
{
	return new IDEObjectType();
};

// Class representing an object type in the IDE
function IDEObjectType()
{
	assert2(this instanceof arguments.callee, "Constructor called as a function");
};

// Called by IDE when a new object instance of this type is to be created
IDEObjectType.prototype.CreateInstance = function(instance)
{
	return new IDEInstance(instance);
};

// Class representing an individual instance of an object in the IDE
function IDEInstance(instance, type)
{
	assert2(this instanceof arguments.callee, "Constructor called as a function");
	
	// Save the constructor parameters
	this.instance = instance;
	this.type = type;
	
	// Set the default property values from the property table
	this.properties = {};
	
	for (var i = 0; i < property_list.length; i++)
		this.properties[property_list[i].name] = property_list[i].initial_value;
		
	// Plugin-specific variables
	// this.myValue = 0...
};

// Called when inserted via Insert Object Dialog for the first time
IDEInstance.prototype.OnInserted = function()
{
};

// Called when double clicked in layout
IDEInstance.prototype.OnDoubleClicked = function()
{
};

// Called after a property has been changed in the properties bar
IDEInstance.prototype.OnPropertyChanged = function(property_name)
{
};

// For rendered objects to load fonts or textures
IDEInstance.prototype.OnRendererInit = function(renderer)
{
};

// Called to draw self in the editor if a layout object
IDEInstance.prototype.Draw = function(renderer)
{
};

// For rendered objects to release fonts or textures
IDEInstance.prototype.OnRendererReleased = function(renderer)
{
};