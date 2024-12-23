const userDesc = document.querySelector('.user_description');
const userDescBtn = document.querySelector('.user_edit_button');


let isEditing = false;


userDescBtn.addEventListener('click', () => {
    if (isEditing) {

        userDesc.setAttribute("contenteditable", "false"); 
        userDescBtn.textContent = "Edit"; 
        isEditing = false;
  
    } else {
       
        userDesc.setAttribute("contenteditable", "true"); 
        userDesc.focus(); 
        userDescBtn.textContent = "Save"; 
        isEditing = true;
    }
});


userDesc.addEventListener('input', () => {
    const text = userDesc.textContent;


    if (text.length > 100) {
        userDesc.textContent = text.slice(0, 100); 
        alert("Довжина тексту обмежена 100 символами."); 
        userDesc.focus(); 
        const range = document.createRange();
        const selection = window.getSelection();
        range.selectNodeContents(userDesc);
        range.collapse(false);
        selection.removeAllRanges();
        selection.addRange(range);
    }
});
