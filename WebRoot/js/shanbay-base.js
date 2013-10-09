jQuery.fn.outerHTML=function(){return $('<div></div>').append(this.eq(0).clone()).html();};String.format=function(){var s=arguments[0];for(var i=0;i<arguments.length-1;i++){var reg=new RegExp("\\{"+i+"\\}","gm");s=s.replace(reg,arguments[i+1]);}
return s;}
String.clean=function(){if(!arguments[0])return false;var s=arguments[0];s=s.toLowerCase()
s=s.replace(/[\",.!:;?]+/g,' ');s=s.replace(/[\']/g,'');s=s.replace(/\s{2,}/g,' ').replace(/^\s*/,'').replace(/\s*$/,'');return s}
String.prototype.clean=function(){var s=this.toLowerCase();s=s.replace(/[\",.!:;?]+/g,' ');s=s.replace(/[\']/g,'');s=s.replace(/\s{2,}/g,' ').replace(/^\s*/,'').replace(/\s*$/,'');return s}
String.prototype.clean_space=function(){var s=this;s=s.replace(/\s{2,}/g,' ').replace(/^\s*/,'').replace(/\s*$/,'');return s}
Array.prototype.append=function(element){if(Object.prototype.toString.call(this)=='[object Array]'){this.push(element);return this;}else{return false;}}
Array.prototype.uniq=function(){var new_array=new Array();$.each(this,function(k,v){if($.inArray(v,new_array)==-1){new_array.push(v);}});return new_array;}
Array.prototype.last=function(){var l=this.length-1;return this[l];}
Array.prototype.max=function(){var a=this.slice(0);var b=a.sort().reverse();return b[0];}
Array.prototype.min=function(){var a=this.slice(0);return a.sort()[0];}
Array.prototype.remove=function(v){var i=$.inArray(v,this);if(i==-1)return false;this.splice(i,1);return this;}
Array.prototype.shuffle=function(){var s=[];while(this.length)s.push(this.splice(Math.random()*this.length,1)[0]);while(s.length)this.push(s.pop());return this;}
$.fn.parseTemplate=function(data)
{var str=(this).html();var _tmplCache={}
var err="";try
{var func=_tmplCache[str];if(!func)
{var strFunc="var p=[],print=function(){p.push.apply(p,arguments);};"+"with(obj){p.push('"+
str.replace(/[\r\t\n]/g," ").replace(/'(?=[^#]*#>)/g,"\t").split("'").join("\\'").split("\t").join("'").replace(/<#=(.+?)#>/g,"',$1,'").split("<#").join("');").split("#>").join("p.push('")
+"');}return p.join('');";func=new Function("obj",strFunc);_tmplCache[str]=func;}
return func(data);}catch(e){err=e.message;}
return"< # ERROR: "+err.toString()+" # >";}
play_mp3=function(audio_url){if(!audio_url)return false;try{var sound=soundManager.getSoundById('shanbay_audio');if(!sound)return false;}catch(err){return false;}
if(sound.isHTML5){sound.play({url:audio_url})}else{sound.load({url:audio_url,autoPlay:true}).play();}}
play_preloaded_mp3=function(review){var review_id=review.id;if(!review_id)return false;try{var sound=soundManager.getSoundById('review-sound-'+review_id);if(!sound)return false;}catch(err){return false;}
if(sound.loaded){if(sound.isHTML5){sound.play();}else{sound.play();}}else{play_mp3(sound.url);};}
$('[data-toggle="dropdown"]').hover(function(){$('[data-toggle="dropdown"]').parent().removeClass('open')
$(this).parent().addClass('open');$(this).parent().mouseleave(function(){$(this).removeClass('open');});});$('[data-toggle="dropdown"]').click(function(){var url=$(this).attr('url');if(url){window.location.href=url;};});$('.shortcuts-button').click(function(){$('.shortcuts-modal').modal();});document.onkeydown=check;function check(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;try{if(((event.keyCode==8)&&((event.srcElement.type!="text"&&event.srcElement.type!="textarea"&&event.srcElement.type!="password")||event.srcElement.readOnly==true))){event.keyCode=0;event.returnValue=false;}}catch(err){return true;}}
var prompt_feedback_dialog=function(){if(!$('.feedback-modal form textarea').val()){$('#feedback-prompt').html($('#feedback-prompt-modal-tmpl').html());};$('#feedback-prompt-modal').modal();$('.feedback-modal form textarea').focus();$('#btn-submit-feedback').click(function(){$('#btn-submit-feedback').attr('disabled','disabled');var feedback=$('.feedback-modal form textarea').val();if(feedback.length==0){return false;}
var review_id=undefined;if($('#review .review-id').length>0){review_id=$('#review .review-id').attr('data-id');}
var feedback_data={content:feedback}
feedback_data.feedback_url=$("#feedback-url").html();if(review_id!=undefined){feedback_data.review_id=review_id;}
$.post('/feedback/add/',feedback_data,function(data){if(data.status===0){$('#feedback-prompt-modal .modal-body').html($('#feedback-submit-success-hint').html());$('#feedback-prompt-modal .modal-footer').html($('#btn-feedback-hint-close').html());$('.feedback-modal form textarea').val('');}
else{$('#feedback-prompt-modal .modal-body').html($('#feedback-submit-fail-hint').html());$('#feedback-prompt-modal .modal-footer').html($('#btn-feedback-hint-close').html());}});return false;});}
$('.feedback-button').click(prompt_feedback_dialog);var add_word=function(e,r){$(e.currentTarget).attr('disabled',true);$.ajax({url:'/api/v1/bdc/learning/',type:'POST',dataType:'json',data:"{\"id\":"+r.object_id+",\"content_type\":\""+r.content_type+"\"}",contentType:'application/json',success:function(r){$('.word-added').show();$('.word-added a').attr('href','/review/learning/'+r.data.id);}});};var forget=function(e,r){$('.popover .forget').toggle();$.ajax({url:'/api/v1/bdc/learning/'+r.data.learning_id,type:'PUT',dataType:'json',data:"{\"retention\":1}",contentType:'application/json'});$(e.currentTarget).attr('disabled','true');};var speak=function(r){play_mp3(r.data.uk_audio);};var show_popup=function(e){$('.navbar-search').popover({placement:'bottom',trigger:"manual"}).popover('show');$('body').click(close_popup);};var close_popup=function(e){e.stopPropagation();$('body').trigger('click.dropdown.data-api');$('html').trigger('click.dropdown');$('.navbar-search').popover({placement:'bottom',trigger:"manual"}).popover('hide');$('body').unbind('click');};var show_result=function(search_result,word){var result=search_result.status_code;if(result==1){var html=$('#search_fail_tmpl').tmpl({word:word});var title=$(html).find('.title').html();var html=$(html).find('.body').html();}else{var html=$('#search_tmpl').tmpl(search_result.data).html();var title=$('#search-result-title-tmpl').tmpl(search_result.data).html();}
$('.search-query').val('');$('.navbar-search').attr('data-content',html);$('.navbar-search').attr('data-original-title',title);$('.navbar-search').popover({placement:'bottom',trigger:"manual"}).popover('show');};var search=function(e){e.preventDefault();e.stopPropagation();var word=$('.search-query').val();search_word(word);};function search_word(word){var matched_pattern=word.match(/[\w- ]+/);if(!matched_pattern){var title=$('#no_search_tmpl').tmpl().html();$('.navbar-search').attr('data-original-title',title);$('.navbar-search').popover({placement:'bottom',trigger:"manual"}).popover('show');return;}
$('.navbar-search').attr('data-original-title',TEXTS["loading"]).attr('data-content','');show_popup();if(word==''||word.length<2){return;}
$.getJSON('/api/v1/bdc/search/?word='+word,function(r){show_result(r,word);$('.popover .speaker').click(function(e){speak(r);return false;});$('#add-word').click(function(e){add_word(e,r.data);return false;});$('.add .forget').click(function(e){forget(e,r);return false;});});}
function getSelText(){var txt='';if(window.getSelection){txt=window.getSelection();}else if(document.getSelection){txt=document.getSelection();}else if(document.selection){txt=document.selection.createRange().text;}
return txt.toString();}
$('.navbar-search').submit(search);$('.search-icon').click(search);$('.container').dblclick(function(){var txt=$.trim(getSelText());if(txt){var matched_pattern=txt.match(/[a-zA-Z- ]+/);if(matched_pattern&&$('.navbar-search').length)
search_word(txt);}
return false;})
$.ajaxSetup({cache:false})