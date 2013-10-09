var user = {};
Dialog = function(selector) {
	var obj = $(selector);
	var close_btn = obj.find('.close');
	var content = obj.find('.content div:eq(0)');
	var visible = false;
	var self = this;
	var init = function(callback, paras) {
		obj.show();
		visible = true;
		obj.find('.overlay').attr('style', '');
		obj.find('.content').attr('style', '');
		var t = obj.find('.toolbar-title').text();
		if (t.length > 0)
			obj.find('.toolbar .title').text(t);
		if (callback) {
			$('#rdialog .toolbar .back').show().click(function() {
				;
				$('#rdialog .content div').html('');
				callback.apply(undefined, paras);
				$(this).hide();
			});
		} else {
			$('#rdialog .toolbar .back').hide();
		}
		close_btn.unbind('click');
		close_btn.click(function() {
			self.close();
		});
	}
	var close = this.close = function() {
		obj.hide();
		$('.rdialog-background').removeClass('rdialog-background');
		content.html('');
		visible = false;
	}
	this.popup = function() {
		var callback = undefined, paras = [];
		if (arguments.length > 2) {
			var callback = arguments[2];
			var paras = arguments[3];
		}
		if (content.html().length > 0 && !callback)
			close_dialog();
		if (callback) {
			content.html('');
		}
		if (arguments.length > 1) {
			var selector = arguments[0], data = arguments[1];
			$(selector).tmpl(data).appendTo(content);
		} else {
			var html = arguments[0];
			content.html(html);
		}
		init(callback, paras);
	}
	this.close_action = function(func) {
		close_btn.click(function() {
			func();
			self.close();
		});
	}
	this.status = function() {
		return visible;
	}
	this.setHeight = function(h) {
		if (h == 'auto') {
			obj.find('.content').height('auto');
			setTimeout(function() {
				obj.find('.overlay').height(obj.find('.content').height() + 40);
			}, 50);
		} else {
			obj.find('.overlay').height(h + 40);
			obj.find('.content').height(h);
		}
		var h = 0.45 * ($(window).height() - obj.find('.content').height() - 60);
		var w = $('#right-content').offset().left + 0.45 * ($('#right-content').width() - obj.find('.content').width());
		obj.offset({
			left : w,
			top : h
		});
	}
	this.setFixHeight = function(h) {
		if (h == "main") {
			set_top();
			var l = Math.min($('#main').height() - 70, $(window).height() - 80);
			obj.find('.content').height(l);
			obj.find('.overlay').height(l + 70);
			$(window).scroll(function() {
				set_top();
			})
		}
		function set_top() {
			var fix_top = $('#content').offset().top;
			var w_scroll_top = $(window).scrollTop();
			if (fix_top < w_scroll_top) {
				var o = $('#rdialog').offset();
				o.top = w_scroll_top;
				$('#rdialog').offset(o);
			} else {
				var o = $('#rdialog').offset();
				o.top = fix_top;
				$('#rdialog').offset(o);
			}
		}
	}
}
window.dialog = new Dialog('#dialog');
function close_dialog() {
	dialog.close();
	if (typeof rdialog != 'undefined')
		rdialog.close();
}
function get_url(name, d) {
	var url_str = URLS[name];
	if (url_str) {
		var url = $.tmpl(url_str, d).text();
	} else {
		var url = ""
	}
	return url
}
function set(array) {
	var new_array = []
	$.each(array, function(key, element) {
		var i = $.inArray(element, new_array);
		if (i == -1) {
			new_array.push(element);
		}
	})
	return new_array;
}
function is_equal(a, b) {
	return $.trim('' + a).toLowerCase() == $.trim('' + b).toLowerCase() ? true : false;
}
jQuery.fn.outerHTML = function() {
	return $('<div></div>').append(this.eq(0).clone()).html();
};
String.format = function() {
	var s = arguments[0];
	for ( var i = 0; i < arguments.length - 1; i++) {
		var reg = new RegExp("\\{" + i + "\\}", "gm");
		s = s.replace(reg, arguments[i + 1]);
	}
	return s;
}
String.clean = function() {
	if (!arguments[0])
		return false;
	var s = arguments[0];
	s = s.toLowerCase()
	s = s.replace(/[\",.!:;?]+/g, ' ');
	s = s.replace(/[\']/g, '');
	s = s.replace(/\s{2,}/g, ' ').replace(/^\s*/, '').replace(/\s*$/, '');
	return s
}
String.prototype.clean = function() {
	var s = this.toLowerCase();
	s = s.replace(/[\",.!:;?]+/g, ' ');
	s = s.replace(/[\']/g, '');
	s = s.replace(/\s{2,}/g, ' ').replace(/^\s*/, '').replace(/\s*$/, '');
	return s
}
String.prototype.clean_space = function() {
	var s = this;
	s = s.replace(/\s{2,}/g, ' ').replace(/^\s*/, '').replace(/\s*$/, '');
	return s
}
Array.prototype.append = function(element) {
	if (Object.prototype.toString.call(this) == '[object Array]') {
		this.push(element);
		return this;
	} else {
		return false;
	}
}
Array.prototype.uniq = function() {
	var new_array = new Array();
	$.each(this, function(k, v) {
		if ($.inArray(v, new_array) == -1) {
			new_array.push(v);
		}
	});
	return new_array;
}
Array.prototype.last = function() {
	var l = this.length - 1;
	return this[l];
}
Array.prototype.max = function() {
	var a = this.slice(0);
	var b = a.sort().reverse();
	return b[0];
}
Array.prototype.min = function() {
	var a = this.slice(0);
	return a.sort()[0];
}
Array.prototype.remove = function(v) {
	var i = $.inArray(v, this);
	if (i == -1)
		return false;
	this.splice(i, 1);
	return this;
}
Array.prototype.shuffle = function() {
	var s = [];
	while (this.length)
		s.push(this.splice(Math.random() * this.length, 1)[0]);
	while (s.length)
		this.push(s.pop());
	return this;
}
$.fn.parseTemplate = function(data) {
	var str = (this).html();
	var _tmplCache = {}
	var err = "";
	try {
		var func = _tmplCache[str];
		if (!func) {
			var strFunc = "var p=[],print=function(){p.push.apply(p,arguments);};"
					+ "with(obj){p.push('"
					+ str.replace(/[\r\t\n]/g, " ").replace(/'(?=[^#]*#>)/g, "\t").split("'").join("\\'").split("\t").join("'").replace(/<#=(.+?)#>/g, "',$1,'").split("<#").join("');").split("#>")
							.join("p.push('") + "');}return p.join('');";
			func = new Function("obj", strFunc);
			_tmplCache[str] = func;
		}
		return func(data);
	} catch (e) {
		err = e.message;
	}
	return "< # ERROR: " + err.toString() + " # >";
}
$.fn.highlight_list = function() {
	var ul = this;
	if (undefined != arguments[0]) {
		var list_value = arguments[0]
	} else {
		var list_value = $(ul).attr('list_value');
		if (undefined == list_value)
			return false;
	}
	ul.find('li').removeClass('active');
	ul.find('li.' + list_value).addClass('active');
	return false;
}
$.fn.init_val = function() {
	var self = this;
	init(self);
	this.bind('blur', function() {
		init(self);
	})
	this.bind('click', function() {
		clean(self)
	});
	function init(self) {
		var i = self.attr('init');
		var v = self.val();
		if (v == "") {
			self.val(i);
		}
	}
	function clean(self) {
		var i = self.attr('init');
		var v = self.val();
		if (i == v)
			self.val('');
	}
}
$.fn.hasParent = function(p) {
	return this.filter(function() {
		return !!$(this).closest(p).length;
	});
}
is_flash_detected = function() {
	if ($.browser.webkit)
		return true;
	if (navigator.mimeTypes && navigator.mimeTypes["application/x-shockwave-flash"]) {
		return true;
	} else if (document.all && (navigator.appVersion.indexOf("Mac") == -1)) {
		try {
			var xObj = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			if (xObj) {
				return true;
				xObj = null;
			}
		} catch (e) {
		}
		;
	}
	return false;
}
example_sort = function(a, b) {
	var ra = Examples[a];
	var rb = Examples[b];
	return rb.likes - ra.likes;
}
note_sort = function(a, b) {
	var na = Notes[a];
	var nb = Notes[b];
	return nb.likes - na.likes;
}
countProp = function(obj) {
	var count = 0;
	var child;
	for (child in obj) {
		count++;
	}
	return count;
}
jmerge = function() {
	if (arguments.length == 0)
		return false;
	var o = {};
	$.each(arguments, function(key, argument) {
		$.each(argument, function(i, v) {
			o[i] = v;
		})
	})
	return o;
}
jslice = function(obj, start_index, length) {
	if (arguments.length == 1) {
		start_index = 0;
		length = Infinity
	}
	if (arguments.length == 2) {
		length = Infinity
	}
	if (arguments.length > 3 || arguments.length == 0)
		return false;
	if (!/[\d]+/.test(start_index))
		return false;
	if (!/[\d]+|Infinity/.test(length))
		return false;
	var a = [];
	var o = {};
	var end_index = start_index + length + 1;
	$.each(obj, function(k, v) {
		a.append(k);
		if (a.length > start_index && a.length < end_index) {
			o[k] = v;
		}
	})
	return o;
}
range = function(b, e) {
	var r = [];
	for (i = b; i <= e; i++) {
		r.push(i);
	}
	return r;
}
get_pages = function(len, items_per_page) {
	if (len == 0)
		return [];
	var pages = [];
	var v = len / items_per_page;
	var m = len % items_per_page == 0 ? v : parseInt(v) + 1;
	pages = range(1, m);
	return pages;
}
get_pagor = function(elements_array, counts_per_page, current_page) {
	var pagor = {};
	pagor.pages = Math.ceil(elements_array.length / counts_per_page);
	pagor.first = current_page == 1 ? false : 1;
	pagor.prev = Math.max(current_page - 1, 1);
	pagor.next = Math.min(current_page + 1, pagor.pages);
	pagor.last = current_page == pagor.pages ? false : pagor.pages;
	pagor.first_element_index = (current_page - 1) * counts_per_page;
	;
	pagor.last_element_index = current_page * counts_per_page - 1;
	pagor.page_elements = elements_array.splice(pagor.first_element_index, counts_per_page);
	return pagor;
}
implement_pagor = function(pagor_type, pagor) {
	if (!pagor_type)
		pagor_type = {
			name : "table",
			type : "full"
		};
	if (pagor.type) {
		var tmpl_id = "#" + pagor_type.name + "-" + pagor_type.type + "-pagor-tmpl";
	} else {
		var tmpl_id = "#" + pagor_type.name + "-pagor-tmpl";
	}
	var page_html = $(tmpl_id).tmpl(pagor).html();
	return page_html;
}
get_UTC_time = function(offset) {
	var d = new Date();
	var utc = d.getTime() + (d.getTimezoneOffset() * 60 * 1000);
	var nd = new Date(utc + (1 * 3600 * 1000 * offset));
	return nd;
}
showhovers = function(obj) {
	$(obj).find('.actions-hover').css({
		'visibility' : 'visible'
	});
}
hidehovers = function(obj) {
	$(obj).find('.actions-hover').css({
		'visibility' : 'hidden'
	});
}
selectTab = function(node) {
	var active_id = '#tour' + $('ul.tourtabs li.selected').attr('id').split('-')[1]
	var id = '#tour' + $(node).parent().attr('id').split('-')[1]
	$('ul.tourtabs li').removeClass('selected');
	$(node).parent().addClass('selected');
	$(active_id).hide();
	$(id).show();
}
clearArea = function(node) {
	$(node).attr('value', '');
	$(node).css({
		'color' : '#EC5800'
	})
	$(node).focus();
	$(node).keyup(function(e) {
		code = e.which;
		if (code == 37 || code == 39) {
			return;
		}
	})
}
clearOriginalArea = function() {
	original_value = $(this).attr('original_value');
	value = $(this).val();
	if (original_value == value) {
		$(this).val('');
	}
	return;
}
higherArea = function(node) {
	var text = $(node).text();
	if (text.length > 20) {
		$(node).css({
			'height' : '50px'
		});
	}
}
preload_mp3 = function(word) {
	return false;
}
play_mp3 = function(audio_url) {
	if (!audio_url)
		return false;
	try {
		var sound = soundManager.getSoundById('shanbay_audio');
		if (!sound)
			return false;
	} catch (err) {
		return false;
	}
	if (sound.isHTML5) {
		sound.play({
			url : audio_url
		})
	} else {
		sound.load({
			url : audio_url,
			autoPlay : true
		}).play();
	}
}
play_audio = function() {
	$('audio').trigger('click');
}
function show_edit_definition(id) {
	var df = $('#cndf span.dfnt').text().replace(/\s{2,}/g, ' ').replace(/^\s/, '').replace(/\s$/, '');
	var review = {
		id : id,
		definition : df
	};
	dialog.popup('#edit-definition-tmpl', review);
	$('#edit-definition textarea').focus();
	$('#edit-definition input[type=button]').click(function() {
		return edit_definition(id);
	})
	return false;
}
function edit_definition(id) {
	var review = reviews[id];
	var df = $('#edit-definition textarea[name="definition"]').val().replace(/\s{2,}/g, ' ').replace(/^\s/, '').replace(/\s$/, '');
	if (df.length == 0) {
		$('#edit-definition').find('span.hint').show();
		return false;
	} else {
		$('#edit-definition').find('span.hint').hide();
		$("#edit-definition").find('span.loading').show();
		$.post('/definition/edit/' + id + '/', {
			'definition' : df
		}, function(data) {
			$('#edit-definition').find('.loading').hide();
			$('#cndf span.dfnt').text(df);
			review.definition = df;
			close_dialog();
		})
	}
	return false;
}
validate_words = function(words, current_words) {
	var s = form_letter_object(words);
	var t = form_letter_object(current_words);
	var hit = {};
	var duplicate = {};
	var miss = {};
	var error = {};
	$.each(t, function(key) {
		value = t[key] - s[key];
		if (value <= 0) {
			hit[key] = t[key]
		} else if (value > 0) {
			duplicate[key] = value
		} else {
			error[key] = t[key]
		}
	});
	$.each(s, function(key) {
		value = s[key] - t[key];
		if (!t[key]) {
			miss[key] = s[key];
		} else if (value > 0) {
			miss[key] = value;
		}
	})
	s_hit = get_json_string(hit);
	s_miss = get_json_string(miss);
	s_duplicate = get_json_string(duplicate);
	s_error = get_json_string(error);
	var fours = [ s_hit, s_miss, s_duplicate, s_error ]
	return fours
}
form_letter_object = function(words) {
	var words_letter = {};
	$.each(words, function(key, letter) {
		if (!words_letter[letter]) {
			words_letter[letter] = 1;
		} else {
			words_letter[letter] += 1;
		}
	})
	return words_letter
}
get_json_string = function(r_o) {
	var letters = [];
	var v;
	$.each(r_o, function(key) {
		v = r_o[key];
		for (i = 1; i <= v; i++) {
			letters.push(key);
		}
	})
	return letters.join(' ');
}
resolveLearning = function(learning_id, callback, params) {
	$.get('/learning/resolve/' + learning_id + '/', function(data) {
		if (callback) {
			callback.apply(undefined, params);
		}
	})
}
failLearning = function(learning_id, callback, params) {
	$.get('/learning/fail/' + learning_id + '/', function(data) {
		if (callback) {
			callback.apply(undefined, params);
		}
	})
}
setLearningReviewTimes = function(learning_id, callback, params) {
	$.get('/learning/review_times/set/' + learning_id + '/', function(data) {
		if (callback) {
			callback.apply(undefined, params);
		}
	});
}
checkNull = function() {
	for (i = 0; i < arguments.length; i++) {
		if (document.getElementById(arguments[i]).value.length == 0) {
			alert('内容不能为空');
			return false;
		}
	}
	return true;
}
$('.tabs .tab').live('click', function() {
	var active_id = $('.tab.active').attr('id').split('-').slice(1).join('-')
	active_id = '#' + active_id
	$('.tab').removeClass('active');
	$(this).addClass('active');
	var current_id = $(this).attr('id').split('-').slice(1).join('-')
	current_id = '#' + current_id
	$(active_id).hide();
	$(current_id).show();
	return false;
})
$('ul.horizon-list').highlight_list();
$('#letter select').change(function() {
	$('#letter form').submit();
});
function search(word) {
	$('#search-content').show().html($('#search-loading').html());
	register_template('search', 'search', 2);
	var url = "/bdc/search/word/";
	var matched_pattern = word.match(/[\w- ]+/);
	if (!matched_pattern) {
		$('#search-content').html($.tmpl('no_search'));
		$('#search .close').click(function() {
			$('#search-content').hide();
		});
	} else {
		word = $.trim(matched_pattern[0]).replace(/\s{2,}/g, ' ');
		$.get(url, {
			word : word
		}, function(data) {
			if (data.result == 0) {
				$('#search input[type=text]').val('');
				$('#search-content').html($.tmpl('search', data));
				$('#add-word').click(function() {
					var add_url = $(this).attr('href');
					$(this).hide();
					$('#search .add .loading').show();
					$.get(add_url, function(data) {
						$('#search .add .loading').hide();
						var url = "/learning/" + data.id + "/";
						$('#search .added').show().find('a').attr('href', url);
					})
					return false;
				})
				$('#search .forget').click(function() {
					var url = "/review/retention/set/" + data.learning_id;
					$.ajax({
						url : url,
						data : {
							retention : 1
						},
						dataType : "json",
						cache : false,
						success : function(data) {
							$('#added-word').slideUp();
							$('#forgot-msg').slideDown();
						}
					})
				});
				$('#search-content .speaker').click(function() {
					play_mp3(data.us_audio);
				});
			} else {
				$('#search-content').html($.tmpl('search_fail', {
					word : word
				}));
			}
			$('#search .close').click(function() {
				$('#search-content').hide();
			});
		})
	}
	return false;
}
function register_template(jquery_template, template_name, version) {
	if ($.template[jquery_template]) {
		return;
	}
	if (version == undefined) {
		version = 1;
	}
	try {
		var url = get_url("tmpl", {
			"template_name" : template_name,
			"version" : version
		});
	} catch (err) {
		var url = ""
	}
	if (url.length == 0)
		url = "/tmpl/" + template_name + "_v." + version + ".html";
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			try {
				$('#templates').html(data);
				$('#templates').find('script').each(function(index) {
					var id = $(this).attr('id').replace(/_tmpl/, '');
					$.template(id, $(this));
				})
				$('#templates').html('');
			} catch (err) {
				if (console) {
					console.log("is templates container existed?");
				}
				return false;
			}
		}
	})
}
function add_href_to_page() {
	$('.page').each(function(k, v) {
		var $page = $(v);
		var url = window.location.pathname;
		var page = $(v).attr('page');
		var offset = $(v).attr('offset');
		var start = offset * (page - 1) + 1;
		$(v).attr('href', url + '?start=' + start + '&offset=' + offset);
	})
}
jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') {
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString();
		}
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires, path, domain, secure ].join('');
	} else {
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for ( var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};
$.ajaxSetup({
	beforeSend : function(xhr, settings) {
		function getCookie(name) {
			var cookieValue = null;
			if (document.cookie && document.cookie != '') {
				var cookies = document.cookie.split(';');
				for ( var i = 0; i < cookies.length; i++) {
					var cookie = jQuery.trim(cookies[i]);
					if (cookie.substring(0, name.length + 1) == (name + '=')) {
						cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
						break;
					}
				}
			}
			return cookieValue;
		}
		function sameOrigin(url) {
			var host = document.location.host;
			var protocol = document.location.protocol;
			var sr_origin = '//' + host;
			var origin = protocol + sr_origin;
			return (url == origin || url.slice(0, origin.length + 1) == origin + '/') || (url == sr_origin || url.slice(0, sr_origin.length + 1) == sr_origin + '/')
					|| !(/^(\/\/|http:|https:).*/.test(url));
		}
		function safeMethod(method) {
			return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
		}
		if (!safeMethod(settings.type) && sameOrigin(settings.url)) {
			delete_duplicated_cookies('csrftoken');
			xhr.setRequestHeader("X-CSRFToken", getCookie('csrftoken'));
		}
	}
});
function delete_duplicated_cookies(name) {
	name += "=";
	var cookie_pattern = new RegExp(name, "g");
	var cookie_matches = document.cookie.match(cookie_pattern);
	if (cookie_matches && cookie_matches.length > 1) {
		$.cookie(name, null);
	}
}
function get_csrf_token() {
	var csrftoken = $('#csrftoken input[name=csrfmiddlewaretoken]').val();
	return csrftoken;
}
function verify_csrf() {
	delete_duplicated_cookies('csrftoken');
	var csrftoken = get_csrf_token();
	if (csrftoken) {
		$.cookie('csrftoken', csrftoken);
	}
}
function get_userinfo() {
	try {
		var url = get_url("user", {});
	} catch (err) {
		var url = "";
	}
	if (url.length == 0) {
		url = "/common/user/info/";
	}
	var t = new Date();
	$.getJSON(url, {
		t : t.getTime()
	}, function(data) {
		var result = data.result;
		if (result == 0) {
			fill_nickname(data.nickname);
			user.id = data.userid;
			user.name = data.username;
			user.is_staff = data.is_staff;
			$.cookie('username', user.name);
			$.cookie('userid', user.id);
		}
	});
}
function fill_nickname(nickname) {
	register_template('username', 'notice', 2);
	$('#userhome').html($.tmpl('username', {
		username : nickname
	}));
	$('.main-menu-sub-menu p').mouseenter(function() {
		$(this).find('a').css({
			'color' : '#CC0099'
		});
	});
	$('.main-menu-sub-menu p').mouseleave(function() {
		var a = $(this).find('a');
		if (a.attr('href') === '/accounts/logout/') {
			a.css({
				'color' : '#888888'
			});
		} else {
			a.css({
				'color' : '#209E85'
			});
		}
	});
}
function close_auth_message() {
	$('#auth-messages-container').remove();
}
function close_notification() {
	$('#notification-container').remove();
}
function toggle_menu() {
	$('#topnav .logo').click(function(event) {
		toggles(event);
	})
	$(document).click(function() {
		$('#menu-container').hide();
	})
	function toggles(event) {
		event.stopPropagation();
		var is_hidden = $('#menu-container:hidden').length;
		if (is_hidden == 1) {
			$('#menu-container').fadeIn();
		} else {
			$('#menu-container').hide();
		}
		$('#menu-content li').click(function() {
			var href = $(this).find('a').attr('href');
			window.location.href = href;
		})
	}
}
function activate_feedback() {
	$('#feedback .menu').unbind('click').click(function() {
		$('.msg').hide();
		$('#feedback-container').show();
		$(document).unbind('keyup');
		var form = $('#feedback form');
		var textarea = form.find('textarea');
		textarea.show().focus();
		form.unbind('submit').submit(function(event) {
			$('.msg').hide();
			var content = $.trim(textarea.val());
			if (content.length == 0) {
				$("#empty-msg").show();
				return false;
			}
			var feedback_info = {
				content : content
			}
			var review_id = get_feedback_review_id();
			if (review_id != undefined) {
				feedback_info.review_id = review_id;
			}
			var url = form.attr("action");
			form.find('.loading').show();
			form.find('input[type=submit]').hide();
			$.post(url, feedback_info, function(data) {
				form.find('.loading').hide();
				form.find('input[type=submit]').show();
				show_feedback_result(textarea, data);
			}, "json");
			return false;
		})
		form.find('.cancel').click(function() {
			$('#feedback-container').hide();
		})
	});
	function show_feedback_result(textarea, data) {
		if (data["status"] === 0) {
			textarea.hide().val('');
			$('#feedback-success').show();
			$('#feedback-container').fadeOut(2000);
		} else {
			$("#feedback-failed").show();
		}
	}
	function get_feedback_review_id() {
		var review_id;
		if (typeof (active_reviews) == "undefined") {
			review_id = undefined;
		} else {
			try {
				review_id = active_reviews[current_index].id;
			} catch (err) {
				review_id = undefined;
			}
		}
		return review_id;
	}
}
verify_csrf();
toggle_menu();
activate_feedback();
$('#search form').submit(function() {
	$('#search .search').trigger('click');
	return false;
})
$('#search .search').click(function(e) {
	var word = $.trim($('#search input[type=text]').val());
	$('html').click(function(event) {
		if ($(event.target).parents('#search-container').length == 0) {
			$('#search-content').hide();
			$('#search-content input').focus();
		}
	})
	return search(word);
});
$('#id_num_vocabulary').attr('disabled', true);
var deactive_sub_menu = function() {
	$('.menus li').removeClass('active');
	$('.menus li').find('a.main-menu').css({
		'color' : 'white'
	});
	$('.menus li').find('a.main-menu').css({
		'font-weight' : 'normal'
	});
	$('.right li').removeClass('active');
	$('.right li').find('a.main-menu').css({
		'color' : 'white'
	});
	$('.right li').find('a.main-menu').css({
		'font-weight' : 'normal'
	});
	$('li em').css({
		'background' : 'white'
	});
}
$('.right li').mouseleave(function() {
	deactive_sub_menu();
	$("#userhome-sub-menu").hide();
});
$('.right li').mouseenter(function() {
	var index = $('.right li').index(this);
	deactive_sub_menu();
	if (index === 0) {
		$(this).addClass('active');
		$(this).find('a.main-menu').css({
			'color' : '#209E85'
		});
		$(this).find('a.main-menu').css({
			'font-weight' : 'bold'
		});
		$(this).find('em').css({
			'background' : '#209E85'
		});
		var width = 10 + parseInt($('li#userhome').css('width').replace('px', ''));
		$('#userhome-sub-menu').css({
			'width' : width.toString() + 'px'
		});
		$('.main-menu-sub-menu').removeClass('active-sub-menu');
		$('#market-sub-menu').hide();
		$('#community-sub-menu').hide();
		$('#vocabulary-sub-menu').hide();
		$('#userhome-sub-menu').addClass('active-sub-menu').show();
	} else {
		$('#vocabulary-sub-menu').hide();
		$('#market-sub-menu').hide();
		$('#userhome-sub-menu').hide();
		$('#community-sub-menu').hide();
	}
});
$('.menus li').mouseleave(function() {
	$('.main-menu-sub-menu').removeClass('active-sub-menu');
	deactive_sub_menu();
	$('#vocabulary-sub-menu').hide();
	$('#market-sub-menu').hide();
	$('#userhome-sub-menu').hide();
	$('#community-sub-menu').hide();
});
$('.menus li').mouseenter(function() {
	var index = $('.menus li').index(this);
	$('.main-menu-sub-menu').removeClass('active-sub-menu');
	deactive_sub_menu();
	if (index !== 1) {
		$(this).addClass('active');
		$(this).find('a.main-menu').css({
			'color' : '#209E85'
		});
		$(this).find('a.main-menu').css({
			'font-weight' : 'bold'
		});
		$(this).find('em').css({
			'background' : '#209E85'
		});
	}
	if (index === 0) {
		$('#market-sub-menu').hide();
		$('#community-sub-menu').hide();
		$('#userhome-sub-menu').hide();
		$('#vocabulary-sub-menu').show().addClass('active-sub-menu');
	} else if (index === 2) {
		$('#vocabulary-sub-menu').hide();
		$('#community-sub-menu').hide();
		$('#userhome-sub-menu').hide();
		$('#market-sub-menu').show().addClass('active-sub-menu');
	} else if (index === 3) {
		$('#vocabulary-sub-menu').hide();
		$('#market-sub-menu').hide();
		$('#userhome-sub-menu').hide();
		$('#community-sub-menu').show().addClass('active-sub-menu');
	} else {
		$('#vocabulary-sub-menu').hide();
		$('#market-sub-menu').hide();
		$('#userhome-sub-menu').hide();
		$('#community-sub-menu').hide();
	}
});
$('input[name="retention"]:last').parent().parent().parent().append($('#append_after_id_retention').html());
$('#append_after_id_retention').html("");
$('#learning-mode-intro-trigger').click(function() {
	if ($('#learning-mode-intro').hasClass('active-intro')) {
		$('#learning-mode-intro').slideUp();
		$('#learning-mode-intro').removeClass('active-intro');
		$(this).html($('span#html-detail').html());
	} else {
		$('#learning-mode-intro').slideDown();
		$('#learning-mode-intro').addClass('active-intro');
		$(this).html($('span#html-slide-up').html());
	}
});
$('#id_finish_time').parent().append($('#append_after_id_finish_time').html());
$('#append_after_id_finish_time').html("");
$('#finish-time-intro-trigger').click(function() {
	if ($('#finish-time-intro').hasClass('active-intro')) {
		$('#finish-time-intro').slideUp();
		$('#finish-time-intro').removeClass('active-intro');
		$(this).html($('span#html-detail').html());
	} else {
		$('#finish-time-intro').slideDown();
		$('#finish-time-intro').addClass('active-intro');
		$(this).html($('span#html-slide-up').html());
	}
});
$('#id_num_vocabulary').parent().append($('#append_after_id_num_vocabulary').html());
$('#append_after_id_num_vocabulary').html("");
$('#num-vocabulary-intro-trigger').click(function() {
	if ($('#num-vocabulary-intro').hasClass('active-intro')) {
		$('#num-vocabulary-intro').slideUp();
		$('#num-vocabulary-intro').removeClass('active-intro');
		$(this).html($('span#html-detail').html());
	} else {
		$('#num-vocabulary-intro').slideDown();
		$('#num-vocabulary-intro').addClass('active-intro');
		$(this).html($('span#html-slide-up').html());
	}
});
$('#id_time').parent().append($('#append_after_id_time').html());
$('#append_after_id_time').html("");
$('#setting-planning #id_time').change();
$.ajaxSetup({
	cache : false
});
var BAD_DOMAINS = [ 'sohu.com.cn', 'qq.com.cn', 'gamil.com' ]
$('#id_email').blur(function(e) {
	var error_found = false;
	var self = $(this);
	var email = $(this).val();
	var match_str = email.match(/w{3}/);
	var error_msg = $('#error-msg').val();
	if (match_str) {
		$('#error_email .error ').text(error_msg);
		error_found = true;
		return false;
	}
	$.each(BAD_DOMAINS, function(i, d) {
		var r = new RegExp(d, 'i');
		if (email.match(r)) {
			var error_msg = $('#error-domain').val();
			$('#error_email .error ').text(error_msg);
			self.focus();
			error_found = true;
			return false;
		}
	})
	if (!error_found)
		$('#error_email .error ').text('');
})
var refresh_captcha = function() {
	$('img.captcha').unbind('click', refresh_captcha);
	$('img.captcha').bind('click', refresh_captcha);
	$.get('../captcha/', {}, function(new_captcha) {
		var captcha_help = $('img.captcha').next();
		$('img.captcha').prev().remove();
		$('img.captcha').prev().remove();
		$('img.captcha').remove();
		captcha_help.before(new_captcha);
		$('img.captcha').bind('click', refresh_captcha);
		return false;
	});
}
$('img.captcha').bind('click', refresh_captcha);