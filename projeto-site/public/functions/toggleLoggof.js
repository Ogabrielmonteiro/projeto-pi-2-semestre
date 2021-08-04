var toggle = false;
    function showDropdown() {
        var element = document.getElementById('div-loggout');
        toggle = !toggle;
        if (toggle == false) {
            element.classList.remove('display-none');
            document.getElementById('div-container-dropdwon').classList.add('mt-30');
        } else {
            element.classList.add('display-none');
            document.getElementById('div-container-dropdwon').classList.remove('mt-30');
        }
    }