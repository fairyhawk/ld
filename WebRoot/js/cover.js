function highlight_main_menu(){
    $('#main-menu a').mouseenter(function(){
        $(this).css({color: '#000000'});
        });

    $('#main-menu a').mouseleave(function(){
        $(this).css({color: '#666666'});
        });
}

function hide_nav_btn(current_index){
    if (current_index == 0) {
        $('#nextBtn a').fadeIn();
        $('#prevBtn a').fadeOut();
        }
        else if(current_index == 3){
        $('#nextBtn a').fadeOut();
        $('#prevBtn a').fadeIn();
        }
        else{
        $('#nextBtn a').fadeIn();
        $('#prevBtn a').fadeIn();
        }
}

function slide(){
    $('input#login-username').focus();
    $('#prevBtn a').fadeOut();
    var current_index = 0;
    $('#nextBtn').click(function(){
        current_index += 1;
        if (current_index >= 4) current_index = 3;
        hide_nav_btn(current_index);
        var new_left = - 990 * current_index;
        $('#slider-bar li a').removeClass('current').addClass('notcurrent');
        $('#slider-bar li a').eq(Math.abs(new_left) / 990).removeClass('notcurrent').addClass('current');
        $('#slider-ul').animate(
        {marginLeft: new_left},600
        );
        });

    $('#prevBtn').click(function(){
        current_index -= 1;
        if (current_index <=0) current_index = 0;
        hide_nav_btn(current_index);
        var new_left = - 990 * current_index;
        $('#slider-bar li a').removeClass('current').addClass('notcurrent');
        $('#slider-bar li a').eq(Math.abs(new_left) / 990).removeClass('notcurrent').addClass('current');
        $('#slider-ul').animate(
        {marginLeft: new_left},600
        );
        });

    $('#slider-bar li a').click(function(){
        current_index = $('#slider-bar li a').index(this);
        hide_nav_btn(current_index);
        var new_left = -990 * current_index + 'px';
        $('.slide-content').hide();
        var t = setTimeout($('#slider-ul').css('margin-left', new_left), 400);
        $('.slide-content').fadeIn(600);
        $('#slider-bar li a').removeClass('current').addClass('notcurrent');
        $(this).removeClass('notcurrent').addClass('current');
    });
}

$('input#id_username').focus();
