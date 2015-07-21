/**
 * Created by JBA3353 on 27/01/2015.
 */



/*
 * HEADER SCROLL ANIMATION
 */

function headerScrollAnimationInit() {

    window.onscroll = function (e) {
        if(window.scrollY > 1) {
            document.getElementById('header').classList.add('sticky');
            document.getElementById('header-profile-img').classList.add('sticky');
            document.getElementById('header-profile-name').classList.add('sticky');
        } else {
            document.getElementById('header').classList.remove('sticky');
            document.getElementById('header-profile-img').classList.remove('sticky');
            document.getElementById('header-profile-name').classList.remove('sticky');
        }
    };
}

function headerScrollAnimationReset() {

    window.onscroll = function (e) {};
}




/*
 * VIRTUAL KEYBOARD
 */

function virtualKeyboardFixPosInit(element) {

    /* Get screen size */
    var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    var portraitScreenHeight;
    var landscapeScreenHeight;

    /* Get screen orientation */
    if(window.orientation === 0 || window.orientation === 180){
        portraitScreenHeight = h;
        landscapeScreenHeight = w;
    }
    else{
        portraitScreenHeight = w;
        landscapeScreenHeight = h;
    }

    /* Set event listener to hide element */
    var tolerance = 25;
    window.onresize = function(event) {
        if((window.orientation === 0 || window.orientation === 180) &&
            ((window.innerHeight + tolerance) < portraitScreenHeight)) {

            document.getElementById(element).style.display = 'none';
        }
        else if((window.innerHeight + tolerance) < landscapeScreenHeight){

            document.getElementById(element).style.display = 'none';
        }
        else{
            document.getElementById(element).style.display = 'block';
        }
    };

}

function virtualKeyboardFixPosReset() {

    window.onresize = function(event) {
    };
}



/*
 * LAZY LOADING
 */

function lazyLoadingInit() {

    var bLazy = new Blazy({
        src: 'real-src',
        success: function (element) {
            setTimeout(function () {
                // We want to remove the loader gif now.
                // First we find the parent container
                // then we remove the "loading" class which holds the loader image
                var parent = element.parentNode;
                parent.className = parent.className.replace(/\bloading\b/, '');
            }, 200);
        }
    });
}




function filtersScrollAnimation() {

    var toTop = document.getElementById('search-filter-list');
    var offset = 500;

    var lastScrollTop = 0;

    window.onscroll = function (e) {

        var st = window.scrollY;
        if (st > lastScrollTop){
            // downscroll code
            toTop.classList.remove('sticky');
        } else {
            // upscroll code
            if (window.scrollY > offset) {
                toTop.classList.add('sticky');
            } else {
                if(st == 0) {
                    toTop.classList.remove('sticky');
                }
            }
        }
        lastScrollTop = st;
    };
}
