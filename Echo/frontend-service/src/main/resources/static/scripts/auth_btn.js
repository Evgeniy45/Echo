const headerLogin = document.querySelector('.header_login');
const closeBtnLogin = document.querySelector('.close_btn_login');
const loginContainer = document.querySelector('.login_container');
const loginFormBtn = document.querySelector('.login_form_btn')
const loginInput = document.querySelector('.loginInput')
const loginError = document.querySelector('.loginError')

const headerRegister = document.querySelector('.header_register')
const registerContainer = document.querySelector('.register_container')
const closeBtnRegister = document.querySelector('.close_btn_register')
const registerFormBtn = document.querySelector('.register_form_btn')
const registerError = document.querySelector('.registerError')

const blackBackground = document.querySelector('.black_background');

let defaultSetting = true;
let defaultSetting2 = true

// loginb

headerLogin.addEventListener('click', (event) => {
    event.preventDefault(); 
    defaultSetting = !defaultSetting; 
    hideHandler(); 
});

function hideHandler() {
    if (defaultSetting === false) {
        document.body.classList.add('no-scroll'); // Додаємо клас до body
        blackBackground.classList.remove('hide');
        loginContainer.classList.remove('hide');
    } else {
        blackBackground.classList.add('hide');
        loginContainer.classList.add('hide');
        document.body.classList.remove('no-scroll'); // Додаємо клас до body
    }
}

closeBtnLogin.addEventListener('click', () => {
    defaultSetting = !defaultSetting;
    hideHandler(); 
});


// errorMessage
// Login form handling
loginFormBtn.addEventListener('click', (event) => {
    event.preventDefault();

    const inputs = document.querySelectorAll('.loginInput'); 
    let hasError = false;

    // Перевіряємо всі поля
    inputs.forEach((input) => {
        if (input.value.trim() === '') { 
            hasError = true;
        }
    });

    // Відображаємо помилку, якщо є незаповнені поля
    if (hasError) {
        loginError.classList.remove('hide'); 
    } else {
        loginError.classList.add('hide'); 
        blackBackground.classList.add('hide');
        document.body.classList.remove('no-scroll'); // Додаємо клас до body
        loginContainer.classList.add('hide');

        // Очищаємо поля після успішного входу
        inputs.forEach((input) => {
            input.value = '';  
        });
    }
});

// Register form handling
registerFormBtn.addEventListener('click', (event) => {
    event.preventDefault();
    
    const inputs = document.querySelectorAll('.registerInput'); 
    let hasError = false;

    // Перевіряємо всі поля
    inputs.forEach((input) => {
        if (input.value.trim() === '') { 
            hasError = true;
        }
    });

    // Відображаємо помилку, якщо є незаповнені поля
    if (hasError) {
        registerError.classList.remove('hide'); 
    } else {
        registerError.classList.add('hide'); 
        document.body.classList.remove('no-scroll'); // Додаємо клас до body
        blackBackground.classList.add('hide');
        registerContainer.classList.add('hide');

        // Очищаємо поля після успішної реєстрації
        inputs.forEach((input) => {
            input.value = '';  
        });
    }
});

// Header Register button handling
headerRegister.addEventListener('click', (event) => {
    event.preventDefault(); 
    defaultSetting2 = !defaultSetting2; 
    hideHandler2(); 
});

function hideHandler2() {
    if (!defaultSetting2) {
        document.body.classList.add('no-scroll'); // Додаємо клас до body
        blackBackground.classList.remove('hide');
        registerContainer.classList.remove('hide');
    } else {
        blackBackground.classList.add('hide');
        registerContainer.classList.add('hide');
        document.body.classList.remove('no-scroll'); // Додаємо клас до body
    }
}

closeBtnRegister.addEventListener('click', () => {
    defaultSetting2 = !defaultSetting2;
    hideHandler2(); 
});

// Switching between login and register
const signUp = document.querySelector('.sign_up');
const logIn = document.querySelector('.log_in');

// Переход з логіну до реєстрації
signUp.addEventListener('click', () => {
    clearInputs('.loginInput'); // Очищаємо поля форми логіну
    loginContainer.classList.add('hide');
    registerContainer.classList.remove('hide');   
        
});

// Переход з реєстрації до логіну
logIn.addEventListener('click', () => {
    clearInputs('.registerInput'); // Очищаємо поля форми реєстрації
    registerContainer.classList.add('hide');
    loginContainer.classList.remove('hide');       
});

// Функція для очищення полів
function clearInputs(selector) {
    const inputs = document.querySelectorAll(selector);
    inputs.forEach((input) => {
        input.value = '';
    });
}
