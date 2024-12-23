// Відкриття модального вікна
document.getElementById("add_post_button").addEventListener("click", function() {
  document.getElementById("editor_modal_redactive_container").style.display = "flex"; // Показати модальне вікно
  document.body.classList.add("no_scroll"); // Заблокувати прокрутку сторінки
});

// Закриття модального вікна
document.getElementById("close_redactive_btn").addEventListener("click", function() {
  document.getElementById("editor_modal_redactive_container").style.display = "none"; // Приховати модальне вікно
  document.body.classList.remove("no_scroll"); // Відновити прокрутку сторінки
});
