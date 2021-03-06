var wordtestCount = 0;
var wordtestCount_wrong = 0;
var wordtestCount_right = 0;
var rightWords = "";
var wrongWords = "";
function add_bkground_css(base) {
	for ( var count = 0; count < $("#vocabulary_test ul .row").length; count++) {
		var d = "#vocabulary_test ul .row:eq(" + count + ")";
		var label = "#vocabulary_test ul .row label:eq(" + count + ")";
		$(label).addClass('checkbox');
		if (count % base >= 0 && count % base < (base / 2)) {
			$(d).addClass('l2');
		} else {
			$(d).addClass('l1');
		}
	}

}

function dis() {
	$('#button_next #submit').click(function() {
		$('#button_next').children().eq(0).hide();
		$('#button_next').children().eq(1).show();
	});
}

function select_operation() {
	$('input#select_all').click(function() {
		$('input#select_all').attr('checked', true);
		$('ul li input').attr('checked', true);
		$('input#invert_selection').attr('checked', false);
	});
	$('input#invert_selection').click(function() {
		var length = $('ul li input').length;
		for ( var i = 0; i < length; i++) {
			if ($('ul li input').eq(i).attr('checked')) {
				$('ul li input').eq(i).attr('checked', false);
			} else {
				$('ul li input').eq(i).attr('checked', true);
			}
		}
		var checked_length = $('ul li input:checked').length;
		if (checked_length == length) {
			$('input#select_all').attr('checked', true);
		} else {
			$('input#select_all').attr('checked', false);
		}
	});
}

function judge() {
	$('.definition:eq(0)').addClass('alert-info');
	$('.definition input').click(function() {
		$("#last_result").show();
		var $definition = $(this).parents('.definition');
		$definition.slideUp();
		var answer_id = $(this).attr('answer');
		var choice_id = $(this).val();
		var right_answer = $.trim($("#choice_" + answer_id).find('label').text()).replace(/^\d\./, '');
		var rank = $(this).attr('rank');
		var selector = '#word_' + answer_id;
		var testWord = $(selector).text();
		var isRight = 0;
		if (answer_id == choice_id) {
			wordtestCount_right++;
			var answer_content = $("#last_result .right_answer_content").text()+testWord+":"+right_answer;
			$('#last_result').removeClass('alert-error alert-success').addClass('alert-success').find('.answer').text(answer_content).removeClass('hide');
			$('#last_result').find('.def').text('').addClass('hide');
			$(selector).addClass('correct');
			var ranks = $('#right_ranks').val() + " " + rank;
			$('#right_ranks').val(ranks);
			rightWords +=answer_id+"_";
			isRight=1
		} else {
			wordtestCount_wrong++;
			var answer_content = $("#last_result .error_answer_content").text();
			var def = testWord+$('#last_result .error_definition').text() + right_answer;
			if (choice_id === '0') {
				$('#last_result').removeClass('alert-success alert-error').addClass('alert-error').find('.answer').text("").removeClass('hide');
			} else {
				$('#last_result').removeClass('alert-success alert-error').addClass('alert-error').find('.answer').text(answer_content).removeClass('hide');
			}
			$('#last_result').find('.def').text(def).removeClass('hide');
			$(selector).addClass('incorrect');
			wrongWords +=answer_id+"_";
		}
		updateTestRecord(answer_id,isRight);
		wordtestCount = $("#wordtestCount").val();
		if(wordtestCount == wordtestCount_wrong+wordtestCount_right){
			$('#button_next input').attr('disabled', false);
		}
		$('#last_result').slideDown();
		$definition.next().addClass('alert-info');
	});
}

$('#button_exam').click(function() {
	var exam_type = $('#exam_names input:checked');
	if (exam_type.length == 0) {
		$('#fail_prompt').show();
		return false;
	}

	var url = $(this).attr('href').replace(/\/[A-Z0-9]+$/, '/');
	url = url + exam_type.val();
	$(this).attr('href', url);
});

$('#button-next-group').click(function() {
	$('#test-words-part-one').slideUp('slow');
	$('#test-words-part-two').slideDown('fast');
	$('#button_next').slideDown('fast');
});


function updateTestRecord(wordId,isRight){
	var testId = $("#mtId").val();
	 $.ajax({
			url : "/letter/updateTestRecord",
			data : {"testId":testId,"wordId":wordId,"isRight":isRight},
			type : "post",
			dataType : "json",
			cache : false,
			async : true,
			success:function(data){
				if(data.status==0)
					return true;
				else
					alert(data.msg);
			},
			error:function(error){
			}
	 });
}