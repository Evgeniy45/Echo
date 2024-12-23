document.addEventListener("DOMContentLoaded", function () {
    const userInfoContainer = document.querySelector(".user_info_container");
    const footer = document.querySelector(".footer");
    const initialTop = userInfoContainer.getBoundingClientRect().top;
    const containerHeight = userInfoContainer.offsetHeight;
    const distanceToFooter = 36;
    let lastScrollY = window.scrollY; // Для збереження попередньої позиції скролу
    let scrollTimeout; // Таймер для обробки зупинки скролу

    document.addEventListener("scroll", function () {
        const scrollPosition = window.scrollY;
        const footerTop = footer.getBoundingClientRect().top + scrollPosition;
        const maxTop = footerTop - containerHeight - distanceToFooter;

        // Логіка позиціонування блоку
        if (scrollPosition + initialTop + containerHeight + distanceToFooter >= footerTop) {
            userInfoContainer.style.position = "absolute";
            userInfoContainer.style.top = `${maxTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        } else if (scrollPosition <= initialTop) {
            userInfoContainer.style.position = "fixed";
            userInfoContainer.style.top = `${initialTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        } else {
            userInfoContainer.style.position = "absolute";
            userInfoContainer.style.top = `${scrollPosition + initialTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        }

        // Логіка ефекту тіні
        if (scrollPosition > lastScrollY) {
            // Скрол вниз
            userInfoContainer.classList.add("shadow-top");
            userInfoContainer.classList.remove("shadow-bottom");
        } else if (scrollPosition < lastScrollY) {
            // Скрол вгору
            userInfoContainer.classList.add("shadow-bottom");
            userInfoContainer.classList.remove("shadow-top");
        }

        // Скидаємо таймер
        clearTimeout(scrollTimeout);

        // Якщо скрол зупинився, знімаємо всі класи тіней
        scrollTimeout = setTimeout(() => {
            userInfoContainer.classList.remove("shadow-top", "shadow-bottom");
        }, 10); // Тінь зникає через 150 мс після зупинки скролу

        // Оновлюємо попередню позицію скролу
        lastScrollY = scrollPosition;
    });
});
