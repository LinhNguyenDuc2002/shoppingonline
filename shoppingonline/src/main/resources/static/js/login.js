const login = document.querySelector('.js-login')
const signup = document.querySelectorAll('.js-signup')

const modal_login = document.querySelector('.js-modal-login')
const modal_signup = document.querySelector('.js-modal-signup')

const closes = document.querySelectorAll('.js-close')

function showLogin(){
    if (modal_signup.classList.contains('open')) {
        modal_signup.classList.remove('open')
    }
    modal_login.classList.add('open')
}
function showSignup(){
    if (modal_login.classList.contains('open')) {
        modal_login.classList.remove('open')
    }
    modal_signup.classList.add('open')
}
function hidden(){
    if (modal_signup.classList.contains('open')) {
        modal_signup.classList.remove('open')
    }
    else{
        modal_login.classList.remove('open')
    }
}

for(const close of closes){
    close.addEventListener('click',hidden)
}
for(const btn of signup){
    btn.addEventListener('click',showSignup)
}
login.addEventListener('click',showLogin)