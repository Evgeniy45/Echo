const loginbtn = document.querySelector('.login-btn');
const overlay = document.querySelector('.overlay')
const hide = document.querySelector('.hide')
const signUp = document.getElementById('sign-up')
const logIn = document.getElementById('log-in')
const log = document.querySelector('.reg')
const reg = document.querySelector('.reg-reg')

 

loginbtn.addEventListener('click', () => {
 
  

    overlay.classList.remove('hide')
    log.classList.remove('hide')
    
    
})

signUp.addEventListener('click', () => {

    log.classList.add('hide')

    reg.classList.remove('hide')
    
})

logIn.addEventListener('click', () => {
    log.classList.remove('hide')
    reg.classList.add('hide')
})


