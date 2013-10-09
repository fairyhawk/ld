var SUCCESS_STATUS = 0;
var FAIL_STATUS = 1;
var SUBSCRIBE_WORDBOOK_SUCCESS = 0;
var SUBSCRIBE_WORDBOOK_SUBSCRIBED = 1;
var SUBSCRIBE_WORDBOOK_IN_SUFFICIENT_COINS = 2;
var SUBSCRIBE_WORDBOOK_IN_SUFFICIENT_PROGESS = 3;
var MIN_TITLE_LENGTH = 5;
var MIN_DESCRIPTION_LENGTH = 50;
$.ajaxSetup({
	cache : false
});
$('.edit-wordbook-cover').unbind('click').click(function() {
	$(this).hide();
	$(this).next().show();
});
$('.btn-cancel-change-wordbook-cover').unbind('click').click(function() {
	$(this).parent().hide();
	$(this).parent().prev().show();
});
$('.btn-change-wordbook-cover-submit').click(function() {
	var cover_image = $(this).parent().find('.cover-image');
	if (cover_image.val() == false) {
		alert($('#wordbook-empty-cover').html());
		return false;
	}
});
$("#wordbook-unsave-button").click(function() {
	$(this).attr('disabled', 'disabled');
	var wordbook_id = $('#wordbook-id-html').html();
	$('#wordbook-unsave-div').slideUp('slow');
	$('.wordbook-saving-hint').slideDown('slow');
	$.get('/letter/unsubscribe/' + wordbook_id + '/', {}, function(data) {
		var wordlist_ids = data.wordlist_ids;
		if (data.status == SUCCESS_STATUS) {
			$('.wordbook-saving-hint').slideUp('slow');
			$('#wordbook-save-div').slideDown('slow');
			$('#wordbook-save-button').removeAttr('disabled');
		} else {
			alert($('#wordbook-subscribe-failed').html());
		}
	}, 'json');
	$(this).removeAttr('disabled');
});
$('#wordbook-save-button').click(function() {
	$(this).attr('disabled', 'disabled');
	var wordbook_id = $('#wordbook-id-html').html();
	$('#wordbook-save-div').slideUp('slow');
	$('.wordbook-saving-hint').slideDown('slow');
	$.get('/letter/subscribe/' + wordbook_id + '/', {}, function(data) {
		var wordlist_ids = data.wordlist_ids;
		if (data.status == SUBSCRIBE_WORDBOOK_SUCCESS) {
			$('.wordbook-in-sufficient-progress-hint').hide();
			$('.wordbook-saving-hint').slideUp('slow');
			$('#wordbook-unsave-div').slideDown('slow');
		} else if (data.status == SUBSCRIBE_WORDBOOK_SUBSCRIBED) {
			$('.wordbook-in-sufficient-progress-hint').hide();
			$('.wordbook-saving-hint').slideUp('slow');
			$('.wordbook-subscribed-hint').slideDown('slow');
		} else if (data.status == SUBSCRIBE_WORDBOOK_IN_SUFFICIENT_COINS) {
			$('.wordbook-in-sufficient-progress-hint').hide();
			$('.wordbook-saving-hint').slideUp('slow');
			$('.wordbook-in-sufficient-coins-hint').slideDown('slow');
		} else {
			$('.wordbook-saving-hint').slideUp('slow');
			$('#wordbook-save-div').slideDown('slow');
			$('.wordbook-in-sufficient-progress-hint').slideDown('slow');
		}
	}, 'json');
	$(this).removeAttr('disabled');
});
var highlight_wordbook_category = function() {
	wordbook_id = $('#current-category-id').text();
	var category_selector = 'ul#wordbook-category-list li[data="' + wordbook_id + '"]';
	$(category_selector).addClass('active');
};
var submit_wordbook = function() {
	$('#wordbook-submit-button').click(function() {
		var id = $(this).attr('wordbook_id');
		$.get('/wordbook/submit/' + id + '/', function(data) {
			if (data.status == SUCCESS_STATUS) {
				location.reload();
			} else {
				var error_message = $('#wordbook-submit-error').html();
				alert(error_message);
			}
		}, 'json');
	});
}
var verify_user_create_wordbook = function() {
	$('#wordbook-verify-agreebtn').click(function() {
		$('.wordbook-verify-comment').slideDown('fast');
		$('span.verify-result').html('agree');
		var placeholder = $('#wordbook-verify-msg-agree-placeholder').html();
		var verify_status = $('#wordbook-verify-agree-status').html();
		$('.wordbook-button-area').html(verify_status);
		$('.wordbook-verify-comment textarea').attr('placeholder', placeholder);
	});
	$('#wordbook-verify-refusebtn').click(function() {
		$('.wordbook-verify-comment').slideDown('fast');
		$('span.verify-result').html('refuse');
		var placeholder = $('#wordbook-verify-msg-refuse-placeholder').html();
		var verify_status = $('#wordbook-verify-refuse-status').html();
		$('.wordbook-button-area').html(verify_status);
		$('.wordbook-verify-comment textarea').attr('placeholder', placeholder);
	});
	$('.verify-wordbook-submit').click(function() {
		var result = $('span.verify-result').html();
		var reason = $('.wordbook-verify-comment textarea').val();
		process_wordbook_verification(result, reason);
	});
}
function notify_user(recipient, body, subject) {
	$.post('/17mail/compose/', {
		recipient : recipient,
		body : body,
		subject : subject
	}, function(data) {
		return false;
	});
}
function process_wordbook_verification(result, reason) {
	var wordbook_id = $('.verify-wordbook-id').html();
	$.post('/wordbook/verify/process/', {
		result : result,
		wordbook_id : wordbook_id
	}, function(data) {
		if (data.status == SUCCESS_STATUS) {
			$('.wordbook-verify-comment').slideUp('fast');
			var recipient = $('.wordbook-owner .wordbook-owner-username').html();
			var msg_subject = $('#wordbook-verify-msg-subject').html();
			if (data.result == 'agree') {
				var msg_agree = $('#wordbook-verify-msg-agree').html();
				var body = msg_agree + '"' + reason + '".';
				notify_user(recipient, body, msg_subject);
				setTimeout('location.reload();', 1000);
			} else {
				var msg_refuse_head = $('#wordbook-verify-msg-refuse-head').html();
				var msg_refuse_tail = $('#wordbook-verify-msg-refuse-tail').html();
				var body = msg_refuse_head + '"' + reason + '".' + msg_refuse_tail;
				notify_user(recipient, body, msg_subject);
				setTimeout('location.reload();', 1000);
			}
		}
	}, 'json');
}
function modify_wordbook_basic_info() {
	$('#modify-wordbook-fail').hide();
	var wordbook_id = $('#wordbook-id').text();
	var url = '/wordbook/verify/modify/' + wordbook_id + '/';
	$('#wordbook-basic-info-modal').modal();
	$('#wordbook-basic-info-modal .btn-primary').click(function() {
		var title = $('#wordbook-basic-info-modal #modify-wordbook-title').val();
		var description = $('#wordbook-basic-info-modal #modify-wordbook-description').val();
		var category = $('#wordbook-basic-info-modal #modify-wordbook-category').val()
		$.post(url, {
			'title' : title,
			'description' : description,
			'category' : category
		}, function(data) {
			if (data.status == 0) {
				location.reload();
			} else {
				$('#modify-wordbook-fail').show();
			}
		});
	});
}
function trigger_modify_wordbook_publish_status() {
	$('.edit-wordbook-publish-status').unbind('click').click(function() {
		var wordbook_id = $(this).attr('data');
		var wordbook_status = $(this).attr('status');
		var recipient = $(this).parent().find('.author').text();
		var wordbook_title = $(this).parent().find('.wordbook-title').text();
		console.log(wordbook_status);
		$('#wordbook-id').html(wordbook_id);
		$('#wordbook-publish-status-modal').modal();
		$('#wordbook-publish-status-modal #modify-wordbook-publish-status option[value=' + wordbook_status + ']').attr('selected', true);
		var wordbook_status_name = $('#wordbook-publish-status-modal #modify-wordbook-publish-status option[value=' + wordbook_status + ']').html();
		$('#wordbook-publish-status-modal .wordbook-current-status').html(wordbook_status_name);
		$('#wordbook-publish-status-modal .btn-primary').unbind('click').click(function() {
			$(this).attr('disabled', true);
			var wordbook_id = $('#wordbook-id').text();
			var publish_status = $('#wordbook-publish-status-modal #modify-wordbook-publish-status').val()
			var msg = $('#modify-wordbook-publish-status-msg').val();
			var url = '/wordbook/manage/update_status/' + wordbook_id + '/' + publish_status + '/';
			$.get(url, function(data) {
				console.log(data);
				if (data.status == 0) {
					var body = $('#wordbook-status-change-msg-top').text() + wordbook_title + $('#wordbook-status-change-msg-middle').text() + msg + $('#wordbook-status-change-msg-bottom').text();
					var subject = $('#wordbook-status-change-msg-subject').text();
					notify_user(recipient, body, subject);
					setTimeout('location.reload();', 1000);
				} else {
					alert("Sever Error, please try again later");
				}
			});
			$(this).attr('disabled', false);
		});
	});
}
function trigger_modify_wordbook_basic_info() {
	$('#edit-wordbook-basic-info').click(function() {
		var wordbook_title = $('.wordbook-title a').text().trim();
		var wordbook_description = $('.wordbook-description div').text().trim();
		$('#wordbook-basic-info-modal #modify-wordbook-title').val(wordbook_title);
		$('#wordbook-basic-info-modal #modify-wordbook-description').val(wordbook_description);
		modify_wordbook_basic_info();
	});
}
function trigger_edit_wordbook_basic_info() {
	$('.edit-wordbook-basic-info').unbind('click').click(function() {
		var wordbook_title = $(this).parent().find('.wordbook-title').text();
		var wordbook_description = $(this).parent().find('.wordbook-description').text();
		var wordbook_id = $(this).attr('data');
		$('#wordbook-basic-info-modal #modify-wordbook-title').val(wordbook_title);
		$('#wordbook-basic-info-modal #modify-wordbook-description').val(wordbook_description);
		$('#wordbook-id').html(wordbook_id);
		modify_wordbook_basic_info();
	});
}
function verify_wordbook_title_length() {
	$('.wordbook-basicinfo-submit-btn').click(function() {
		var title_length = $('#id_title').val().length;
		var description_length = $('#id_description').val().trim().length;
		if (title_length < MIN_TITLE_LENGTH || description_length < MIN_DESCRIPTION_LENGTH) {
			if (title_length < MIN_TITLE_LENGTH) {
				alert($('#wordbook-title-invalid-length-hint').html());
			} else {
				alert($('#wordbook-description-invalid-length-hint').html());
			}
			return false;
		}
	});
}
function verify_wordbook_cover() {
	$('#wordbook-cover-upload-button').click(function() {
		if ($('#cover-image').val() == false) {
			alert($('#wordbook-empty-cover').html());
			return false;
		}
	});
}
function create_wordbook_add_wordlist() {
	$(".create-wordbook-add-wordlist-btn").unbind('click').click(function() {
		var wordlist_id = $(this).attr('data-id');
		var wordbook_id = $('#wordbook-id-html').html();
		var parent_dom = $(this).parent();
		parent_dom.html($('#manipulate-wordlist-waiting-hint').html());
		$.post('/wordbook/add/wordlist/', {
			wordlist_id : wordlist_id,
			wordbook_id : wordbook_id
		}, function(data) {
			if (data.status == 0) {
				$('#remove-wordlist-button button').attr("data-id", wordlist_id);
				parent_dom.html($('#remove-wordlist-button').html());
				parent_dom.parent().find('span.badge').show();
				create_wordbook_remove_wordlist();
			} else {
				parent_dom.html($('#manipulate-wordlist-error-hint').html());
			}
		}, 'json');
	});
}
function create_wordbook_remove_wordlist() {
	$(".create-wordbook-remove-wordlist-btn").unbind('click').click(function() {
		var wordlist_id = $(this).attr('data-id');
		var wordbook_id = $('#wordbook-id-html').html();
		var parent_dom = $(this).parent();
		parent_dom.html($('#manipulate-wordlist-waiting-hint').html());
		$.post('/wordbook/remove/wordlist/', {
			wordlist_id : wordlist_id,
			wordbook_id : wordbook_id
		}, function(data) {
			if (data.status == 0) {
				$('#add-wordlist-button button').attr("data-id", wordlist_id);
				parent_dom.html($('#add-wordlist-button').html());
				parent_dom.parent().find('span.badge').hide();
				create_wordbook_add_wordlist();
			} else {
				parent_dom.html($('#manipulate-wordlist-error-hint').html());
			}
		}, 'json');
	});
}
function trigger_change_wordbook_cover() {
	$('a#change-wordbook-cover').click(function() {
		$('#wordbook-cover-div').slideUp();
		$('#change-wordbook-cover-form').slideDown();
	});
}
function delete_user_wordbook() {
	$('.delete-wordbook a').click(function() {
		var a = confirm($('#delete-confirm-prompt').html());
		if (a) {
			var this_dom = $(this);
			this_dom.hide();
			this_dom.parent().find('.delete-hint').show();
			var wordbook_id = $(this).attr('data-id');
			$.get('/wordbook/delete/' + wordbook_id + '/', {}, function(data) {
				this_dom.parent().find('.delete-hint').hide();
				if (data.status == SUCCESS_STATUS) {
					location.reload();
				} else {
					alert($('#delete-fail-prompt').html());
				}
			}, 'json');
		}
	});
}
function switch_to_wordlist_tab() {
	$("#wordlist-tab-trigger").click();
}
function switch_to_wordbook_tab() {
	$("#wordbook-tab-trigger").click();
}
function trigger_start_to_learn_wordbook_hint() {
	$(".btn-start-to-learn-wordbook").unbind('click').click(function() {
		var wordbook_title = $(this).parent().parent().find('.wordbook-title a').html();
		var next_url = $(this).attr("next");
		$("#start-to-learn-wordbook-hint-modal .gonna_learn_type").html("单词书");
		$("#start-to-learn-wordbook-hint-modal .gonna_learn").html(wordbook_title);
		$("#start-to-learn-wordbook-hint-modal .btn-primary").attr("href", next_url);
		$("#start-to-learn-wordbook-hint-modal").modal();
	});
}
function trigger_start_to_learn_wordlist_hint() {
	$(".btn-start-to-learn-wordlist").unbind('click').click(function() {
		var wordlist_title = $(this).parent().parent().find('.wordbook-wordlist-name a').html();
		var next_url = $(this).attr("next");
		$("#start-to-learn-wordbook-hint-modal .gonna_learn_type").html("词串");
		$("#start-to-learn-wordbook-hint-modal .gonna_learn").html(wordlist_title);
		$("#start-to-learn-wordbook-hint-modal .btn-primary").attr("href", next_url);
		$("#start-to-learn-wordbook-hint-modal").modal();
	});
}
$('.wordbook-unsubscribe a').click(function() {
	var index = $('.wordbook-unsubscribe a').index($(this));
	$(this).hide();
	$(this).parent().children().eq(2).show();
	var wordbook_id = $(this).parent().children().eq(1).html();
	$.get('/wordbook/unsubscribe/' + wordbook_id + '/', function(data) {
		if (data.status == SUCCESS_STATUS) {
			window.location.reload();
		} else {
			alert("取消收藏失败，请稍后再试");
		}
	}, 'json');
});
function refresh_create_button() {
	if ($('#wordbook-tab-trigger').parent().hasClass('active')) {
		$('.create-wordlist-btn-area').removeClass('hide');
		$('.create-wordbook-btn-area').addClass('hide');
	} else {
		$('.create-wordbook-btn-area').removeClass('hide');
		$('.create-wordlist-btn-area').addClass('hide');
	}
}
$('#wordbook-wordlist-switcher li').click(refresh_create_button);
function init_create_button() {
	if ($('#wordbook-tab-trigger').parent().hasClass('active')) {
		$('.create-wordbook-btn-area').removeClass('hide');
		$('.create-wordlist-btn-area').addClass('hide');
	} else {
		$('.create-wordlist-btn-area').removeClass('hide');
		$('.create-wordbook-btn-area').addClass('hide');
	}
}
function enable_update_wordbook_weight() {
	function trigger_show_update_wordbook_weight() {
		$(this).hide();
		$(this).next().show();
	}
	function trigger_hide_update_wordbook_weight() {
		$(this).parent().hide();
		$(this).parent().prev().show();
	}
	$('.btn-show-update-wordbook-weight').click(trigger_show_update_wordbook_weight);
	$('.update-wordbook-weight .btn-cancel').click(trigger_hide_update_wordbook_weight);
	$('.update-wordbook-weight .btn-submit').click(function() {
		var this_dom = $(this);
		this_dom.attr('disabled', true);
		var wordbook_id = this_dom.attr('data');
		var weight = this_dom.parent().find('input').val()
		console.log(wordbook_id);
		console.log(weight);
		url = '/wordbook/manage/update_weight/' + wordbook_id + '/' + weight + '/';
		$.get(url, function(data) {
			console.log(data);
			this_dom.parent().parent().find('.btn-show-update-wordbook-weight').html(weight);
			this_dom.prev().click();
			this_dom.attr('disabled', false);
		});
	});
}