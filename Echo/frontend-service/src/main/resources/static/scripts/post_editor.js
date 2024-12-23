// Відкриття модального вікна
document.getElementById("add_post_button").addEventListener("click", function() {
  document.getElementById("editor_modal").style.display = "flex"; // Показати модальне вікно
  document.body.classList.add("no_scroll"); // Заблокувати прокрутку сторінки
});

// Закриття модального вікна
document.getElementById("close_button").addEventListener("click", function() {
  document.getElementById("editor_modal").style.display = "none"; // Приховати модальне вікно
  document.body.classList.remove("no_scroll"); // Відновити прокрутку сторінки
});
