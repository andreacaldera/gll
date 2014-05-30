var require = require('requirejs');
require.config({
  shim: {
    underscore: {
      exports: '_'
    },
    bookingParser: {
    	deps: ["underscore.string"]
    },
    backbone: {
      deps: ["underscore", "underscore.string", "jquery"],
      exports: "Backbone"
    },
    
    'bootstrap/bootstrap-slider': { deps: ['jquery'], exports: '$.fn.slider' }
  }
});

//the "main" function to bootstrap your code
require(['jquery', 'underscore.string', 'underscore', 'bookingParser'], function ($, _s, _, bookingParser) {  

	console.log('yay');
	var s = 'some string';
	console.dir(_s.capitalize('epeli'));

});