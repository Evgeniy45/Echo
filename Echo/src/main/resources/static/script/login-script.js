const loginbtn = document.querySelector('.login-btn');
const overlay = document.querySelector('.overlay')
const hide = document.querySelector('.hide')
const signUp = document.getElementById('sign-up')
const logIn = document.getElementById('log-in')
const log = document.querySelector('.reg')
const reg = document.querySelector('.reg-reg')
const emptyReg = document.querySelector('.emptyReg')
const emptyLogin = document.querySelector('.emptyLogin')
const completeBtn2 = document.getElementById('signup')
const error = document.querySelector('.error')



const completeBtn = document.getElementById('login')


function hideErrorMessages() {
    emptyLogin.classList.add('hide');
    emptyReg.classList.add('hide');
}

loginbtn.addEventListener('click', () => {
 
    document.body.style.overflow = 'hidden';

    overlay.classList.remove('hide')
    log.classList.remove('hide')
    hideErrorMessages();
    
    
})

signUp.addEventListener('click', () => {

    log.classList.add('hide')

    reg.classList.remove('hide')
    hideErrorMessages();

})

logIn.addEventListener('click', () => {
    log.classList.remove('hide')
    reg.classList.add('hide')
    hideErrorMessages();
})


completeBtn.addEventListener('click', () => {
    
    const username = document.querySelector('.inputUsername').value.trim();
    const inputPasswordLogin = document.querySelector('.inputPasswordLogin').value.trim(); // валідація логіна
    if(username, inputPasswordLogin === ''){
        emptyLogin.classList.remove('hide')
    }else{
        emptyLogin.classList.add('hide')
    }
  
})

completeBtn2.addEventListener('click', () => {
    document.body.style.overflow = 'hidden';
    const emailInput = document.getElementById('email').value.trim()
    const usernameInput = document.getElementById('usernameReg').value.trim()
    const phoneInput = document.getElementById('phone').value.trim()
    const passwordInput = document.getElementById('password').value.trim() // валідація регістрації
    const confirmInput = document.getElementById('confirm').value.trim()

    if(emailInput, usernameInput, phoneInput, passwordInput, confirmInput == ''){
        emptyReg.classList.remove('hide')
    }else{
        emptyReg.classList.add('hide')
    }

    if(passwordInput !== confirmInput){
        error.classList.remove('hide')
        console.log('password don`t match');
        
    }else{
        error.classList.add('hide')
    }
    
})

