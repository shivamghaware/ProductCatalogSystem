document.addEventListener('DOMContentLoaded', () => {
    // Navbar Hide on Scroll Logic
    let lastScrollY = window.scrollY;
    const navbar = document.querySelector('.navbar');

    if (navbar) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > lastScrollY && window.scrollY > 50) {
                // Scrolling Down
                navbar.classList.add('navbar-hidden');
            } else {
                // Scrolling Up
                navbar.classList.remove('navbar-hidden');
            }
            lastScrollY = window.scrollY;
        });
    }
});
